package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.TestPaper;

public class Paper_buliding extends HttpServlet {

	/**
	 * @see
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 列出所有的正在创建的试卷，也就是Session中的testPapers中的试卷对象
	 * */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 保存到Session容器中
		HttpSession session = request.getSession();
		List<TestPaper> testPapers = (List<TestPaper>) session.getAttribute("testPapers");
		if(testPapers==null || testPapers.size()==0){
			request.setAttribute("showMessage", "尚未创建任何试卷，请先创建试卷！");
		}
		// 为后面直接编辑当前试卷
		request.getRequestDispatcher("/teacher/profession/paper_buliding.jsp").forward(request, response);
	}

}
