/**
 **作者：翁加林
 **时间：2012-7-27
 **文件名：Admin_name_helpMan_update_info.java
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

public class Name_helpMan_update_info extends HttpServlet {
	private static final long serialVersionUID = -6506011628475949460L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String helpId = request.getParameter("helpId");
		String code = request.getParameter("code");
		String departmentId = request.getParameter("departmentId");
		int help_id =-1;
		int department_id=-1;
		Department department = null;
		try {
			help_id = Integer.parseInt(helpId);
		} catch (Exception e) {}	
		try {
			department_id = Integer.parseInt(departmentId);
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			department = departmentServiceImp.getDepartmentById(department_id);
		} catch (Exception e) {}
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				HelpMan.class,
				new String[]{"id","code","department"},
				new Object[]{help_id,code,department});
		if (showMessage == null) {
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			HelpMan helpMan = helpManServiceImp.getHelpManById(help_id); 
			if(helpMan==null) {
				showMessage = "辅导员代码不存在！";
			}else {
				// 更新到数据库
				helpMan.setCode(code.trim());
				helpMan.setDepartment(department);
				showMessage = helpManServiceImp.updateHelpManInfo(helpMan);
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_helpMan_update?helpId=" + helpId).forward(request, response);
	}

}
