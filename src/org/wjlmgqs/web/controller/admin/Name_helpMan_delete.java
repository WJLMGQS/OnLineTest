/**
 **作者：翁加林
 **时间：2012-7-27
 **文件名：Admin_name_helpMan_delete.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.HelpManServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_helpMan_delete extends HttpServlet {

	private static final long serialVersionUID = 2336530097634403498L;

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
		} catch (Exception e1) {}
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorVoProperty(HelpMan.class,"id",help_id);
		// 验证客户端资料
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			showMessage = helpManServiceImp.deleteHelpManById(help_id);
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_helpMan")
				.forward(request, response);
	}
}
