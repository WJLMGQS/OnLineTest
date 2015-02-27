/**
 **作者：翁加林
 **时间：2012-8-17
 **文件名：QuestionBank_multiple_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest13
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.MultipleServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class QuestionBank_multiple_update extends HttpServlet {
	private static final long serialVersionUID = 2129751554301763806L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = "错误信息:试题编号有误！";
		String multipleId = request.getParameter("multipleId");
		Teacher teacher;
		// 数据库信息核对成功后删除，并返回删除结果
		int multiple_id = -1;
		try {
			multiple_id = Integer.parseInt(multipleId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(Multiple.class,"id",multiple_id);
		if(showMessage == null){
			MultipleServiceImp multipleService = new MultipleServiceImp();
			teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			Multiple multiple = multipleService.getMultipleById(multiple_id, teacher);
			if (multiple == null) {// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:学生编号--" + multipleId + "--不存在！";
			} else {
				KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp();
				List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
				request.setAttribute("multiple", multiple);
				request.setAttribute("knowledges", knowledges);
				request	.getRequestDispatcher("/teacher/profession/questionBank_multiple_update.jsp")
						.forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_multiple")
				.forward(request, response);
	}
}
