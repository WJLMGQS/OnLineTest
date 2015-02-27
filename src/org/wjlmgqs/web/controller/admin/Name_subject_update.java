/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_subject_update.java
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
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_subject_update extends HttpServlet {

	private static final long serialVersionUID = -2110514858135320898L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String subjectId = request.getParameter("subjectId");
		int subject_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		try {
			subject_id = Integer.parseInt(subjectId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(Subject.class,"id", subject_id);
		if (showMessage == null) {
			// 数据库信息核对成功后删除，并返回删除结果
			SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
			Subject subject = subjectServiceImp.getSubjectById(subject_id);
			if (subject == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:指定科目不存在！";
			} else {
				request.setAttribute("subject", subject);
				request.getRequestDispatcher("/teacher/admin/name_subject_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_subject").forward(request, response);
	}

}
