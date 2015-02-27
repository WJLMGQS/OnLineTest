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
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.HelpMan;

public class HelpManDaoImp {
	public boolean isExistedHelpMan_code(Statement stmt, HelpMan helpMan) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_helpMans where code='"
				+ helpMan.getCode() + "' and department_id='"
				+ helpMan.getDepartment().getId() + "'";
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
	public boolean isExistedHelpMan(Statement stmt, int help_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_helpMans where id='" + help_id + "'";
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

	public HelpMan getHelpManById(int help_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select t_helpMans.id,t_helpMans.code,t_departments.id,t_departments.code"
				+ " from  t_helpMans,t_departments where department_id=t_departments.id and t_helpMans.id='"
				+ help_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		HelpMan helpMan = null;
		Department department = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				department = new Department();
				department.setCode(rs.getString("t_departments.code"));
				department.setId(rs.getInt("t_departments.id"));
				helpMan = new HelpMan();
				helpMan.setId(rs.getInt("id"));
				helpMan.setDepartment(department);
				helpMan.setCode(rs.getString("t_helpMans.code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return helpMan;
	}
	public List<HelpMan> getAllHelpMans() {
		SQLUtil sqlUtil = new SQLUtil();
		List<HelpMan> list = new ArrayList<HelpMan>();
		String sql = "select t_helpMans.id,t_helpMans.code,t_departments.id,t_departments.code"
				+ " from  t_helpMans,t_departments where department_id=t_departments.id ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			HelpMan helpMan = null;
			Department department = null;
			while (rs.next()) {
				department = new Department();
				department.setCode(rs.getString("t_departments.code"));
				department.setId(rs.getInt("t_departments.id"));
				helpMan = new HelpMan();
				helpMan.setId(rs.getInt("t_helpMans.id"));
				helpMan.setDepartment(department);
				helpMan.setCode(rs.getString("t_helpMans.code"));
				list.add(helpMan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public int getAllHelpMansNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_helpMans ";
		return sqlUtil.getTableNumber(sql);
	}

	public List<HelpMan> getAllHelpMansByDepartmentId(int department_id) {
		SQLUtil sqlUtil = new SQLUtil();
		List<HelpMan> list = null;
		String sql = "select t_helpMans.id,t_helpMans.code,t_departments.id,t_departments.code"
				+ " from  t_helpMans,t_departments where department_id=t_departments.id and department_id='"
				+ department_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			DepartmentDaoImp DepartmentDaoImp = new DepartmentDaoImp(); 
			if (DepartmentDaoImp.isExistedDepartment(stmt, department_id)) {
				list = new ArrayList<HelpMan>();
				rs = stmt.executeQuery(sql);
				HelpMan helpMan = null;
				Department department = null;
				while (rs.next()) {
					department = new Department();
					department.setCode(rs.getString("t_departments.code"));
					department.setId(rs.getInt("t_departments.id"));
					helpMan = new HelpMan();
					helpMan.setId(rs.getInt("t_helpMans.id"));
					helpMan.setDepartment(department);
					helpMan.setCode(rs.getString("t_helpMans.code"));
					list.add(helpMan);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	/*
	 * 创建辅导员，辅导员的姓名可以重复 *
	 */
	public String createHelpMan(HelpMan helpMan) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_helpMans(code,department_id) values('"
				+ helpMan.getCode() + "','" + helpMan.getDepartment().getId()
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
			if (isExistedHelpMan_code(stmt, helpMan)) {
				showMessage = "创建失败：" + helpMan.getDepartment().getCode()
						+ "辅导员--" + helpMan.getCode() + "--已经存在!";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：辅导员--" + helpMan.getCode() + "--创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：辅导员--" + helpMan.getCode() + "--创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新辅导员创建失败！";
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


	public String updateHelpManInfo(HelpMan helpMan) {
		String showMessage = null;
		String sql = "update t_helpMans set department_id=?,code=? where id='"
				+ helpMan.getId() + "'";
		//sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedHelpMan_code(ps, helpMan)) {
				showMessage = "创建失败：辅导员--" +helpMan.getCode() + "--已经存在!";
			} else {
				int _account = 1;
				ps.setInt(_account++, helpMan.getDepartment().getId());
				ps.setString(_account++, helpMan.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：辅导员信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，辅导员信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，辅导员信息更新失败！";
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
	
	public String deleteHelpManById(int help_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "delete from t_helpMans where id='" + help_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (!isExistedHelpMan(stmt, help_id)) {
				showMessage = "提示：辅导员--" + help_id + "--不存在,删除失败！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：辅导员--" + help_id + "--删除成功！";
					conn.commit();
				} else {
					showMessage = "提示：账号出现异常,删除操作失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,删除操作失败！";
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

}
