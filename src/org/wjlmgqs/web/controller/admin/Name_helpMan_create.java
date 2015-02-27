/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Admin_name_helpMan_create.java
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
import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.HelpManServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_helpMan_create extends HttpServlet {
	private static final long serialVersionUID = -6660167685632578201L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String departmentId = request.getParameter("departmentId");
		String code = request.getParameter("code");
		int department_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		Department department = null;
		try {
			department_id= Integer.parseInt(departmentId);
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			department = departmentServiceImp.getDepartmentById(department_id);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(HelpMan.class,new String[]{"code","department"}, new Object[]{code,department});
		if (showMessage == null) {
			HelpMan help = new HelpMan();
			help.setCode(code);
			help.setDepartment(department);
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			showMessage = helpManServiceImp.createHelpMan(help);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_helpMan").forward(
				request, response);
	}
}
