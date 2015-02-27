/**
 **作者：翁加林
 **时间：2012-7-25
 **文件名：Admin_account_profession_update_info.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.UserAccountStatus;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Account_profession_update_info extends HttpServlet {
	private static final long serialVersionUID = 362479459848186591L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		String subjectId = request.getParameter("subjectId");
		String status = request.getParameter("status");
		// 检测格式及正确性
		int subject_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		UserAccountStatus accountStatus = null;
		Subject subject = null;
		try {
			subject_id = Integer.parseInt(subjectId);
			SubjectServiceImp subjectService = new SubjectServiceImp();
			subject = subjectService.getSubjectById(subject_id);
		} catch (Exception e) {}	
		try {
			int status_index = Integer.parseInt(status);
			accountStatus = UserAccountStatus.values()[status_index];
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Teacher.class, 
				new String[]{"userId","name","sex","telephone","subject","status"}, 
				new Object[]{userId,name,sex,telephone,subject,accountStatus});
		if(showMessage == null) {
			ProfessionServiceImp professionService = new ProfessionServiceImp(); 
			Teacher teacher = professionService.getProfessionById(userId);
			if(teacher==null) {
				showMessage = "错误信息：任课教师账号不存在！";
			}else {
				teacher.setName(name.trim());
				teacher.setSex(sex);
				teacher.setStatus(accountStatus);
				teacher.setTelephone(telephone);
				teacher.setSubject(subject);
				showMessage = professionService.updateProfessionInfoByAdmin(teacher);
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_profession_update?userId=" + userId).forward(request, response);
	}

}
