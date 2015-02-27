/**
 **作者：翁加林
 **时间：2012-8-20
 **文件名：QuestionBank_judge_create.java
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
import org.wjlmgqs.web.util.StaticVariable;

public class QuestionBank_judge_create extends HttpServlet {
	private static final long serialVersionUID = -2375260392230910128L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String showMessage = null;
		String content = request.getParameter("content");
		String result = request.getParameter("result");
		String difficulty = request.getParameter("difficulty");
		String knowledgeId = request.getParameter("knowledgeId");
		String analyse = request.getParameter("analyse");
		Teacher teacher = null;
		QuestionDifficultyType difficultyType = null;
		Knowledge knowledge = null;
		// 检测格式及正确性
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
		// 验证客户端资料
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Judge.class,
				new String[]{"content","result","difficulty","knowledge","analyse"},
				new Object[]{content,result,difficultyType,knowledge,analyse});
		if (showMessage == null) {
			JudgeServiceImp judgeService = new JudgeServiceImp();
			Judge judge = new Judge();
			judge.setAnalyse(analyse);
			judge.setContent(content);
			judge.setCreateTime(new Date());
			judge.setDifficulty(difficultyType);
			judge.setImage(StaticVariable.getQuestBankImage());
			judge.setKnowledge(knowledge);
			judge.setResult(result);
			showMessage = judgeService.createJudge(judge);
		}
		request.setAttribute("showMessage", showMessage);
		request.setAttribute("hiddenDivContentStatus", "show");
		request.getRequestDispatcher("/teacher/profession/questionBank_judge").forward(request, response);
	}

}
