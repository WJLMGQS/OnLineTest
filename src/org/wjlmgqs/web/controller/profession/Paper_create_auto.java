/**
 **@author wjlmgqs
 **上午12:20:55
 **Paper_create_auto.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest21
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

public class Paper_create_auto extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 创建新试卷，标注试卷创建者信息(depository_id)"
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取当前用户所对应的所有知识点
		KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
		request.setAttribute("knowledges",knowledges);
		request.getRequestDispatcher("/teacher/profession/paper_autoBulid_params.jsp").forward(request, response);
	}
}
