/**
 **作者：翁加林
 **时间：2012-7-25
 **文件名：Admin_account_profession.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;

public class Account_profession extends HttpServlet {
	private static final long serialVersionUID = -4703603947644583316L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProfessionServiceImp professionService = new ProfessionServiceImp(); 
		List<Teacher> professions = professionService.getAllProfessions();
		SubjectServiceImp subjectService = new SubjectServiceImp();
		List<Subject> subjects = subjectService.getAllSubjects();
		request.setAttribute("professions", professions);
		request.setAttribute("subjects", subjects);
		request.getRequestDispatcher("/teacher/admin/account_profession.jsp")
				.forward(request, response);
	}
}
