/**
 **@author wjlmgqs
 **上午1:03:30
 **Account_regist.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


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
import org.wjlmgqs.web.util.WebLogger;

public class Account_regist extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String classId = request.getParameter("classId");
		String password = request.getParameter("freshF");
		int class_id =-1;
		Classes classes = null;
		try {
			class_id = Integer.parseInt(classId);
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			classes = classesServiceImp.getClassesById(class_id);
		} catch (Exception e) {}	
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Student.class,
				new String[]{"userId","name","telephone","sex","classes","password"},
				new Object[]{userId,name,telephone,sex,classes,password});
		if (showMessage == null) {
			MD5Code md5 = new MD5Code();
			//格式验证合格后生成监考员对象
			Student student = new Student();
			student.setStatus(UserAccountStatus.ACTIVE);
			student.setUserId(userId);
			student.setClasses(classes);
			//下面的属性采用默认形式
			student.setName(name);
			student.setPassword(md5.getMD5ofStr(password));
			student.setPhoto(StaticVariable.getStudentPhoto());//照片默认为系统照片"/images/defaultStudentPhoto.jpg";
			student.setSex(sex);
			student.setClasses(classes);
			student.setTelephone(telephone);
			//数据库信息核对成功后插入，并返回插入结果
			StudentServiceImp studentService = new StudentServiceImp();
			showMessage = studentService.createStudent(student);
			if(student.getStatus()==UserAccountStatus.ACTIVE){
				request.getSession().setAttribute("studentRole", student);
				response.sendRedirect(request.getContextPath()+"/student/student_main");
				return;
			}
		}
		WebLogger.showInfor(showMessage);
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/studentRegistUI").forward(request, response);
	}
}
