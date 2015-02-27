/**
 **作者：翁加林
 **时间：2012-7-25
 **文件名：Admin_account_profession_create.java
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
import org.wjlmgqs.web.util.MD5Code;

public class Account_profession_create extends HttpServlet {
	private static final long serialVersionUID = 7246069740185294521L;

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
		String password = request.getParameter("freshF");
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
			int status_index = Integer.parseInt(status.trim());
			accountStatus = UserAccountStatus.values()[status_index];
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Teacher.class, 
				new String[]{"userId","name","sex","telephone","subject","status","password"}, 
				new Object[]{userId,name,sex,telephone,subject,accountStatus,password});
		if (showMessage==null) {
			//生成密码对应的密文
			MD5Code md5 = new MD5Code();
			password = md5.getMD5ofStr(password);
			//格式验证合格后生成监考员对象
			Teacher teacher = new Teacher();
			teacher.setName(name.trim());
			teacher.setPassword(password);
			teacher.setSex(sex);
			teacher.setStatus(accountStatus);
			teacher.setTelephone(telephone);
			teacher.setUserId(userId);
			teacher.setPower("1");
			teacher.setSubject(subject);
			//数据库信息核对成功后插入，并返回插入结果
			ProfessionServiceImp professionService = new ProfessionServiceImp(); 
			showMessage = professionService.createProfession(teacher);
		}
		System.out.println("showMessage:"+showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_profession")
				.forward(request, response);
	}
}
