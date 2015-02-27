/**
 **作者：翁加林
 **时间：2012-8-9
 **文件名：Account_professionSelf_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest10
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Account_professionSelf_update extends HttpServlet {
	private static final long serialVersionUID = -2329912987262386055L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/teacher/profession/account_professionSelf_update.jsp").forward(request,response);
	}

}
