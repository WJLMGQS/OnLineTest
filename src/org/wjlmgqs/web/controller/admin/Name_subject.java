/**
 **作者：翁加林
 **时间：2012-7-22
 **文件名：Admin_name_subjects.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
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

public class Name_subject extends HttpServlet {
	private static final long serialVersionUID = 702815268667144472L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
		List<Subject> subjects = subjectServiceImp.getAllSubjects();
		request.setAttribute("subjects", subjects);
		request.getRequestDispatcher("/teacher/admin/name_subject.jsp")
				.forward(request, response);
		return;
	}
}
