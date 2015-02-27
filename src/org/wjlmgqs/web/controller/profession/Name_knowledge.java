/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：Name_knowledge.java
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
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;

public class Name_knowledge extends HttpServlet {
	private static final long serialVersionUID = 6823423524906062752L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/name_knowledge.jsp").forward(request,
				response);
	}

}
