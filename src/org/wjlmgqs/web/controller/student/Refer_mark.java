/**
 **@author wjlmgqs
 **下午2:38:08
 **Refer_mark.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ScopeMark;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.service.impl.ScopeMarkServiceImp;

public class Refer_mark extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("studentRole");
		ScopeMarkServiceImp scopeMarkServiceImp = new ScopeMarkServiceImp();
		List<ScopeMark> scopeMarks = scopeMarkServiceImp.getAllMarkByStudent(student);
		request.setAttribute("scopeMarks", scopeMarks);
		request.getRequestDispatcher("/student/refer_mark_list.jsp").forward(request,response);
	
	}
}
