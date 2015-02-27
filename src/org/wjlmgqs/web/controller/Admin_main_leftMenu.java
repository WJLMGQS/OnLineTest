/**
 **作者：翁加林
 **时间：2012-7-24
 **文件名：Admin_main_leftMenu.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest02
 */

package org.wjlmgqs.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Admin_main_leftMenu extends HttpServlet {
	private static final long serialVersionUID = -2874603500007702811L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/teacher/admin_main_leftMenu.jsp").forward(request, response);
	}

}
