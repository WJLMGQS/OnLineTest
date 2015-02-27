/**
 **作者：翁加林
 **时间：2012-7-27
 **文件名：Admin_name_helpMan_update.java
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
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_helpMan_update extends HttpServlet {

	private static final long serialVersionUID = -6963761083836787377L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String helpId = request.getParameter("helpId");
		int help_id =-1;
		try {
			help_id = Integer.parseInt(helpId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(HelpMan.class, "id", help_id);
		if (showMessage == null) {
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			HelpMan helpMan = helpManServiceImp.getHelpManById(help_id);
			if(helpMan==null){
				showMessage = "错误信息：指定的指导员不存在！";
			}else{
				// 数据库信息核对成功后删除，并返回删除结果
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				List<Department> departments = departmentServiceImp.getAllDepartments();
				request.setAttribute("departments", departments);
				request.setAttribute("helpMan", helpMan);
				request.getRequestDispatcher("/teacher/admin/name_helpMan_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_helpMan")
				.forward(request, response);
	}

}
