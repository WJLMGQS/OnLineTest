/**
 **作者：翁加林
 **时间：2012-7-24
 **文件名：Admin_main_rightShow.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest02
 */

package org.wjlmgqs.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Student_persion_home extends HttpServlet {

	private static final long serialVersionUID = 3319660322125700717L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/student/student_persion_home.jsp")
				.forward(request, response);
	}

}
