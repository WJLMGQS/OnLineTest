/**
 **作者：翁加林
 **时间：2012-8-20
 **文件名：QuestionBank_judge.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest14
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.JudgeServiceImp;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;

public class QuestionBank_judge extends HttpServlet {

	private static final long serialVersionUID = 1606024549877204169L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		JudgeServiceImp judgeService = new JudgeServiceImp();
		List<Judge> judges = judgeService.getAllJudgesByTeacher(teacher);
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp();
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("judges", judges);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/questionBank_judge.jsp").forward(request,response);
	}

}
