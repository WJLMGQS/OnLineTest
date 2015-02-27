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
import org.wjlmgqs.daomain.Grade;

public class GradeDaoImp {
	public int getAllGradesNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_grades ";
		return sqlUtil.getTableNumber(sql);
	}
	
	/*
	 * 创建年级，同学院下不能有相同年级代码 *
	 */
	public String createGrade(Grade grade) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_grades(code) values('"
				+ grade.getCode() + "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedGrade_code(stmt, grade)) {
				showMessage = "创建失败：年级--" + grade.getCode() + "--已经存在！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：年级--" + grade.getCode() + "--创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：年级--" + grade.getCode() + "--创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新年级创建失败！";
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
	
	public boolean isExistedGrade_code(Statement stmt, Grade grade) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_grades where code='" + grade.getCode() + "'";
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

	public Grade getGradeById(int grade_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select t_grades.id,t_grades.code"
				+ " from  t_grades where t_grades.id='"
				+ grade_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		Grade grade = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				grade = new Grade();
				grade.setId(rs.getInt("id"));
				grade.setCode(rs.getString("t_grades.code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return grade;
	}
	
	public List<Grade> getAllGrades() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Grade> list = new ArrayList<Grade>();
		String sql = "select t_grades.id,t_grades.code"
				+ " from  t_grades ";
	//	sql = sqlUtil.LimitSQLByIndex(i,pageSize,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Grade grade = null;
			while (rs.next()) {
				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));
				list.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public String updateGradeInfo(Grade grade) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_grades set code=? where id='"
				+ grade.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedGrade_code(ps, grade)) {
				showMessage = "创建失败：年级--" + grade.getCode() + "--已经存在！";
			} else {
				ps.setString(1, grade.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：年级信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，年级信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，年级信息更新失败！";
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
	
}
