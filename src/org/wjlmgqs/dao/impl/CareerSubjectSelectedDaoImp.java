/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.CareerSubjectSelected;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.Subject;

public class CareerSubjectSelectedDaoImp {
	public boolean isExistedCareerSubjectSelected_code(Statement stmt,
			CareerSubjectSelected careerSubjectSelected) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_career_subject_choices where subject_id="
				+ careerSubjectSelected.getSubject().getId()
				+ " and career_id=" + careerSubjectSelected.getCareer().getId();
		sqlUtil.dealSQLPrint(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			DBUtils.closeConnection(null, null, rs);
		}
		return false;
	}

	/*
	 * 获取指定的选修记录
	 */
	public CareerSubjectSelected getCareerSubjectSelectedById(int css_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select t_career_subject_choices.id , t_subjects.id,t_subjects.code , "
				+ "				t_careers.id,t_careers.code ,t_grades.id,t_grades.code , t_departments.id,t_departments.code "
				+ "		from t_career_subject_choices,t_subjects,t_departments ,t_careers,t_grades "
				+ "		where t_career_subject_choices.subject_id=t_subjects.id and t_career_subject_choices.career_id =t_careers.id "
				+ "					and  t_careers.grade_id=t_grades.id and t_careers.department_id=t_departments.id and t_career_subject_choices.id="
				+ css_id;
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		CareerSubjectSelected careerSubjectSelected = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Subject subject = null;
			Department department = null;
			Grade grade = null;
			Career career = null;
			if (rs.next()) {
				department = new Department();
				department.setCode(rs.getString("t_departments.code"));
				department.setId(rs.getInt("t_departments.id"));

				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));

				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setGrade(grade);
				career.setCode(rs.getString("t_careers.code"));
				career.setDepartment(department);
				
				subject = new Subject();
				subject.setCode(rs.getString("t_subjects.code"));
				subject.setId(rs.getInt("t_subjects.id"));

				careerSubjectSelected = new CareerSubjectSelected();
				careerSubjectSelected.setId(rs
						.getInt("t_career_subject_choices.id"));
				careerSubjectSelected.setCareer(career);
				careerSubjectSelected.setSubject(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return careerSubjectSelected;
	}
	/*
	 * 获取科目对象和班级对象，不获取专业、辅导员、年级、学院等详细对象信息，通过点击班级对象再查看详细的信息
	 */
	public List<CareerSubjectSelected> getAllCareerSubjectSelecteds() {
		SQLUtil sqlUtil = new SQLUtil();
		List<CareerSubjectSelected> list = new ArrayList<CareerSubjectSelected>();
		String sql = "select t_career_subject_choices.id , t_subjects.id,t_subjects.code , "
				+ "				t_careers.id,t_careers.code ,t_grades.id,t_grades.code , t_departments.id,t_departments.code "
				+ "		from t_career_subject_choices,t_subjects,t_departments ,t_careers,t_grades "
				+ "		where t_career_subject_choices.subject_id=t_subjects.id and t_career_subject_choices.career_id =t_careers.id "
				+ "					and  t_careers.grade_id=t_grades.id and t_careers.department_id=t_departments.id ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Subject subject = null;
			CareerSubjectSelected careerSubjectSelected = null;
			Department department = null;
			Grade grade = null;
			Career career = null;
			while (rs.next()) {
				department = new Department();
				department.setCode(rs.getString("t_departments.code"));
				department.setId(rs.getInt("t_departments.id"));

				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));

				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setGrade(grade);
				career.setCode(rs.getString("t_careers.code"));
				career.setDepartment(department);
				subject = new Subject();
				subject.setCode(rs.getString("t_subjects.code"));
				subject.setId(rs.getInt("t_subjects.id"));

				careerSubjectSelected = new CareerSubjectSelected();
				careerSubjectSelected.setId(rs
						.getInt("t_career_subject_choices.id"));
				careerSubjectSelected.setCareer(career);
				careerSubjectSelected.setSubject(subject);
				list.add(careerSubjectSelected);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public int getAllCareerSubjectSelectedsNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_career_subject_choices ";
		return sqlUtil.getTableNumber(sql);
	}

	/*
	 * 创建选修，一个班级只能选修每门科目一次
	 */
	public String createCareerSubjectSelected(
			CareerSubjectSelected careerSubjectSelected) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into  t_career_subject_choices(career_id,subject_id) values('"
				+ careerSubjectSelected.getCareer().getId()
				+ "','"
				+ careerSubjectSelected.getSubject().getId() + "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedCareerSubjectSelected_code(stmt,
					careerSubjectSelected)) {
				showMessage = "创建失败："
						+ careerSubjectSelected.getCareer().getCode()
						+ "专业已经选修课程--"
						+ careerSubjectSelected.getSubject().getCode() + "--！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示："
							+ careerSubjectSelected.getCareer().getCode()
							+ "专业成功选修科目--"
							+ careerSubjectSelected.getSubject().getCode()
							+ "--！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新选修创建失败！";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("回滚事务失败！");
			}
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		return showMessage;
	}
	public String updateCareerSubjectSelectedInfo(
			CareerSubjectSelected careerSubjectSelected) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_career_subject_choices set career_id=?,subject_id=? where id='"
				+ careerSubjectSelected.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedCareerSubjectSelected_code(ps,
					careerSubjectSelected)) {
				showMessage = "更新失败："
						+ careerSubjectSelected.getCareer().getCode()
						+ "专业已经选修科目--"
						+ careerSubjectSelected.getSubject().getCode() + "--！";
			} else {
				int _account = 1;
				ps.setInt(_account++, careerSubjectSelected.getCareer().getId());
				ps.setInt(_account++, careerSubjectSelected.getSubject().getId());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：选修信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，选修信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，选修信息更新失败！";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}

	public List<Career> getAllCareersBySubjectID(int subject_id) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Career> list = new ArrayList<Career>();
		String sql = "select t_careers.id,t_careers.code from t_career_subject_choices,t_careers " +
				"where t_career_subject_choices.career_id=t_careers.id and t_career_subject_choices.subject_id="+subject_id;
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Career career = null;
			while (rs.next()) {
				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setCode(rs.getString("t_careers.code"));
				list.add(career);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

}
