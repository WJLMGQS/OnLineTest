/**
 **作者：翁加林
 **时间：2012-8-2
 **文件名：Admin_name_classes_update.java
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
import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.service.impl.HelpManServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_classes_update extends HttpServlet {
	private static final long serialVersionUID = 2779179812445548446L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String classId = request.getParameter("classId");
		int class_id = -1;
		try {
			class_id = Integer.parseInt(classId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(Classes.class,"id", class_id);
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			Classes classes = classesServiceImp.getClassesById(class_id);
			if (classes != null) {
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				GradeServiceImp gradeServiceImp = new GradeServiceImp();
				HelpManServiceImp helpManServiceImp = new HelpManServiceImp(); 
				CareerServiceImp careerServiceImp = new CareerServiceImp();
				List<Department> departments = departmentServiceImp.getAllDepartments();
				List<Grade> grades = gradeServiceImp.getAllGrades();
				List<Career> careers = careerServiceImp.getAllCareersByDepartmentId_GradeId(classes.getCareer().getDepartment().getId(),classes.getCareer().getGrade().getId());
				List<HelpMan> helpMans = helpManServiceImp.getAllHelpMansByDepartmentId(classes.getHelpMan().getDepartment().getId());
				request.setAttribute("departments", departments);
				request.setAttribute("grades", grades);
				request.setAttribute("careers", careers);
				request.setAttribute("helpMans", helpMans);
				request.setAttribute("classes", classes);
				request.getRequestDispatcher("/teacher/admin/name_classes_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_classes").forward(
				request, response);
	}
}
