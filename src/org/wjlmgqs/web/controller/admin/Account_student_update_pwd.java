/**
 **作者：翁加林
 **时间：2012-8-6
 **文件名：Admin_account_student_update_pwd.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest07
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;

public class Account_student_update_pwd extends HttpServlet {
	private static final long serialVersionUID = -6710624486726950362L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("studentId");
		String freshF = request.getParameter("freshF");
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Student.class,new String[]{"userId","password"},new Object[]{userId,freshF});
		if (showMessage == null) {
			MD5Code code = new MD5Code();
			String fresh = code.getMD5ofStr(freshF);// 产生新的存储到数据库的密文
			// 存入数据库
			StudentServiceImp studentService = new StudentServiceImp();
			showMessage = studentService.updateStduentPwd(fresh, userId);
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_student_update?studentId=" + userId)
				.forward(request, response);
	}
}
