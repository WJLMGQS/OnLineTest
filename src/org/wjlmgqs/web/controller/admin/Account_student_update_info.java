/**
 **作者：翁加林
 **时间：2012-8-6
 **文件名：Admin_account_student_update_info.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest07
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.enums.UserAccountStatus;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Account_student_update_info extends HttpServlet {
	private static final long serialVersionUID = -2793032783151147313L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("studentId");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		String classId = request.getParameter("classId");
		String status = request.getParameter("status");
		int class_id =-1;
		UserAccountStatus accountStatus = null;
		Classes classes = null;
		try {
			class_id = Integer.parseInt(classId);
		} catch (Exception e) {}	
		try {
			int status_index = Integer.parseInt(status.trim());
			accountStatus = UserAccountStatus.values()[status_index];
		} catch (Exception e) {}	
		try {
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			classes = classesServiceImp.getClassesById(class_id);
		} catch (NumberFormatException e1) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Student.class,
				new String[]{"userId","name","sex","status","telephone","class"},
				new Object[]{userId,name,sex,status,telephone,class_id});
		// 检测格式及正确性
		if (showMessage == null) {
				StudentServiceImp studentService = new StudentServiceImp();
				// 更新到数据库
				Student student = studentService.getStudentById(userId);
			if(student!=null){
				student.setName(name);
				student.setSex(sex);
				student.setStatus(accountStatus);
				student.setTelephone(telephone);
				student.setClasses(classes);
				showMessage = studentService.updateStudentInfo(student);
			} else {
				showMessage = "错误信息:指定班级号不存在！";
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_student_update?studentId=" + userId).forward(request, response);
	}
}
