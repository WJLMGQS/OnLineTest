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
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;

public class CareerDaoImp {

	/*
	 * 创建专业，同学院且同年级下不能有相同专业
	 */
	public String createCareer(Career career) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_careers(code,grade_id,department_id) values('"
				+ career.getCode() + "','" + career.getGrade().getId() + "','" + career.getDepartment().getId() + "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedCareer_code(stmt, career)) {
				showMessage = "创建失败：同学院且同年级下专业--" + career.getCode() + "--已经存在！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：专业--" + career.getCode() + "--创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：专业--" + career.getCode() + "--创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新专业创建失败！";
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
	public String updateCareerInfo(Career career) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_careers set grade_id=?,department_id=?,code=? where id='"
				+ career.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedCareer_code(ps, career)) {
				showMessage = "更新失败：同学院且同年级下专业专业--" + career.getCode() + "--已经存在！";
			} else {
				int _account = 1;
				ps.setInt(_account++, career.getGrade().getId());
				ps.setInt(_account++, career.getDepartment().getId());
				ps.setString(_account++, career.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：专业信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，专业信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，专业信息更新失败！";
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
	public boolean isExistedCareer_code(Statement stmt, Career career) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_careers where code='" + career.getCode()
				+ "' and grade_id='" + career.getGrade().getId() + "' and department_id='" + career.getDepartment().getId() + "'";
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
	 * 获取指定专业编号的专业对象
	 */
	public Career getCareerById(int career_id) {
		SQLUtil sqlUtil = new SQLUtil();
		Grade grade = null;
		Department department = null;
		Career career = null;
		String sql = "select t_careers.id,t_careers.code,t_grades.id,t_grades.code,t_departments.id,t_departments.code"
				+ " from t_careers,t_grades,t_departments "
				+ "where t_careers.department_id=t_departments.id and t_grades.id=t_careers.grade_id and t_careers.id='"
				+ career_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return career;
	}

	/*
	 * 获取所有的专业信息，包括年级和学院，3张表连接查询
	 */
	public List<Career> getAllCareers() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Career> list = new ArrayList<Career>();
		String sql = "select t_careers.id,t_careers.code,t_grades.id,t_grades.code,t_departments.id,t_departments.code"
				+ " from t_careers,t_grades,t_departments "
				+ "where t_careers.department_id=t_departments.id and t_grades.id=t_careers.grade_id ";
		//sql = sqlUtil.LimitSQLByIndex(i,pageSize,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Grade grade = null;
			Department department = null;
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
				list.add(career);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	private boolean isExistedCareer(Statement stmt, int grade_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_grades where id='" + grade_id + "'";
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
	

	public int getAllCareersNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_careers ";
		return sqlUtil.getTableNumber(sql);
	}
	public List<Career> getAllCareersByDepartmentId_GradeId(int department_id,int grade_id) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Career> list = null;
		String sql = "select t_careers.id,t_careers.code,t_grades.id,t_grades.code,t_departments.id,t_departments.code"
				+ " from  t_grades,t_careers,t_departments "
				+ "where t_careers.grade_id=t_grades.id and t_careers.department_id = t_departments.id and grade_id='"
				+ grade_id + "' and department_id='"+department_id+"'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			if (isExistedCareer(stmt, grade_id)) {
				list = new ArrayList<Career>();
				rs = stmt.executeQuery(sql);
				Grade grade = null;
				Career career = null;
				Department department = null;
				while (rs.next()) {
					department = new Department();
					department.setCode(rs.getString("t_departments.code"));
					department.setId(rs.getInt("t_departments.id"));

					grade = new Grade();
					grade.setId(rs.getInt("t_grades.id"));
					grade.setCode(rs.getString("t_grades.code"));

					career = new Career();
					career.setCode(rs.getString("t_careers.code"));
					career.setGrade(grade);
					career.setId(rs.getInt("t_careers.id"));
					career.setDepartment(department);
					
					list.add(career);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public List<Career> getCareerByIds(String[] careerIds) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Career> careers = new ArrayList<Career>();
		Career career = null;
		String sql = "select id from t_careers where " +sqlUtil.dealIds2Or(careerIds, "id");
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				career = new Career();
				career.setId(rs.getInt("id"));
				careers.add(career);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		if(careers.size()==0){
			return null;
		}
		return careers;
	}
}
