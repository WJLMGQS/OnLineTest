/**
 **作者：翁加林
 **时间：2012-8-17
 **文件名：QuestionBank_multiple_update_info.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest13
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.service.impl.MultipleServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.WebLogger;

public class QuestionBank_multiple_update_info extends HttpServlet {
	private static final long serialVersionUID = 7011844627822382593L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String multipleId = request.getParameter("multipleId");
		String content = request.getParameter("content");
		String[] choices = request.getParameterValues("choices");
		String[] results = request.getParameterValues("results");
		String difficulty = request.getParameter("difficulty");
		String knowledgeId = request.getParameter("knowledgeId");
		String analyse = request.getParameter("analyse");
		// 检测格式及正确性
		Teacher teacher = null;
		QuestionDifficultyType difficultyType = null;
		Knowledge knowledge = null;
		int multiple_id = -1;
		try {
			teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			multiple_id = Integer.parseInt(multipleId);
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
				Multiple.class,
				new String[]{"id","content","choices","results","difficulty","knowledge","analyse"},
				new Object[]{multiple_id,content,choices,results,difficultyType,knowledge,analyse});
		if (showMessage == null) {
			String[] cs = new String[choices.length-1]; 
			String[] rs = new String[choices.length-1]; //答案和选项的个数保持一致，选中：1，没被选中：0；
			for(int i = 0;i<cs.length;i++) {
				if(choices[i+1].trim().equals("")) {
					showMessage = "错误信息:选项不能为空 ！";
					break;
				}
				cs[i] = choices[i+1];
				rs [i] = "0";//选项内容是否被选中答案的初始状态，表示没被选中
			}
			if(showMessage==null) {
				for(int i = 0;i<results.length;i++) {//System.out.println("提交到服务端的答案 ："+results[i]);
					String index = results[i].trim();
					try {
						int sIndex = Integer.parseInt(index);
						rs[sIndex-1] = "1";//内容为1表示此处的选项被选作答案,这里减1是因为传递的答案数组下标是从1开始，而接收方的数组下表从0开始
					} catch (NumberFormatException e) {
						showMessage = "错误信息：选项答案至少选中一个";
						WebLogger.showInfor(showMessage);
						break;
					}
				}
				MultipleServiceImp multipleService = new MultipleServiceImp();
				Multiple multiple = multipleService.getMultipleById(multiple_id,teacher);
				if (multiple != null) {
					multiple.setAnalyse(analyse);
					multiple.setContent(content);
					multiple.setCreateTime(new Date());
					multiple.setDifficulty(difficultyType);
					multiple.setKnowledge(knowledge);
					multiple.setResults(rs);
					multiple.setChoices(cs);
					showMessage = multipleService.updateMultiple(multiple);
				}else {
					showMessage = "错误信息:至少选中一个答案 ！";
				}
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_multiple_update?multipleId=" + multipleId).forward(request, response);
	}

}
