/**
 **作者：翁加林
 **时间：2012-8-1
 **文件名：Admin_name_career_update.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
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
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_career_update extends HttpServlet {
	private static final long serialVersionUID = -3692644170098692967L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String careerId = request.getParameter("careerId");
		int career_id = -1;
		try {
			career_id = Integer.parseInt(careerId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(CareerSubjectSelected.class,"id", career_id);
		// 验证客户端资料
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			Career career = careerServiceImp.getCareerById(career_id);
			if (career == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:指定专业不存在！";
			} else {
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				GradeServiceImp gradeServiceImp = new GradeServiceImp();
				List<Department> departments = departmentServiceImp.getAllDepartments();
				List<Grade> grades = gradeServiceImp.getAllGrades();
				request.setAttribute("departments", departments);
				request.setAttribute("grades", grades);
				request.setAttribute("career", career);
				request.getRequestDispatcher("/teacher/admin/name_career_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_career").forward(
				request, response);
	}
}
