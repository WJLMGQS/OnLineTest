/**
 **作者：翁加林
 **时间：2012-8-5
 **文件名：Admin_name_classSubjectSelected_update_info.java
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

public class Name_careerSubjectSelected_update_info extends HttpServlet {
	private static final long serialVersionUID = -4127060882608748226L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String cssId = request.getParameter("cssId");
		String careerId = request.getParameter("careerId");
		String subjectId = request.getParameter("subjectId");
		// 检测格式及正确性
		int career_id =-1;
		int subject_id =-1;
		int css_id =-1;
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
		} catch (Exception e) {}	
		try {
			css_id = Integer.parseInt(cssId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				CareerSubjectSelected.class,
				new String[]{"subject","career","id"},
				new Object[]{subject,career,css_id});
		if (showMessage == null) {
			CareerSubjectSelectedServiceImp careerSubjectSelectedServiceImp = new CareerSubjectSelectedServiceImp();
			CareerSubjectSelected careerSubjectSelected = careerSubjectSelectedServiceImp.getCareerSubjectSelectedById(css_id);
			if (careerSubjectSelected == null) {
				showMessage = "选修记录不存在！";
			} else {
				careerSubjectSelected.setSubject(subject);
				careerSubjectSelected.setCareer(career);
				showMessage = careerSubjectSelectedServiceImp.updateCareerSubjectSelectedInfo(careerSubjectSelected);
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_careerSubjectSelected_update?cssId=" + cssId)
				.forward(request, response);
	}
}
