/**
**作者：翁加林
**时间：2012-8-21
**文件名：QuestionBank_fillBlank.java
**包名：org.wjlmgqs.web.controller.profession
**工程名：OnLineTest15
*/


package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.FillBlankServiceImp;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;

public class QuestionBank_fillBlank extends HttpServlet {

	private static final long serialVersionUID = -331993003915578222L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
		FillBlankServiceImp fillBlankService = new FillBlankServiceImp(); 
		List<FillBlank> fillBlanks = fillBlankService.getAllFillBlanksByTeacher(teacher);
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("fillBlanks", fillBlanks);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/questionBank_fillBlank.jsp")
				.forward(request, response);
	}
	
}
