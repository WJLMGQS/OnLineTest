/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：Name_knowledge_create.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_knowledge_create extends HttpServlet {
	private static final long serialVersionUID = -136829780405161285L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String code = request.getParameter("code");
		showMessage = BeanValidateUtil.validatorVoProperty(Knowledge.class,"code", code);
		if (showMessage == null) {
			Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			Knowledge knowledge = new Knowledge();
			knowledge.setCode(code.trim());
			knowledge.setTeacher(teacher);
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
			showMessage = knowledgeServiceImp.createKnowledge(knowledge);
		}
		request.setAttribute("hiddenDivContentStatus", "show");
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/name_knowledge").forward(
				request, response);
	}
}
