package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.TestPaper;

public class Paper_singles extends HttpServlet {

	private static final long serialVersionUID = -659547646050727545L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取试卷对象以及单选试题对象集
		TestPaper testPaper = (TestPaper) request.getSession().getAttribute("testPaper");
		List<Single> singles = testPaper.getSingles();
		//跳转到单选题显示界面
		request.setAttribute("singles", singles);
		request.getRequestDispatcher("/teacher/profession/paper_singles.jsp").forward(request, response);
	}

}
