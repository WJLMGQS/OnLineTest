/**
 **作者：翁加林
 **时间：2012-8-2
 **文件名：Admin_name_classes_create.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.HelpManServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_classes_create extends HttpServlet {
	private static final long serialVersionUID = -95640493780553113L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * 创建班级只要专业和辅导员对象就好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String careerId = request.getParameter("careerId");
		String helpId = request.getParameter("helpId");
		String code = request.getParameter("code");
		int help_id = -1;
		int career_id = -1;
		HelpMan helpMan = null;
		Career career = null;
		try {
			help_id = Integer.parseInt(helpId);
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			helpMan = helpManServiceImp.getHelpManById(help_id);
		} catch (Exception e) {}	
		try {
			career_id = Integer.parseInt(careerId);
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			career = careerServiceImp.getCareerById(career_id);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Classes.class,
				new String[]{"career","helpMan","code"},
				new Object[]{career,helpMan,code});
		if (showMessage == null) {
			Classes classes = new Classes();
			classes.setCareer(career);
			classes.setCode(code);
			classes.setHelpMan(helpMan);
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			showMessage = classesServiceImp.createClasses(classes);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_classes").forward(
				request, response);
	}

}
