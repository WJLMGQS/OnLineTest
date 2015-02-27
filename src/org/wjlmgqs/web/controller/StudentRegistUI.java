/**
 **作者：翁加林
 **时间：2012-7-18
 **文件名：UIService.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
 */

package org.wjlmgqs.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;

public class StudentRegistUI extends HttpServlet {

	private static final long serialVersionUID = -5728986812778351838L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		GradeServiceImp gradeServiceImp = new GradeServiceImp();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		List<Grade> grades = gradeServiceImp.getAllGrades();
		request.setAttribute("departments", departments);
		request.setAttribute("grades", grades);
		request.getRequestDispatcher("/student/studentRegist.jsp").forward(request, response);
	}

}
