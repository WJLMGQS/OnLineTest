/**
 **作者：翁加林
 **时间：2012-7-25
 **文件名：Admin_account_profession_update.java
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

import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Account_profession_update extends HttpServlet {
	private static final long serialVersionUID = 387062120496965524L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(Student.class,"userId",userId);
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			ProfessionServiceImp professionService = new ProfessionServiceImp(); 
			Teacher profession = professionService.getProfessionById(userId);
			if (profession == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:教师编号:" + userId + "账户不存在！";
			} else {
				SubjectServiceImp subjectService = new SubjectServiceImp();
				List<Subject> subjects = subjectService.getAllSubjects();
				request.setAttribute("subjects", subjects);
				request.setAttribute("profession", profession);
				request.getRequestDispatcher("/teacher/admin/account_profession_update.jsp").forward(
						request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_profession")
				.forward(request, response);
	}
}
