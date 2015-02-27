package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperUseType;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Paper_update_info extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		//接受更新的数据
		String name = request.getParameter("name").trim();
		String _finishTime = request.getParameter("finishTime");
		String _singleMark = request.getParameter("singleMark");
		String _multipleMark = request.getParameter("multipleMark");
		String _judgeMark = request.getParameter("judgeMark");
		String _fillBlankMark = request.getParameter("fillBlankMark");
		String _useType = request.getParameter("useType");
		String _difficulty = request.getParameter("difficulty");
		
		int finishTime = -1;
		BigDecimal singleMark = null;
		BigDecimal multipleMark = null;
		BigDecimal judgeMark = null;
		BigDecimal fillBlankMark = null;
		TestPaperUseType useType = null;
		QuestionDifficultyType difficulty = null;
		try {
			int index_useType = Integer.parseInt(_useType);
			useType = TestPaperUseType.values()[index_useType];
		} catch (Exception e) {}	
		try {	
			int index_difficulty = Integer.parseInt(_difficulty);
			difficulty = QuestionDifficultyType.values()[index_difficulty];
		} catch (Exception e) {}	
		try {
			finishTime = Integer.parseInt(_finishTime);
		} catch (Exception e) {}	
		try {
			singleMark = new BigDecimal(_singleMark);
		} catch (Exception e) {}	
		try {	
			multipleMark = new BigDecimal(_multipleMark);
		} catch (Exception e) {}	
		try {
			judgeMark = new BigDecimal(_judgeMark);
		} catch (Exception e) {}	
		try {
			fillBlankMark = new BigDecimal(_fillBlankMark);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				TestPaper.class,
				new String[]{"name","useType","difficulty","singleMark","multipleMark","judgeMark","fillBlankMark"},
				new Object[]{name,useType,difficulty,singleMark,multipleMark,judgeMark,fillBlankMark});
		//检测接受到得数据是否为空，如果为空就直接返回，并提示消息：数据更新失败
		if(showMessage==null){//数据检测通过后更新试卷中的数据信息
			TestPaper testPaper = (TestPaper)request.getSession().getAttribute("testPaper");
			testPaper.setName(name);
			testPaper.setFinishTime(finishTime);
			testPaper.setSingleMark(singleMark);
			testPaper.setMultipleMark(multipleMark);
			testPaper.setJudgeMark(judgeMark);
			testPaper.setFillBlankMark(fillBlankMark);
			testPaper.setUseType(useType);
			testPaper.setDifficulty(difficulty);
			testPaper.setUpdateTime(new Date());
			testPaper.setSave(true);
			TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
			showMessage = testPaperServiceImp.createOrUpdateTestPaper(testPaper);
			request.getSession().setAttribute("testPaper", testPaper);
		}
		request.setAttribute("showMessage",showMessage);
		//返回试卷界面，并提示更新结果
		request.getRequestDispatcher("/teacher/profession/paper_update").forward(request, response);
	}
}
