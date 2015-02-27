/**
 **@author wjlmgqs
 **下午11:57:04
 **Paper_judges_insert.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest20.1
 */
package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.service.impl.JudgeServiceImp;

/**
 */
public class Paper_judges_insert extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 转发到插入新单选题页面
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("Ids");
		String showMessage = null;
		if (ids == null || ids.length() <= 0) {
			showMessage = "提示：插入试题失败！";
		} else {
			String[] Ids = ids.split(",");
			JudgeServiceImp judgeServiceImp = new JudgeServiceImp();
			List<Judge> judges = judgeServiceImp.getJudgesByIds(Ids,(Teacher) request.getSession().getAttribute("professionTeacher"));
			if (judges == null) {
				showMessage = "提示：数据库异常，试题插入失败！";
			} else if (judges.size() <= 0) {
				showMessage = "提示：要插入的试题不存在，试题插入失败！";
			} else {
				TestPaper testPaper = (TestPaper) request.getSession().getAttribute("testPaper");
				List<Judge> _judges = testPaper.getJudges();
				for (Judge judge : judges) {
					_judges.add(judge);
				}
				request.getSession().setAttribute("testPaper", testPaper);
				showMessage = "提示：试题插入成功！";
			}
		}
		request.setAttribute("showMessage", showMessage);
		// 这里可能要同步Session中的testPapers对象中的本试卷试题内容
		request.getRequestDispatcher("/teacher/profession/paper_judges").forward(request, response);
	}
}