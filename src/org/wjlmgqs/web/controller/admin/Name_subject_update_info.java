/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_subject_update_info.java
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

public class Name_subject_update_info extends HttpServlet {
	private static final long serialVersionUID = 5855396363143681816L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String subjectId = request.getParameter("subjectId");
		String code = request.getParameter("code");
		int subject_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		try {
			subject_id = Integer.parseInt(subjectId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Subject.class,new String[]{"id","code"},new Object[]{subject_id,code});
		// 检测格式及正确性
		if (showMessage == null) {
			try {
				SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
				Subject subject = subjectServiceImp.getSubjectById(subject_id);
				if(subject!=null){
					subject.setCode(code);
					showMessage = subjectServiceImp.updateSubjectInfo(subject);
				}else{
					showMessage = "错误信息:指定科目不存在！";
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				showMessage = "错误信息:科目代码不存在！";
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_subject_update?subjectId=" + subjectId).forward(
				request, response);
	}

}
