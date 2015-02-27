/**
 **作者：翁加林
 **时间：2012-7-19
 **文件名：Admin_account_admin.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Account_adminSelf_update extends HttpServlet {
	private static final long serialVersionUID = 6313445136044101992L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/teacher/admin/account_adminSelf_update.jsp").forward(request, response);
	}

}
