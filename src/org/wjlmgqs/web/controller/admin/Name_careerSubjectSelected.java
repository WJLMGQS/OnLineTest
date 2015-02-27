/**
 **作者：翁加林
 **时间：2012-8-3
 **文件名：Admin_name_classSubjectSelected.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest04
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.CareerSubjectSelected;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.CareerSubjectSelectedServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;

public class Name_careerSubjectSelected extends HttpServlet {
	private static final long serialVersionUID = 5891177315450894241L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GradeServiceImp gradeServiceImp = new GradeServiceImp();
		DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
		SubjectServiceImp subjectService = new SubjectServiceImp();
		CareerSubjectSelectedServiceImp careerSubjectSelectedServiceImp = new CareerSubjectSelectedServiceImp();
		List<CareerSubjectSelected> careerSubjectSelecteds = careerSubjectSelectedServiceImp.getAllCareerSubjectSelecteds();
		List<Department> departments = departmentServiceImp.getAllDepartments();
		List<Subject> subjects = subjectService.getAllSubjects();
		List<Grade> grades = gradeServiceImp.getAllGrades();
		request.setAttribute("careerSubjectSelecteds", careerSubjectSelecteds);
		request.setAttribute("departments", departments);
		request.setAttribute("subjects", subjects);
		request.setAttribute("grades", grades);
		request.getRequestDispatcher("/teacher/admin/name_careerSubjectSelected.jsp").forward(request, response);
		return;
	}
}
