/**
 **作者：翁加林
 **时间：2012-7-17
 **文件名：AdminDaoImp.java
 **包名：org.wjlmgqs.dao.impl
 **工程名：OnLineTest01
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
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.UserAccountStatus;

public class TeacherDaoImp {

	// --------------------------------------------------3个教师公有方法，直接由父对象调用---------------------------------------------------------------------------

	public boolean updateTeacherPwd(String fresh, String userId) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "update t_teachers set password='" + fresh + "' where id='" + userId + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 只能更新自己的姓名、性别、电话，其余信息有管理员支配
	 */
	public boolean updateTeacherInfoBySelf(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "update t_teachers set name='" + teacher.getName()
				+ "',sex='" + teacher.getSex() + "',telephone='"
				+ teacher.getTelephone() + "' where id='" + teacher.getUserId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 教师对象公有方法，直接通过教师对象调用
	 */
	public Teacher teacherLogin(String userId, String password) {
		SQLUtil sqlUtil = new SQLUtil();
		Teacher teacher = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select password,name,sex,telephone,status,power,t_subjects.id,code "
				+ "from t_teachers,t_subjects where t_subjects.id=t_teachers.subject_id and t_teachers.id='"
				+ userId + "'";
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String pwd = rs.getString("password");
				String status = rs.getString("status");
				if (pwd.equals(password) && status.equals("1")) {// 账号密码正确且账号已经激活
					Subject subject = new Subject();
					subject.setCode(rs.getString("t_subjects.code"));
					subject.setId(rs.getInt("t_subjects.id"));
					teacher = new Teacher();
					teacher.setUserId(userId);
					teacher.setPassword(rs.getString("password"));
					teacher.setName(rs.getString("name"));
					teacher.setSex(rs.getString("sex"));// 男：1 ；女0；
					teacher.setSubject(subject);
					teacher.setTelephone(rs.getString("telephone"));
					teacher.setPower(rs.getString("power"));
					teacher.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			teacher = null;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return teacher;
	}

	// ----------------------------------由子类继承调用-----------------------------------------------------------------

	/*
	 * 判断是否存在指定的教师账号
	 */
	private boolean isExistedTeacher(Statement stmt, String userId) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_teachers where id='" + userId + "'";
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
	 * 由管理员调用，可以更新该对象的所有信息，并检验权限
	 */
	public String updateTeacherPwdByAdmin(String fresh, String userId, int power) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_teachers set password=? where power='" + power
				+ "' and id='" + userId + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, fresh);
			int rs = ps.executeUpdate();
			if (rs == 1) {
				showMessage = "提示：用户密码更新成功！";
				conn.commit();
			} else {
				showMessage = "提示：数据异常，用户密码更新失败！";
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，用户密码更新失败！";
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
	 * 由管理员调用，可以更新该对象的所有信息
	 */
	public String updateTeacherInfoByAdmin(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_teachers set name=?,sex=?,subject_id=?,telephone=?,status=? "
				+ "where power='"
				+ teacher.getPower()
				+ "' and id='"
				+ teacher.getUserId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			int _account=1;
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(_account++, teacher.getName());
			ps.setString(_account++, teacher.getSex());
			ps.setInt(_account++, teacher.getSubject().getId());
			ps.setString(_account++, teacher.getTelephone());
			ps.setInt(_account++, teacher.getStatus().ordinal());
			int rs = ps.executeUpdate();
			if (rs == 1) {
				showMessage = "提示：用户账号信息更新成功！";
				conn.commit();
			} else {
				showMessage = "提示：数据异常，用户账号信息更新失败！";
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，用户账号信息更新失败！";
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
	 * 根据指定的权限，返回所有的教师
	 */
	public List<Teacher> getAllTeacher(String power) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Teacher> list = new ArrayList<Teacher>();
		String sql = "select t_teachers.id,password,name,sex,telephone,power,status,t_subjects.id,code "
				+ "from t_teachers,t_subjects where power='"
				+ power
				+ "' and subject_id=t_subjects.id ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Teacher teacher = null;
			Subject subject = null;
			while (rs.next()) {
				subject = new Subject();
				subject.setId(rs.getInt("t_subjects.id"));
				subject.setCode(rs.getString("code"));
				teacher = new Teacher();
				teacher.setUserId(rs.getString("t_teachers.id"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setPower(rs.getString("power"));
				teacher.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
				teacher.setSubject(subject);
				list.add(teacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public String deleteTeacherById(String userId) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "delete from t_teachers where id='" + userId + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (!isExistedTeacher(stmt, userId)) {
				showMessage = "删除失败：账号--" + userId + "--不存在,！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：账号--" + userId + "--删除成功！";
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
				System.out.println("删除监考员，回滚事务失败！");
			}
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		return showMessage;
	}

	public String createTeacherByAdmin(Teacher teacher) {
		String showMessage = null;
		String sql = "insert into t_teachers(id,password,name,sex,subject_id,telephone,power,status) values(?,?,?,?,?,?,?,?)";
		// sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedTeacher(ps, teacher.getUserId())) {
				showMessage = "提示：账号--" + teacher.getUserId() + "--已经注册！";
			} else {
				int _account=1;
				ps.setString(_account++, teacher.getUserId());
				ps.setString(_account++, teacher.getPassword());
				ps.setString(_account++, teacher.getName());
				ps.setString(_account++, teacher.getSex());
				ps.setInt(_account++, teacher.getSubject().getId());
				ps.setString(_account++, teacher.getTelephone());
				ps.setString(_account++, teacher.getPower());
				ps.setInt(_account++, teacher.getStatus().ordinal());
				rs = ps.executeUpdate();
				if (rs == 1) {
					conn.commit();
					showMessage = "提示：账号--" + teacher.getUserId() + "--创建成功！";
				} else {
					showMessage = "提示：账号异常,新账户创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("删除监考员，回滚事务失败！");
			}
			showMessage = "提示：数据异常,新账户创建失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}

	/*
	 * 不提供直接调用，提供子类调用
	 */
	public Teacher getTeacherById(String userId, int power) {
		SQLUtil sqlUtil = new SQLUtil();
		Teacher teacher = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select password,name,sex,telephone,status,power,t_subjects.id,code "
				+ "from t_teachers,t_subjects where t_subjects.id=t_teachers.subject_id and t_teachers.id='"
				+ userId + "'";
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getInt("t_subjects.id"));
				subject.setCode(rs.getString("code"));
				teacher = new Teacher();
				teacher.setUserId(userId);
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));// 男：1 ；女0；
				teacher.setSubject(subject);
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setPower(rs.getString("power"));
				teacher.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			teacher = null;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return teacher;
	}

	public List<Teacher> getTeacherIdsBySubjectId(int subject_id) {
		List<Teacher> teachers = new ArrayList<Teacher>();
		String sql = "select t_teachers.id,t_subjects.code from t_teachers,t_subjects where t_teachers.subject_id=t_subjects.id and t_teachers.subject_id="+subject_id;
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Teacher t = null;
		Subject subject = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				t = new Teacher();
				String id = rs.getString("id");
				t.setUserId(id);
				String code = rs.getString("code");
				subject = new Subject();
				subject.setCode(code);
				t.setSubject(subject);
				teachers.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return teachers;
	}

	/**
	 *	@see 需要返回教师的科目代码
	 *		主要是为了满足管理员获取教室的试卷时显示的试卷科目名称
	 *	@return List<Teacher(id,code)> 教室编号、科目代码
	 */
	public List<Teacher> getAllProfessionIds() {
		List<Teacher> teachers = new ArrayList<Teacher>();
		String sql = "select t_teachers.id,t_subjects.code from t_teachers,t_subjects where t_teachers.subject_id=t_subjects.id";
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Teacher t = null;
		Subject subject = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				t = new Teacher();
				String id = rs.getString("id");
				t.setUserId(id);
				String code = rs.getString("code");
				subject = new Subject();
				subject.setCode(code);
				t.setSubject(subject);
				teachers.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return teachers;
	}

}
