/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：Name_knowledge_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_knowledge_update extends HttpServlet {

	private static final long serialVersionUID = -2492200983423544689L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String knowledgeId = request.getParameter("knowledgeId");
		int knowledge_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		try {
			knowledge_id = Integer.parseInt(knowledgeId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorVoProperty(Knowledge.class,"id", knowledge_id);
		if (showMessage == null) {
			Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			// 数据库信息核对成功后删除，并返回删除结果
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
			Knowledge knowledge = knowledgeServiceImp.getknowledgeById(knowledge_id,teacher);
			if (knowledge == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:指定知识点不存在！";
			} else {
				request.setAttribute("knowledge", knowledge);
				request.getRequestDispatcher("/teacher/profession/name_knowledge_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/name_knowledge").forward(
				request, response);
	}

}
