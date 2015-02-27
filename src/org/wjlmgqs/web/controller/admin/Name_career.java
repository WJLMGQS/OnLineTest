/**
 **作者：翁加林
 **时间：2012-8-1
 **文件名：Admin_name_career.java
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

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;

public class Name_career extends HttpServlet {
	private static final long serialVersionUID = 5656671426800050194L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CareerServiceImp careerServiceImp = new CareerServiceImp();
		List<Career> careers = careerServiceImp.getAllCareers();
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		GradeServiceImp gradeServiceImp = new GradeServiceImp();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		List<Grade> grades = gradeServiceImp.getAllGrades();
		request.setAttribute("careers", careers);
		request.setAttribute("departments", departments);
		request.setAttribute("grades", grades);
		request.getRequestDispatcher("/teacher/admin/name_career.jsp").forward(request, response);
		return;
	}
}
