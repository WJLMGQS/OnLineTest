/**
 **作者：翁加林
 **时间：2012-8-1
 **文件名：Admin_name_career_create.java
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
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_career_create extends HttpServlet {
	private static final long serialVersionUID = -1425423105285630325L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String gradeId = request.getParameter("gradeId");
		String departmentId = request.getParameter("departmentId");
		String code = request.getParameter("code");
		
		int grade_id =-1;
		int department_id =-1;
		Department department = null;
		Grade grade = null;
		try {
			grade_id = Integer.parseInt(gradeId);
			GradeServiceImp gradeServiceImp = new GradeServiceImp();
			grade = gradeServiceImp.getGradeById(grade_id);
		} catch (Exception e) {}	
		try {
			department_id = Integer.parseInt(departmentId);
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			department = departmentServiceImp.getDepartmentById(department_id);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Career.class,
				new String[]{"grade","department","code"},
				new Object[]{grade,department,code});
		
		if (showMessage==null) {
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			Career career = new Career();
			career.setCode(code);
			career.setGrade(grade);
			career.setDepartment(department);
			showMessage = careerServiceImp.createCareer(career);
		}
		request.setAttribute("hiddenDivContentStatus", "show");
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_career").forward(
				request, response);
	}

}
