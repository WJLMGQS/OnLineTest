/**
 **作者：翁加林
 **时间：2012-8-9
 **文件名：Profession_main_leftMenu.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest10
 */

package org.wjlmgqs.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Profession_main_leftMenu extends HttpServlet {
	private static final long serialVersionUID = -1489142633235499066L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/teacher/profession_main_leftMenu.jsp").forward(request, response);
	}
}
