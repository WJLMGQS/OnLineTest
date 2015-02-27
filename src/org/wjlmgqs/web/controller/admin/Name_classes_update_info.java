/**
 **作者：翁加林
 **时间：2012-8-2
 **文件名：Admin_name_classes_update_info.java
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

public class Name_classes_update_info extends HttpServlet {
	private static final long serialVersionUID = 367488485737770812L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String classId = request.getParameter("classId");
		String careerId = request.getParameter("careerId");
		String helpId = request.getParameter("helpId");
		String code = request.getParameter("code");
		int class_id = -1;
		int career_id = -1;
		int help_id = -1;
		Career career = null;
		HelpMan helpMan = null;
		try {
			class_id = Integer.parseInt(classId);
			career_id = Integer.parseInt(careerId);
		} catch (Exception e) {}	
		try {	
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			career = careerServiceImp.getCareerById(career_id);
		} catch (Exception e) {}	
		try {	
			help_id = Integer.parseInt(helpId);
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
			helpMan = helpManServiceImp.getHelpManById(help_id);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Classes.class,
				new String[]{"career","helpMan","id","code"},
				new Object[]{career,helpMan,class_id,code});
		// 检测格式及正确性
		if (showMessage == null) {
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			Classes classes = classesServiceImp.getClassesById(class_id);
			if (classes == null) {
				showMessage = "错误信息：指定班级不存在！";
			} else {
				classes.setCode(code.trim());
				classes.setCareer(career);
				classes.setHelpMan(helpMan);
				showMessage = classesServiceImp.updateClassesInfo(classes);
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_classes_update?classId=" + classId)
				.forward(request, response);
	}
}
