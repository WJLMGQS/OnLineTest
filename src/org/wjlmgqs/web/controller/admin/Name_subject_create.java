/**
 **作者：翁加林
 **时间：2012-7-22
 **文件名：Admin_name_createSubject.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_subject_create extends HttpServlet {
	private static final long serialVersionUID = 2553265130445908449L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String code = request.getParameter("code");
		showMessage = BeanValidateUtil.validatorVoProperty(Subject.class,"code", code);
		if(showMessage == null){
			SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
			Subject subject = new Subject();
			subject.setCode(code);
			showMessage = subjectServiceImp.createSubject(subject);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_subject")
				.forward(request, response);
	}
}
