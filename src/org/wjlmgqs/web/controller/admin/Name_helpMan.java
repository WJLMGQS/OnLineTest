/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Admin_name_helpMan.java
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
import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.HelpManServiceImp;

public class Name_helpMan extends HttpServlet {
	private static final long serialVersionUID = -910325011984325649L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
		List<HelpMan> helpMans = helpManServiceImp.getAllHelpMans();
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		request.setAttribute("helpMans", helpMans);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/teacher/admin/name_helpMan.jsp").forward(request, response);
		return;
	}
}
