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
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.enums.UserAccountStatus;

public class StudentDaoImp {
	public boolean isExistedStudent(Statement stmt, String userId) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_students where id='" + userId + "'";
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

	/**
	 * @see 获取指定的学生对象，封装班级、科目对象信息
	 */
	public Student getStudentById(String user_id) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select t_students.id,t_students.password,t_students.name,t_students.sex,t_students.telephone,t_students.photo,t_students.status,  "
				+ "t_classes.id,t_classes.code,  t_careers.id,t_careers.code,  t_grades.id,t_grades.code,  A.id,A.code,  B.id,B.code,  t_helpMans.id,t_helpMans.code "
				+ "from t_students,t_classes,t_careers,t_grades,t_departments A,t_departments B,t_helpMans "
				+ "where t_careers.department_id=A.id and t_grades.id=t_careers.grade_id and t_classes.career_id=t_careers.id"
				+ " and t_classes.help_id=t_helpMans.id and t_helpMans.department_id=B.id and t_classes.id=t_students.class_id and t_students.id='"
				+ user_id + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		Classes classes = null;
		Grade grade = null;
		Department departmentA, departmentB = null;
		Career career = null;
		HelpMan helpMan = null;
		Student student = null;
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
				
				student = new Student();
				student.setUserId(rs.getString("id"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setTelephone(rs.getString("telephone"));
				student.setPhoto(rs.getString("photo"));
				student.setClasses(classes);
				student.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return student;
	}


	public List<Student> getAllStudents() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Student> list = new ArrayList<Student>();
		String sql = "select t_students.id,t_students.password,t_students.name,t_students.sex,t_students.telephone,t_students.photo,t_students.status,  "
			+ "t_classes.id,t_classes.code,  t_careers.id,t_careers.code,  t_grades.id,t_grades.code,  A.id,A.code,  B.id,B.code,  t_helpMans.id,t_helpMans.code "
			+ "from t_students,t_classes,t_careers,t_grades,t_departments A,t_departments B,t_helpMans "
			+ "where t_careers.department_id=A.id and t_grades.id=t_careers.grade_id and t_classes.career_id=t_careers.id"
			+ " and t_classes.help_id=t_helpMans.id and t_helpMans.department_id=B.id and t_classes.id=t_students.class_id ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Classes classes = null;
		Grade grade = null;
		Department departmentB,departmentA = null;
		Career career = null;
		HelpMan helpMan = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Student student = null;
			while (rs.next()) {
				grade = new Grade();
				grade.setId(rs.getInt("t_grades.id"));
				grade.setCode(rs.getString("t_grades.code"));
				
				departmentA = new Department();
				departmentA.setCode(rs.getString("A.code"));
				departmentA.setId(rs.getInt("A.id"));
				
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

				student = new Student();
				student.setUserId(rs.getString("id"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setTelephone(rs.getString("telephone"));
				student.setPhoto(rs.getString("photo"));
				student.setClasses(classes);
				student.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
				
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public int getAllStudentsNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_students ";
		return sqlUtil.getTableNumber(sql);
	}
	
	/*
	 * 创建学生，部分属性采用默认形式
	 */
	public String createStudent(Student student) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_students(id,password,name,sex,telephone,photo,class_id,status) values(?,?,?,?,?,?,?,?)";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = DBUtils.getConnection();
	
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedStudent(ps, student.getUserId())) {
				showMessage = "提示：账号--" + student.getUserId() + "--已经注册！";
			} else {
				int _account = 1;
				ps.setString(_account++, student.getUserId());
				ps.setString(_account++, student.getPassword());
				ps.setString(_account++, student.getName());
				ps.setString(_account++, student.getSex());
				ps.setString(_account++, student.getTelephone());
				ps.setString(_account++, student.getPhoto());
				ps.setInt(_account++, student.getClasses().getId());
				ps.setInt(_account++, student.getStatus().ordinal());
				rs = ps.executeUpdate();
				if (rs == 1) {
					conn.commit();
					student.setStatus(UserAccountStatus.ACTIVE);
					showMessage = "提示：账号--" + student.getUserId() + "--创建成功！";
				} else {
					showMessage = "提示：注册信息有误,新账户创建失败！";
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
	 * 更新学生照片
	 */
	public boolean updateStudentPhoto(Student student) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean result = false;
		String sql = "update t_students set photo=? where id='"
				+ student.getUserId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, student.getPhoto());
			int rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				result = true;
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return result;
	}

	public String updateStudentInfo(Student student) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_students set name=?,sex=?,telephone=?,class_id=?,status=? "
				+ " where id='" + student.getUserId() + "'";
			sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, student.getName());
			ps.setString(_account++, student.getSex());
			ps.setString(_account++, student.getTelephone());
			ps.setInt(_account++, student.getClasses().getId());
			ps.setInt(_account++, student.getStatus().ordinal());
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

	public String updateStduentPwd(String fresh, String userId) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_students set password=? where id='" + userId
				+ "'";
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

	public Student studentLogin(String userId, String password) {
		SQLUtil sqlUtil = new SQLUtil();
		Student student = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select password,name,sex,telephone,photo,class_id,status "
				+ "from t_students where  t_students.id='"
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
					student = new Student();
					student.setUserId(userId);
					student.setPassword(rs.getString("password"));
					student.setName(rs.getString("name"));
					student.setSex(rs.getString("sex"));// 男：1 ；女0；
					student.setTelephone(rs.getString("telephone"));
					student.setPhoto(rs.getString("photo"));
					student.setStatus(UserAccountStatus.values()[rs.getInt("status")]);
					int clessesId = rs.getInt("class_id");
					ClassesDaoImp classesDaoImp = new ClassesDaoImp();
					Classes classes = classesDaoImp.getClassesById(clessesId);
					student.setClasses(classes);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			student = null;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return student;
	}


}
