/**
 **作者：翁加林
 **时间：2012-8-11
 **文件名：QuestionBank_single.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.SingleServiceImp;

public class QuestionBank_single extends HttpServlet {
	private static final long serialVersionUID = 7708662288497104238L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
		SingleServiceImp singleService = new SingleServiceImp();
		List<Single> singles = singleService.getAllSinglesByTeacher(teacher);
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("singles", singles);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/questionBank_single.jsp").forward(request, response);
	}

}
