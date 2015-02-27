/**
 **作者：翁加林
 **时间：2012-8-1
 **文件名：Admin_name_career_update_info.java
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

public class Name_career_update_info extends HttpServlet {
	private static final long serialVersionUID = -6855361337639199365L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String careerId = request.getParameter("careerId");
		String gradeId = request.getParameter("gradeId");
		String departmentId = request.getParameter("departmentId");
		String code = request.getParameter("code");
		// 检测格式及正确性
		int career_id =-1;
		int grade_id =-1;
		int department_id =-1;
		Grade grade = null;
		Department department = null;
		try {
			career_id = Integer.parseInt(careerId);
		} catch (Exception e) {}	
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
				new String[]{"id","grade","department","code"},
				new Object[]{career_id,grade,department,code});
		if (showMessage == null) {
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			Career career = careerServiceImp.getCareerById(career_id);
			if (career == null) {
				showMessage = "专业代码不存在！";
			} else {
				career.setCode(code.trim());
				career.setGrade(grade);
				career.setDepartment(department);
				showMessage = careerServiceImp.updateCareerInfo(career);
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_career_update?careerId=" + careerId)
				.forward(request, response);
	}
}
