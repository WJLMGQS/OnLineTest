/**
 **作者：翁加林
 **时间：2012-8-11
 **文件名：QuestionBank_single_create.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest11
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.SingleServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.StaticVariable;

public class QuestionBank_single_create extends HttpServlet {
	private static final long serialVersionUID = 1975054549614625482L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String content = request.getParameter("content");
		String SA = request.getParameter("SA");
		String SB = request.getParameter("SB");
		String SC = request.getParameter("SC");
		String SD = request.getParameter("SD");
		String result = request.getParameter("result");
		String difficulty = request.getParameter("difficulty");
		String knowledgeId = request.getParameter("knowledgeId");
		String analyse = request.getParameter("analyse");
		Teacher teacher = null;
		QuestionDifficultyType difficultyType = null;
		Knowledge knowledge = null;
		try {
			teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			int index_difficulty = Integer.parseInt(difficulty.trim());
			difficultyType = QuestionDifficultyType.values()[index_difficulty];
		} catch (Exception e) {}	
		try {
			int knowledge_id = Integer.parseInt(knowledgeId);
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
			knowledge = knowledgeServiceImp.getknowledgeById(knowledge_id,teacher);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Single.class,
				new String[]{"content","SA","SB","SC","SD","result","difficulty","knowledge","analyse"},
				new Object[]{content,SA,SB,SC,SD,result,difficultyType,knowledge,analyse});
		
		// 验证客户端资料
		if (showMessage == null) {
			SingleServiceImp singleService = new SingleServiceImp();
			Single single = new Single();
			single.setAnalyse(analyse);
			single.setContent(content);
			single.setCreateTime(new Date());
			single.setDifficulty(difficultyType);
			single.setImage(StaticVariable.getQuestBankImage());
			single.setKnowledge(knowledge);
			single.setResult(result);
			single.setSA(SA);
			single.setSC(SC);
			single.setSB(SB);
			single.setSD(SD);
			showMessage = singleService.createSingle(single);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus","show");
		request.getRequestDispatcher("/teacher/profession/questionBank_single").forward(request, response);
	}
}
