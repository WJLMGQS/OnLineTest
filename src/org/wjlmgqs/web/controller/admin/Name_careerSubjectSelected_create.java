/**
 **作者：翁加林
 **时间：2012-8-5
 **文件名：Admin_name_classSubjectSelected_create.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest06
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.CareerSubjectSelected;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.CareerSubjectSelectedServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_careerSubjectSelected_create extends HttpServlet {
	private static final long serialVersionUID = 8804745722719385566L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * 创建选修只要班级和科目对象就好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String careerId = request.getParameter("careerId");
		String subjectId = request.getParameter("subjectId");
		int career_id =-1;
		int subject_id =-1;
		Career career = null;
		Subject subject = null;
		try {
			career_id = Integer.parseInt(careerId);
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			career = careerServiceImp.getCareerById(career_id);
		} catch (Exception e) {}	
		try {
			subject_id = Integer.parseInt(subjectId);
			SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
			subject = subjectServiceImp.getSubjectById(subject_id);
		} catch (NumberFormatException e1) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				CareerSubjectSelected.class,
				new String[]{"career","subject"},
				new Object[]{career,subject});
		if (showMessage == null) {
			CareerSubjectSelected careerSubjectSelected = new CareerSubjectSelected();
			careerSubjectSelected.setCareer(career);
			careerSubjectSelected.setSubject(subject);
			CareerSubjectSelectedServiceImp careerSubjectSelectedServiceImp = new CareerSubjectSelectedServiceImp();
			showMessage = careerSubjectSelectedServiceImp.createCareerSubjectSelected(careerSubjectSelected);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/admin/name_careerSubjectSelected").forward(
				request, response);
	}

}
