/**
 **作者：翁加林
 **时间：2012-8-13
 **文件名：QuestionBank_single_update_info.java
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

public class QuestionBank_single_update_info extends HttpServlet {
	private static final long serialVersionUID = 1926432832028581464L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String singleId = request.getParameter("singleId");
		String content = request.getParameter("content");
		String SA = request.getParameter("SA");
		String SB = request.getParameter("SB");
		String SC = request.getParameter("SC");
		String SD = request.getParameter("SD");
		String result = request.getParameter("result");
		String difficulty = request.getParameter("difficulty");
		String knowledgeId = request.getParameter("knowledgeId");
		String analyse = request.getParameter("analyse");
		// 检测格式及正确性
		Teacher teacher = null;
		QuestionDifficultyType difficultyType = null;
		Knowledge knowledge = null;
		int single_id = -1;
		try {
			teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			single_id = Integer.parseInt(singleId);
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
				Single.class,
				new String[]{"id","content","SA","SB","SC","SD","result","difficulty","knowledge","analyse"},
				new Object[]{single_id,content,SA,SB,SC,SD,result,difficultyType,knowledge,analyse});
		if (showMessage == null) {
				SingleServiceImp singleService = new SingleServiceImp();
				Single single = singleService.getSingleById(single_id,teacher);
				if (single != null) {
					single.setAnalyse(analyse);
					single.setContent(content);
					single.setCreateTime(new Date());
					single.setDifficulty(difficultyType);
					single.setKnowledge(knowledge);
					single.setResult(result);
					single.setSA(SA);
					single.setSC(SC);
					single.setSB(SB);
					single.setSD(SD);
					showMessage = singleService.updateSingleInfo(single);
				}else {
					showMessage = "错误信息：指定试题不存在！";
				}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_single_update?singleId=" + singleId).forward(request, response);
	}

}
