/**
**作者：翁加林
**时间：2012-8-16
**文件名：QuestionBank_multiple.java
**包名：org.wjlmgqs.web.controller.profession
**工程名：OnLineTest13
*/


package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.MultipleServiceImp;

public class QuestionBank_multiple extends HttpServlet {

	private static final long serialVersionUID = -2160205664650658532L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
		MultipleServiceImp multipleService = new MultipleServiceImp(); 
		List<Multiple> multiples = multipleService.getAllMultiplesByTeacher(teacher);
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("multiples", multiples);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/questionBank_multiple.jsp").forward(request, response);
	}
	
	
	

}
