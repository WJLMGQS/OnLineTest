package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.TestPaper;

public class Paper_judges extends HttpServlet {

	/**
	 * @see
	 */
	private static final long serialVersionUID = 1450504623621521201L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取试卷对象以及单选试题对象集
		TestPaper testPaper = (TestPaper) request.getSession().getAttribute("testPaper");
		List<Judge> judges = testPaper.getJudges();
		// 跳转到判断题显示界面
		request.setAttribute("judges", judges);
		request.getRequestDispatcher("/teacher/profession/paper_judges.jsp").forward(request, response);
	}

}
