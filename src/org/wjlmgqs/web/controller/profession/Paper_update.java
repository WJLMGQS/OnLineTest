package org.wjlmgqs.web.controller.profession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.TestPaper;

public class Paper_update extends HttpServlet {
	/**
	 * @see
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TestPaper testPaper = (TestPaper)request.getSession().getAttribute("testPaper");
		if(testPaper==null){
			request.setAttribute("showMessage","您选择的试卷不存在，无法查看！");
			request.getRequestDispatcher("/actionFailed.jsp").forward(request, response);	
		}else{//转到查看试卷详细信息的界面(可以编辑)
			request.getRequestDispatcher("/teacher/profession/paper_update.jsp").forward(request, response);	
		}
	}

}
