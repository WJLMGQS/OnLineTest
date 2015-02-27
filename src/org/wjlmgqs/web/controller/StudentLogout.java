/**
**作者：翁加林
**时间：2012-7-18
**文件名：AdminLogout.java
**包名：org.wjlmgqs.web.controller
**工程名：OnLineTest01
*/


package org.wjlmgqs.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentLogout extends HttpServlet {

	private static final long serialVersionUID = -6444165409549326162L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();//Session失效
		request.setAttribute("showMessage","成功退出！3秒后自动跳转到登录界面<meta http-equiv='refresh' content=3;URL='"+request.getContextPath()+
				"/studentLogin'><script language='JavaScript'>if (window != top)top.location.href = location.href;</script>");
		request.getRequestDispatcher("/showMessage.jsp").forward(request, response);
		return;
	}

}
