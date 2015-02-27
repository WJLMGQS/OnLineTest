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
import org.wjlmgqs.daomain.Subject;

public class SubjectDaoImp {
	public boolean isExistedSubject_code(Statement stmt, Subject subject) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_subjects where code='"
				+ subject.getCode() + "'";
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

	public Subject getSubjectById(int subject_id) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id,code from  t_subjects where id='" + subject_id
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Subject subject = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				subject = new Subject();
				subject.setCode(rs.getString("code"));
				subject.setId(subject_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return subject;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return subject;
	}

	public List<Subject> getAllSubjects() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Subject> list = new ArrayList<Subject>();
		String sql = "select id,code from  t_subjects where id>0 ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Subject subject = null;
			while (rs.next()) {
				subject = new Subject();
				subject.setId(rs.getInt("id"));
				subject.setCode(rs.getString("code"));
				list.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public String createSubject(Subject subject) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_subjects(code) values('"
				+ subject.getCode() + "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedSubject_code(stmt, subject)) {
				showMessage = "创建失败：科目--" + subject.getCode() + "--已经存在！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：新科目创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：新科目创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新科目创建失败！";
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
	public int getAllSubjectsNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_subjects where id>0";
		return sqlUtil.getTableNumber(sql);
	}
	public String updateSubjectInfo(Subject subject) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_subjects set code=? where id='"
				+ subject.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedSubject_code(ps, subject)) {
				showMessage = "提示：创建失败：科目--" + subject.getCode() + "--已经存在！";
			} else {
				ps.setString(1, subject.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：科目信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，科目信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，科目信息更新失败！";
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
