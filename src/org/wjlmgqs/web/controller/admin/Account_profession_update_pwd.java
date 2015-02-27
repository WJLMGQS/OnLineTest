/**
 **作者：翁加林
 **时间：2012-7-25
 **文件名：Admin_account_profession_update_pwd.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;

public class Account_profession_update_pwd extends HttpServlet {
	private static final long serialVersionUID = 3066828250825177516L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		String freshF = request.getParameter("freshF");
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Teacher.class,
				new String[]{"userId","password"},
				new Object[]{userId,freshF});
		
		if (showMessage == null) {
			MD5Code code = new MD5Code();
			String fresh = code.getMD5ofStr(freshF);// 产生新的存储到数据库的密文
			// 存入数据库
			ProfessionServiceImp professionService = new ProfessionServiceImp(); 
			showMessage = professionService.updateProfessionPwd(fresh,userId);
		}
		// System.out.println("showMessage:"+showMessage);
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_profession_update?userId=" + userId).forward(
				request, response);
	}
}
