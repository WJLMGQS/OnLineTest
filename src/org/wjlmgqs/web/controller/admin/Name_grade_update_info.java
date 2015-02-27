/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_grade_update_info.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_grade_update_info extends HttpServlet {
	private static final long serialVersionUID = 5036692589154738914L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String gradeId = request.getParameter("gradeId");
		String code = request.getParameter("code");
		int grade_id=-1;
		try {
			grade_id = Integer.parseInt(gradeId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Grade.class,new String[]{"id","code"},new Object[]{grade_id,code});
		// 检测格式及正确性
		if (showMessage == null) {
			GradeServiceImp gradeServiceImp = new GradeServiceImp();
			Grade grade = gradeServiceImp.getGradeById(grade_id);
			if(grade!=null){
				// 更新到数据库
				grade.setCode(code.trim());
				showMessage = gradeServiceImp.updateGradeInfo(grade);
			}else{
				showMessage = "错误信息：指定年级不存在！";
			}
			
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_grade_update?gradeId=" + gradeId).forward(
				request, response);
	}
}
