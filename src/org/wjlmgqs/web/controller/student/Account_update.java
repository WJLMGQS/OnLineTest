/**
 **@author wjlmgqs
 **上午12:11:38
 **Account_update.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Account_update extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/student/account_update.jsp").forward(request, response);
	}

	private static final long serialVersionUID = 1L;

	
}
