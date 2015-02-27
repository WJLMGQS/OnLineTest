/**
**作者：翁加林
**时间：2012-7-17
**文件名：StudentService.java
**包名：org.wjlmgqs.service.impl
**工程名：OnLineTest01
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.StudentDaoImp;
import org.wjlmgqs.daomain.Student;

public class StudentServiceImp {
	public Student getStudentById(String user_id) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.getStudentById(user_id);
	}
	
	public String updateStudentInfo(Student student) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.updateStudentInfo(student);
	}
	public String updateStduentPwd(String fresh, String userId) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.updateStduentPwd(fresh, userId);
	}
	public boolean updateStudentPhoto(Student student) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.updateStudentPhoto(student);
	}	
	
	public List<Student> getAllStudents() {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.getAllStudents();
	}

	public int getAllStudentsNumber() {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.getAllStudentsNumber();
	}

	
	/**
	 * @see 创建学生，部分属性采用默认形式
	 * */
	public String createStudent(Student student) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.createStudent(student);
	}

	/**
	 * @see 学生登录
	 */
	public Student studentLogin(String userId, String password) {
		StudentDaoImp dao = new StudentDaoImp();
		return dao.studentLogin(userId,password);
	}
}
