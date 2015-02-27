/**
 **@author wjlmgqs
 **下午11:57:06
 **Start_singles.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.daomain.TestUnit;
import org.wjlmgqs.web.util.WebLogger;

/**
 * @see 根据参数类型从Session中获取当前指定试题类型的试题集合
 */
public class Start_TestUnits extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String type = request.getParameter("type");
		int typeValue = 0;
		try {
			typeValue = Integer.parseInt(type);
			if(typeValue<0 || typeValue>3){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			showMessage = "错误信息：您指定的考试题型不存在！";
			WebLogger.showInfor("您指定的考试题型不存在！");
		}
		HttpSession session = request.getSession();
		TestPaper testPaper = (TestPaper) session.getAttribute("testPaper");
		if(showMessage!=null || testPaper==null){//试题类型不存在，跳转到失败页面显示消息
			request.setAttribute("showMessage",showMessage);
			request.getRequestDispatcher("/actionFailed.jsp").forward(request, response);
			return;
		}
		List<? extends TestUnit> testUnits = null;
		String testType = null;
		switch(typeValue){
			case 0://单选题
				testType = "singles";
				testUnits = testPaper.getSingles();
				break;
			case 1://多选题
				testType = "multiples";
				testUnits = testPaper.getMultiples();
				break;
			case 2://判断题
				testType = "judges";
				testUnits = testPaper.getJudges();
				break;
			case 3://填空题
				testType = "fillBlanks";
				testUnits = testPaper.getFillBlanks();
				break;
		}
		request.setAttribute(testType, testUnits);
		request.getRequestDispatcher("/student/start_"+testType+".jsp").forward(request, response);
	}
	
}
