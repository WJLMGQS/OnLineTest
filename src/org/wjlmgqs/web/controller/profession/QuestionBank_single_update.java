/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：QuestionBank_single_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.SingleServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class QuestionBank_single_update extends HttpServlet {
	private static final long serialVersionUID = 7002186081655256282L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = "错误信息:试题编号有误！";
		String singleId = request.getParameter("singleId");
		// 验证客户端资料
		int single_id = -1;
		try {
			single_id = Integer.parseInt(singleId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(Single.class,"id",single_id);
		if (showMessage == null) {
			SingleServiceImp singleService = new SingleServiceImp();
			Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			// 数据库信息核对成功后删除，并返回删除结果
			Single single = singleService.getSingleById(single_id,teacher);
			if (single == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:学生编号--" + singleId + "--不存在！";
			} else {
				KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
				List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
				request.setAttribute("single", single);
				request.setAttribute("knowledges", knowledges);
				request.getRequestDispatcher("/teacher/profession/questionBank_single_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_single").forward(request, response);
	}
}
