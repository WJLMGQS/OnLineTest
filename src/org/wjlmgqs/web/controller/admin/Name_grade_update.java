/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_grade_update.java
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

import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_grade_update extends HttpServlet {

	private static final long serialVersionUID = -1020188095369583396L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String gradeId = request.getParameter("gradeId");
		int grade_id=-1;
		try {
			grade_id = Integer.parseInt(gradeId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(Grade.class,"id", grade_id);
		if (showMessage == null){
			// 数据库信息核对成功后删除，并返回删除结果
			GradeServiceImp gradeServiceImp = new GradeServiceImp();
			Grade grade = gradeServiceImp.getGradeById(grade_id);
			if (grade == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:指定年级不存在！";
			} else {
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				List<Department> departments = departmentServiceImp.getAllDepartments();
				request.setAttribute("departments", departments);
				request.setAttribute("grade", grade);
				request.getRequestDispatcher("/teacher/admin/name_grade_update.jsp").forward(
						request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_grade").forward(
				request, response);
	}

}
