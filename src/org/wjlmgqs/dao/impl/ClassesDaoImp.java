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
import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.HelpMan;

public class ClassesDaoImp {
	
	public boolean isExistedClasses_code(Statement stmt, Classes classes) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_classes where code='"
				+ classes.getCode() + "' and career_id='"
				+ classes.getCareer().getId() + "'";
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
	 * 获取指定的班级对象，封装年级、专业、学院、辅导员信息
	 */
	public Classes getClassesById(int class_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select t_classes.id,t_classes.code,"
			+ " t_careers.id,t_careers.code,"
			+ "t_grades.id,t_grades.code,"
			+ "A.id,A.code,"
			+ "B.id,B.code,"
			+ "t_helpMans.id,t_helpMans.code"
			+ " from t_classes,t_careers,t_grades,t_departments A,t_departments B,t_helpMans"
			+ " where t_careers.department_id=A.id and t_grades.id=t_careers.grade_id and t_classes.career_id=t_careers.id"
			+ " and t_classes.help_id=t_helpMans.id and t_helpMans.department_id=B.id and t_classes.id='"
				+ class_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		Classes classes = null;
		Grade grade = null;
		Department departmentA,departmentB = null;
		Career career = null;
		HelpMan helpMan = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				departmentA = new Department();
				departmentA.setCode(rs.getString("A.code"));
				departmentA.setId(rs.getInt("A.id"));

				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));

				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setGrade(grade);
				career.setCode(rs.getString("t_careers.code"));
				career.setDepartment(departmentA);
				
				departmentB = new Department();
				departmentB.setCode(rs.getString("B.code"));
				departmentB.setId(rs.getInt("B.id"));
				
				helpMan = new HelpMan();
				helpMan.setCode(rs.getString("t_helpMans.code"));
				helpMan.setDepartment(departmentB);
				helpMan.setId(rs.getInt("t_helpMans.id"));

				classes = new Classes();
				classes.setCareer(career);
				classes.setCode(rs.getString("t_classes.code"));
				classes.setHelpMan(helpMan);
				classes.setId(rs.getInt("t_classes.id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return classes;
	}
	
	/*
	 * 获取所有的班级信息，包括专业、年级、学院(分为专业所属学院，辅导员所属学院)，6张表连接查询
	 */
	public List<Classes> getAllClassess() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Classes> list = new ArrayList<Classes>();
		String sql = "select t_classes.id,t_classes.code,"
				+ " t_careers.id,t_careers.code,"
				+ "t_grades.id,t_grades.code,"
				+ "A.id,A.code,"
				+ "B.id,B.code,"
				+ "t_helpMans.id,t_helpMans.code"
				+ " from t_classes,t_careers,t_grades,t_departments A,t_departments B,t_helpMans"
				+ " where t_careers.department_id=A.id and t_grades.id=t_careers.grade_id and t_classes.career_id=t_careers.id"
				+ " and t_classes.help_id=t_helpMans.id and t_helpMans.department_id=B.id ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Grade grade = null;
			Department departmentA,departmentB = null;
			Career career = null;
			Classes classes = null;
			HelpMan helpMan = null;
			while (rs.next()) {
				departmentA = new Department();
				departmentA.setCode(rs.getString("A.code"));
				departmentA.setId(rs.getInt("A.id"));

				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));

				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setGrade(grade);
				career.setCode(rs.getString("t_careers.code"));
				career.setDepartment(departmentA);
				
				departmentB = new Department();
				departmentB.setCode(rs.getString("B.code"));
				departmentB.setId(rs.getInt("B.id"));
				
				helpMan = new HelpMan();
				helpMan.setCode(rs.getString("t_helpMans.code"));
				helpMan.setDepartment(departmentB);
				helpMan.setId(rs.getInt("t_helpMans.id"));

				classes = new Classes();
				classes.setCareer(career);
				classes.setCode(rs.getString("t_classes.code"));
				classes.setHelpMan(helpMan);
				classes.setId(rs.getInt("t_classes.id"));
				list.add(classes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public List<Classes> getAllClassessByCareerId(int career_id) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Classes> list = new ArrayList<Classes>();
		String sql = "select t_classes.id,t_classes.code,"
				+ " t_careers.id,t_careers.code,"
				+ "t_grades.id,t_grades.code,"
				+ "A.id,A.code,"
				+ "B.id,B.code,"
				+ "t_helpMans.id,t_helpMans.code"
				+ " from t_classes,t_careers,t_grades,t_departments A,t_departments B,t_helpMans"
				+ " where t_careers.department_id=A.id and t_grades.id=t_careers.grade_id and t_classes.career_id=t_careers.id"
				+ " and t_classes.help_id=t_helpMans.id and t_helpMans.department_id=B.id and t_classes.career_id='"+career_id+"'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Grade grade = null;
			Department departmentA,departmentB = null;
			Career career = null;
			Classes classes = null;
			HelpMan helpMan = null;
			while (rs.next()) {
				departmentA = new Department();
				departmentA.setCode(rs.getString("A.code"));
				departmentA.setId(rs.getInt("A.id"));

				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));

				career = new Career();
				career.setId(rs.getInt("t_careers.id"));
				career.setGrade(grade);
				career.setCode(rs.getString("t_careers.code"));
				career.setDepartment(departmentA);
				departmentB = new Department();
				departmentB.setCode(rs.getString("B.code"));
				departmentB.setId(rs.getInt("B.id"));
				
				helpMan = new HelpMan();
				helpMan.setCode(rs.getString("t_helpMans.code"));
				helpMan.setDepartment(departmentB);
				helpMan.setId(rs.getInt("t_helpMans.id"));

				classes = new Classes();
				classes.setCareer(career);
				classes.setCode(rs.getString("t_classes.code"));
				classes.setHelpMan(helpMan);
				classes.setId(rs.getInt("t_classes.id"));
				list.add(classes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	
	public String updateClassesInfo(Classes classes) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_classes set career_id=?,code=?,help_id=? where id='"
				+ classes.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedClasses_code(ps, classes)) {
				showMessage = "更新失败：班级--" + classes.getCode() + "--已经存在！";
			} else {
				int _account = 1;
				ps.setInt(_account++, classes.getCareer().getId());
				ps.setString(_account++, classes.getCode());
				ps.setInt(_account++, classes.getHelpMan().getId());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：班级信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，班级信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，班级信息更新失败！";
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
	
	/*
	 * 创建班级，辅导员和年级属于同一学院
	 * */
	public String createClasses(Classes classes) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_classes(code,career_id,help_id) values('"
				+ classes.getCode() + "','" + classes.getCareer().getId()+ "','" + classes.getHelpMan().getId()
				+ "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedClasses_code(stmt, classes)) {
				showMessage = "创建失败：班级--" + classes.getCode() + "--已经存在！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：班级--" + classes.getCode() + "--创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：班级--" + classes.getCode() + "--创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新班级创建失败！";
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
	
	public int getAllClassessNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_classes ";
		return sqlUtil.getTableNumber(sql);
	}
}
