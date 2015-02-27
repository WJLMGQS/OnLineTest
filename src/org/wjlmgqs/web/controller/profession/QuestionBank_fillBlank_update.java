/**
 **作者：翁加林
 **时间：2012-8-21
 **文件名：QuestionBank_fillBlank_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest15
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.FillBlankServiceImp;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class QuestionBank_fillBlank_update extends HttpServlet {

	private static final long serialVersionUID = 475293676590133935L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = "错误信息:试题编号有误！";
		String fillBlankId = request.getParameter("fillBlankId");
		// 验证客户端资料
		int fillBlank_id = -1;
		try {
			fillBlank_id = Integer.parseInt(fillBlankId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(FillBlank.class,"id",fillBlank_id);
		FillBlankServiceImp fillBlankService = new FillBlankServiceImp();
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		// 数据库信息核对成功后删除，并返回删除结果
		FillBlank fillBlank = fillBlankService.getFillBlankById(fillBlank_id, teacher);
		if (fillBlank == null) {
			// 出现错误，返回查看所有监考员账号界面
			showMessage = "错误信息:学生编号--" + fillBlankId + "--不存在！";
		} else {
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp();
			List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
			request.setAttribute("fillBlank", fillBlank);
			request.setAttribute("knowledges", knowledges);
			request.getRequestDispatcher("/teacher/profession/questionBank_fillBlank_update.jsp")
					.forward(request, response);
			return;
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_fillBlank").forward(request,
				response);
	}
}
