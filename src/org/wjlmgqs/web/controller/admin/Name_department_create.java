/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Admin_name_department_create.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_department_create extends HttpServlet {
	private static final long serialVersionUID = 5179960974324464285L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String code = request.getParameter("code");
		showMessage = BeanValidateUtil.validatorVoProperty(Department.class,"code", code);
		if (showMessage==null) {
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			Department department = new Department();
			department.setCode(code);
			showMessage = departmentServiceImp.createDepartment(department);
		}
		request.setAttribute("showMessage",showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_department").forward(request, response);
	}
}
