/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Admin_name_department.java
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
import org.wjlmgqs.service.impl.DepartmentServiceImp;

public class Name_department extends HttpServlet {
	private static final long serialVersionUID = -8541352877325313288L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/teacher/admin/name_department.jsp")
				.forward(request, response);
		return;
	}
}
