/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_grade_create.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_grade_create extends HttpServlet {
	private static final long serialVersionUID = 8482882473908753277L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String code = request.getParameter("code");
		showMessage = BeanValidateUtil.validatorVoProperty(Grade.class,"code", code);
		if (showMessage == null) {
			Grade grade = new Grade();
			grade.setCode(code);
			GradeServiceImp gradeServiceImp = new GradeServiceImp();
			showMessage = gradeServiceImp.createGrade(grade);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_grade").forward(request, response);
	}

}
