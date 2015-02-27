/**
 **@author wjlmgqs
 **下午12:25:46
 **Paper_autoBulid_create.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest
 */
package org.wjlmgqs.web.controller.profession;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ChromosomeView;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;
import org.wjlmgqs.enums.TestPaperUseType;
import org.wjlmgqs.service.impl.ChromosomeViewServiceImp;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.WebLogger;

/**
 * 接受页面试卷参数并遗传组卷
 */
public class Paper_autoBulid_create extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		//接受页面提交的请求参数
		String name = request.getParameter("name");
		String _finishTime = request.getParameter("finishTime");
		String _useType = request.getParameter("useType");
		String _singleMark = request.getParameter("singleMark");
		String _multipleMark = request.getParameter("multipleMark");
		String _judgeMark = request.getParameter("judgeMark");
		String _fillBlankMark = request.getParameter("fillBlankMark");
		String _singleNumber = request.getParameter("singleNumber");
		String _judgeNumber = request.getParameter("judgeNumber");
		String _multipleNumber = request.getParameter("multipleNumber");
		String _fillBlankNumber = request.getParameter("fillBlankNumber");
		String _singleDifficulty = request.getParameter("singleDifficulty");
		String _judgeDifficulty = request.getParameter("judgeDifficulty");
		String _multipleDifficulty = request.getParameter("multipleDifficulty");
		String _fillBlankDifficulty = request.getParameter("fillBlankDifficulty");
		String _difficulty = request.getParameter("difficulty");
		String[] _knowledgeIds = request.getParameterValues("knowledgeIds");
		//参数转型
		int finishTime = -1;
		TestPaperUseType useType = null;
		BigDecimal singleMark = null;
		BigDecimal multipleMark = null;
		BigDecimal judgeMark = null;
		BigDecimal fillBlankMark = null;
		int singleNumber = -1;
		int judgeNumber = -1;
		int multipleNumber = -1;
		int fillBlankNumber = -1;
		QuestionDifficultyType difficulty = null;
		QuestionDifficultyType singleDifficulty = null;
		QuestionDifficultyType judgeDifficulty = null;
		QuestionDifficultyType multipleDifficulty = null;
		QuestionDifficultyType fillBlankDifficulty = null;
		try {
			finishTime = Integer.parseInt(_finishTime);
		} catch (Exception e) {}	
		try {	
			int index_useType = Integer.parseInt(_useType);
			useType = TestPaperUseType.values()[index_useType];
		} catch (Exception e) {}	
		try {	
			int index_difficulty = Integer.parseInt(_difficulty);
			difficulty = QuestionDifficultyType.values()[index_difficulty];
		} catch (Exception e) {}	
		try {		
			int single_difficulty = Integer.parseInt(_singleDifficulty);
			singleDifficulty = QuestionDifficultyType.values()[single_difficulty];
		} catch (Exception e) {}	
		try {	
			int judge_difficulty = Integer.parseInt(_judgeDifficulty);
			judgeDifficulty = QuestionDifficultyType.values()[judge_difficulty];
		} catch (Exception e) {}	
		try {	
			int multiple_difficulty = Integer.parseInt(_multipleDifficulty);
			multipleDifficulty = QuestionDifficultyType.values()[multiple_difficulty];
		} catch (Exception e) {}	
		try {	
			int fillBlank_difficulty = Integer.parseInt(_fillBlankDifficulty);
			fillBlankDifficulty = QuestionDifficultyType.values()[fillBlank_difficulty];
		} catch (Exception e) {}	
		try {	
			singleNumber = Integer.parseInt(_singleNumber);
		} catch (Exception e) {}	
		try {	
			judgeNumber = Integer.parseInt(_judgeNumber);
		} catch (Exception e) {}	
		try {	
			multipleNumber = Integer.parseInt(_multipleNumber);
		} catch (Exception e) {}	
		try {	
			fillBlankNumber = Integer.parseInt(_fillBlankNumber);
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
		} catch (Exception e1) {}
		//封装请求参数到对象
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		TestPaper testPaper = new TestPaper(teacher,TestPaperBulidType.AUTO,difficulty);
		testPaper.setName(name);
		testPaper.setFinishTime(finishTime);
		testPaper.setUseType(useType);
		testPaper.setFillBlankMark(fillBlankMark);
		testPaper.setJudgeMark(judgeMark);
		testPaper.setSingleMark(singleMark);
		testPaper.setMultipleMark(multipleMark);
		testPaper.setDifficulty(difficulty);
		testPaper.setTeacher(teacher);
		testPaper.setUpdateTime(testPaper.getCreateTime());

		ChromosomeView chromosomeView = new ChromosomeView();
		chromosomeView.setKnowledgeIds(_knowledgeIds);
		chromosomeView.getFillBlankChromosomeView().setGeneViewDifficulty(fillBlankDifficulty);
		chromosomeView.getFillBlankChromosomeView().setGeneViewNumber(fillBlankNumber);
		chromosomeView.getJudgeChromosomeView().setGeneViewDifficulty(judgeDifficulty);
		chromosomeView.getJudgeChromosomeView().setGeneViewNumber(judgeNumber);
		chromosomeView.getMultipleChromosomeView().setGeneViewDifficulty(multipleDifficulty);
		chromosomeView.getMultipleChromosomeView().setGeneViewNumber(multipleNumber);
		chromosomeView.getSingleChromosomeView().setGeneViewDifficulty(singleDifficulty);
		chromosomeView.getSingleChromosomeView().setGeneViewNumber(singleNumber);
		chromosomeView.setTeacher(teacher);
		
		//校验构建对象
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(TestPaper.class,
				new String[]{"name","useType","finishTime","difficulty","singleMark","multipleMark","judgeMark","fillBlankMark"},
				new Object[]{name,useType,finishTime,difficulty,singleMark,multipleMark,judgeMark,fillBlankMark});
		if(showMessage==null){
			showMessage = BeanValidateUtil.validatorVo(chromosomeView);
		}
		WebLogger.showInfor("完成构建试卷参数信息的校验。。。");
		//校验成功,创建试卷
		if(showMessage==null){
			/**
			 * 开始组卷过程，返回试卷中的各种试题类型集合
			 */
			ChromosomeViewServiceImp chromosomeViewServiceImp = new ChromosomeViewServiceImp();
			showMessage = chromosomeViewServiceImp.bulidRandomTestPaper(chromosomeView,testPaper);//如果试卷创建失败就将testPaper对象置为null
			if(showMessage!=null){
				request.setAttribute("showMessage",showMessage);
				WebLogger.showInfor("完成构建试卷的过程。。。失败");
			}else{
				if(testPaper != null){//创建成功
					//保存试卷
					TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
					showMessage = testPaperServiceImp.createOrUpdateTestPaper(testPaper);
					//为后面直接编辑当前试卷
					@SuppressWarnings("unchecked")
					List<TestPaper> testPapers = (ArrayList<TestPaper>)request.getSession().getAttribute("testPapers");
					if(testPapers==null){
						testPapers = new ArrayList<TestPaper>();
					}
					testPaper.setSave(true);
					testPapers.add(testPaper);
					request.getSession().setAttribute("testPapers",testPapers);
					request.setAttribute("showMessage",showMessage);
					WebLogger.showInfor("完成构建试卷的过程。。。成功");
				}
			}
		}
		//创建失败/校验失败，返回到构建页面错误消息
		WebLogger.showInfor("完成构建试卷的过程。。。");
		//返回试卷界面，并提示更新结果		
		request.getRequestDispatcher("/teacher/profession/paper_create_auto").forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
