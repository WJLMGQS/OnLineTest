/**
 **作者：翁加林
 **时间：2012-8-2
 **文件名：Admin_name_classes.java
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

import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;

public class Name_classes extends HttpServlet {
	private static final long serialVersionUID = 6120763069476717019L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClassesServiceImp classesServiceImp = new ClassesServiceImp();
		List<Classes> classess = classesServiceImp.getAllClassess();
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		GradeServiceImp gradeServiceImp = new GradeServiceImp();
		List<Grade> grades = gradeServiceImp.getAllGrades();
		System.out.println("classes:"+classess.size());
		request.setAttribute("classess", classess);
		request.setAttribute("departments", departments);
		request.setAttribute("grades", grades);
		request.getRequestDispatcher("/teacher/admin/name_classes.jsp").forward(
				request, response);
		return;
	}
}
