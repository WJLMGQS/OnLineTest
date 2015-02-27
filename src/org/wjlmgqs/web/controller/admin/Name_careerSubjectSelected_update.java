/**
 **作者：翁加林
 **时间：2012-8-5
 **文件名：Admin_name_classSubjectSelected_update.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest06
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.CareerSubjectSelected;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.CareerSubjectSelectedServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_careerSubjectSelected_update extends HttpServlet {
	private static final long serialVersionUID = -1411497078958861011L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String cssId = request.getParameter("cssId");
		int css_id = -1;
		try {
			css_id = Integer.parseInt(cssId);
		} catch (NumberFormatException e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(CareerSubjectSelected.class,"id", css_id);
		// 验证客户端资料
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			CareerSubjectSelectedServiceImp careerSubjectSelectedServiceImp = new CareerSubjectSelectedServiceImp();
			CareerSubjectSelected careerSubjectSelected = careerSubjectSelectedServiceImp.getCareerSubjectSelectedById(css_id);
			if (careerSubjectSelected == null) {
				showMessage = "错误信息:指定选修记录不存在！";
			} else {
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				GradeServiceImp gradeServiceImp = new GradeServiceImp();
				CareerServiceImp careerServiceImp = new CareerServiceImp();
				SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
				List<Department> departments = departmentServiceImp.getAllDepartments();
				List<Grade> grades = gradeServiceImp.getAllGrades();
				List<Career> careers = careerServiceImp.getAllCareersByDepartmentId_GradeId(careerSubjectSelected.getCareer().getDepartment().getId(),careerSubjectSelected.getCareer().getGrade().getId());
				List<Subject> subjects = subjectServiceImp.getAllSubjects();
				request.setAttribute("departments", departments);
				request.setAttribute("grades", grades);
				request.setAttribute("careers", careers);
				request.setAttribute("subjects", subjects);
				request.setAttribute("careerSubjectSelected", careerSubjectSelected);
				request.getRequestDispatcher("/teacher/admin/name_careerSubjectSelected_update.jsp").forward(
						request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_careerSubjectSelected").forward(
				request, response);
	}
}
