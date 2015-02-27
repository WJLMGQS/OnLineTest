/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：Name_knowledge_update_info.java
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

public class Name_knowledge_update_info extends HttpServlet {
	private static final long serialVersionUID = 3143768953606352835L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String knowledgeId = request.getParameter("knowledgeId");
		String code = request.getParameter("code");
		int knowledge_id = -1;//默认超出正常范围触发校验失败信息，有parseInt成功后获得新值
		try {
			knowledge_id = Integer.parseInt(knowledgeId);
		} catch (Exception e) {}
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Knowledge.class,new String[]{"id","code"},new Object[]{knowledge_id,code});
		if (showMessage == null) {
			Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			Knowledge knowledge = new Knowledge();
			knowledge.setCode(code.trim());
			knowledge.setId(knowledge_id);
			knowledge.setTeacher(teacher);
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
			showMessage = knowledgeServiceImp.updateKnowledgeInfo(knowledge);
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/name_knowledge_update?knowledgeId=" + knowledgeId)
				.forward(request, response);
	}

}
