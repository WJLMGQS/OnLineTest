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

public class DepartmentDaoImp {
	public boolean isExistedDepartment_code(Statement stmt,
			Department department) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_departments where code='"
				+ department.getCode() + "'";
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
	
	public List<Department> getAllDepartments() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Department> list = new ArrayList<Department>();
		String sql = "select id,code from  t_departments ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Department department = null;
			while (rs.next()) {
				department = new Department();
				department.setId(rs.getInt("id"));
				department.setCode(rs.getString("code"));
				list.add(department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public int getAllDepartmentsNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_departments ";
		return sqlUtil.getTableNumber(sql);
	}
	
	public String createDepartment(Department department) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_departments(code) values('" + department.getCode() + "')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedDepartment_code(stmt, department)) {
				showMessage = "提示：学院--" +department.getCode()+ "--已经存在,创建失败！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：新学院创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：新学院创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新学院创建失败！";
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
	
	public String updateDepartmentInfo(Department department) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_departments set code=? where id='"
				+ department.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedDepartment_code(ps, department)) {
				showMessage = "创建失败：学院--" + department.getCode() + "--已经存在！";
			} else {
				ps.setString(1, department.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：学院信息更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，学院信息更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，学院信息更新失败！";
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
	
	public boolean isExistedDepartment(Statement stmt, int department_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_departments where id='" + department_id
				+ "'";
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
	
	public Department getDepartmentById(int department_id) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id,code from  t_departments where id='"
				+ department_id + "'";
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Department department = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				department = new Department();
				department.setCode(rs.getString("code"));
				department.setId(department_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return department;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return department;
	}
}
