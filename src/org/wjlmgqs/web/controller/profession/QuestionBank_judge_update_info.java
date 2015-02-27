/**
**作者：翁加林
**时间：2012-8-20
**文件名：QuestionBank_judge_update_info.java
**包名：org.wjlmgqs.web.controller.profession
**工程名：OnLineTest14
*/


package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.service.impl.JudgeServiceImp;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class QuestionBank_judge_update_info extends HttpServlet {

	private static final long serialVersionUID = 686343814007255695L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String judgeId = request.getParameter("judgeId");
		String content = request.getParameter("content");
		String result = request.getParameter("result");
		String difficulty = request.getParameter("difficulty");
		String knowledgeId = request.getParameter("knowledgeId");
		String analyse = request.getParameter("analyse");
		Teacher teacher = null;
		QuestionDifficultyType difficultyType = null;
		Knowledge knowledge = null;
		// 检测格式及正确性
		int judge_id = -1;
		try {
			teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			judge_id = Integer.parseInt(judgeId);
		} catch (Exception e) {}	
		try {
			int index_difficulty = Integer.parseInt(difficulty.trim());
			difficultyType = QuestionDifficultyType.values()[index_difficulty];
		} catch (Exception e) {}	
		try {
			int knowledge_id = Integer.parseInt(knowledgeId);
			KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp(); 
			knowledge = knowledgeServiceImp.getknowledgeById(knowledge_id,teacher);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Judge.class,
				new String[]{"id","content","result","difficulty","knowledge","analyse"},
				new Object[]{judge_id,content,result,difficultyType,knowledge,analyse});
		if (showMessage == null) {
			JudgeServiceImp judgeService = new JudgeServiceImp();
			Judge judge = judgeService.getJudgeById(judge_id,teacher);
			if (judge != null) {
				judge.setAnalyse(analyse);
				judge.setContent(content);
				judge.setCreateTime(new Date());
				judge.setDifficulty(difficultyType);
				judge.setKnowledge(knowledge);
				judge.setResult(result);
				showMessage = judgeService.updateJudgeInfo(judge);
			} else {
				showMessage = "错误信息：指定试题不存在！";
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_judge_update?judgeId=" + judgeId)
				.forward(request, response);
	}

}
