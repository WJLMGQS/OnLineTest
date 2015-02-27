/**
 **@author wjlmgqs
 **下午10:47:21
 **Paper_judges_add.java
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
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;

public class Paper_judges_add extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 转发到插入判断题页面
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp();
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("knowledges", knowledges);
		request.getRequestDispatcher("/teacher/profession/paper_judges_add.jsp").forward(request,response);
	}

}
