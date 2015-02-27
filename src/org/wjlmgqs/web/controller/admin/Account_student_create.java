/**
 **作者：翁加林
 **时间：2012-8-4
 **文件名：Admin_account_student_create.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest05
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.enums.UserAccountStatus;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;
import org.wjlmgqs.web.util.StaticVariable;

public class Account_student_create extends HttpServlet {
	private static final long serialVersionUID = -4181606147233122667L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		String classId = request.getParameter("classId");
		String status = request.getParameter("status");
		int class_id =-1;
		UserAccountStatus accountStatus = null;
		Classes classes = null;
		try {
			class_id = Integer.parseInt(classId);
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			classes = classesServiceImp.getClassesById(class_id);
		} catch (Exception e) {}	
		try {
			int status_index = Integer.parseInt(status);
			accountStatus = UserAccountStatus.values()[status_index];
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Student.class,
				new String[]{"userId","class","status"},
				new Object[]{userId,classes,accountStatus});
		if (showMessage == null) {
			MD5Code md5 = new MD5Code();
			//格式验证合格后生成监考员对象
			Student student = new Student();
			student.setStatus(accountStatus);
			student.setUserId(userId);
			student.setClasses(classes);
			//下面的属性采用默认形式
			student.setName(userId.trim());//姓名默认为学号
			student.setPassword(md5.getMD5ofStr(userId));//密码默认为学号
			student.setPhoto(StaticVariable.getStudentPhoto());//照片默认为系统照片"/images/defaultStudentPhoto.jpg";
			student.setSex(StaticVariable.getStudentSex());//性别默认为男性
			student.setTelephone(StaticVariable.getStudentTelephote());//电话默认为系统电话000000000000
			//数据库信息核对成功后插入，并返回插入结果
			StudentServiceImp studentService = new StudentServiceImp();
			showMessage = studentService.createStudent(student);
		}
		System.out.println("showMessage:"+showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_student").forward(
				request, response);
	}
}
