/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Admin_account_student.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;

public class Account_student extends HttpServlet {
	private static final long serialVersionUID = 4494499826313690708L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentServiceImp studentService = new StudentServiceImp();
		SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		GradeServiceImp gradeServiceImp = new GradeServiceImp();
		List<Student> students = studentService.getAllStudents();
		List<Subject> subjects = subjectServiceImp.getAllSubjects();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		List<Grade> grades = gradeServiceImp.getAllGrades();
		request.setAttribute("grades", grades);
		request.setAttribute("students", students);
		request.setAttribute("subjects", subjects);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/teacher/admin/account_student.jsp")
				.forward(request, response);
	}

}
