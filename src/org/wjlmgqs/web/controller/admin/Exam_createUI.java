/**
 **@author wjlmgqs
 **上午12:15:49
 **Exam_createUI.java
 **org.wjlmgqs.web.controller.admin
 **OnLineTest
 */
package org.wjlmgqs.web.controller.admin;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.SubjectServiceImp;

public class Exam_createUI extends HttpServlet {

	private static final long serialVersionUID = -4788753616183640967L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 创建新考试记录，(科目，完成时间，总分)
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取知识点
		SubjectServiceImp subjectServiceImp = new SubjectServiceImp(); 
		List<Subject> subjects = subjectServiceImp.getAllSubjects();
		request.setAttribute("subjects",subjects);
		request.getRequestDispatcher("/teacher/admin/exam_record_build_params.jsp").forward(request, response);
	}
}
