/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_department_update.java
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

public class Name_department_update extends HttpServlet {
	private static final long serialVersionUID = -2035142944913912111L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String departmentId = request.getParameter("departmentId");
		int department_id =-1;
		try {
			department_id = Integer.parseInt(departmentId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(Department.class,"id", department_id);
		// 验证客户端资料
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			Department department = departmentServiceImp.getDepartmentById(department_id);
			if (department == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:指定学院不存在！";
			} else {
				request.setAttribute("department", department);
				request.getRequestDispatcher("/teacher/admin/name_department_update.jsp").forward(
						request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_department").forward(
				request, response);
	}
}
