package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.daomain.ChromosomeView;
import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.service.util.BulidBestGeneViewUtil;
import org.wjlmgqs.service.util.ServieUtil;
import org.wjlmgqs.web.util.WebLogger;
/**
 * 一个试卷染色体组视图对应一份试卷
 * 一个基因视图对应一道试题
 * 基因：
 * 		* 试题编号
 * 		* 试题难度
 * 		* 试题长度：单位分值数量，也称基因长度。
 * 
 */
public class ChromosomeViewServiceImp {

	public String bulidRandomTestPaper(ChromosomeView chromosomeView,TestPaper testPaper){
		String showMessage = null;
		//获得构建试卷需要的试题基因
		showMessage = getChromosomeViewByIds(chromosomeView);
		if(showMessage==null){//正确获得试卷染色体视图对象
			/**
			 * 开始调用工具类创建试卷个各种试题类型的试题集合
			 */
			showMessage = fillTestPaperByView(chromosomeView, testPaper);
		}else{
			testPaper = null;//创建失败后试卷置为null
		}
		return showMessage;
	}

	private String fillTestPaperByView(ChromosomeView chromosomeView,TestPaper testPaper) {
		String showMessage = null;
		Teacher teacher = testPaper.getTeacher();
		BulidBestGeneViewUtil jgapRamdmoBulidTestPaperUtil = new BulidBestGeneViewUtil();
		List<Single> singles;
		List<Multiple> multiples;
		List<Judge> judges;
		List<FillBlank> fillBlanks;
		try {
			//产生单选试题集
			List<GeneView> singleView = jgapRamdmoBulidTestPaperUtil.startBulid(chromosomeView.getSingleChromosomeView());
			SingleServiceImp singleServiceImp = new SingleServiceImp();
			singles = singleServiceImp.getSinglesByIds(ServieUtil.ID2ArrayStr(singleView),teacher);
			//产生多选试题集
			List<GeneView> multipleView = jgapRamdmoBulidTestPaperUtil.startBulid(chromosomeView.getMultipleChromosomeView());
			MultipleServiceImp multipleServiceImp = new MultipleServiceImp();
			multiples = multipleServiceImp.getMultiplesByIds(ServieUtil.ID2ArrayStr(multipleView), teacher);
			//产生判断试题集
			List<GeneView> judgeView = jgapRamdmoBulidTestPaperUtil.startBulid(chromosomeView.getJudgeChromosomeView());
			JudgeServiceImp judgeServiceImp = new JudgeServiceImp();
			judges = judgeServiceImp.getJudgesByIds(ServieUtil.ID2ArrayStr(judgeView), teacher);
			//产生填空试题集
			List<GeneView> fillBlankView = jgapRamdmoBulidTestPaperUtil.startBulid(chromosomeView.getFillBlankChromosomeView());
			FillBlankServiceImp fillBlankServiceImp = new FillBlankServiceImp();
			fillBlanks = fillBlankServiceImp.getFillBlanksByIds(ServieUtil.ID2ArrayStr(fillBlankView), teacher);
			
			testPaper.setSingles(singles);
			testPaper.setMultiples(multiples);
			testPaper.setJudges(judges);
			testPaper.setFillBlanks(fillBlanks);
		} catch (Exception e) {
			showMessage = e.getMessage();
		}
		return showMessage;
	}

	private String getChromosomeViewByIds(ChromosomeView chromosomeView) {
		String showMessage = null;
		//添加满足知识点范围的单选、多选、填空、判断基因
		SingleServiceImp singleServiceImp = new SingleServiceImp();
		MultipleServiceImp multipleServiceImp = new MultipleServiceImp();
		JudgeServiceImp judgeServiceImp = new JudgeServiceImp();
		FillBlankServiceImp fillBlankServiceImp = new FillBlankServiceImp();
		List<GeneView> singles = singleServiceImp.getGeneViewsByKids(chromosomeView.getKnowledgeIds(), chromosomeView.getTeacher());
		List<GeneView> multiples = multipleServiceImp.getGeneViewsByKids(chromosomeView.getKnowledgeIds(), chromosomeView.getTeacher());
		List<GeneView> judges = judgeServiceImp.getGeneViewsByKids(chromosomeView.getKnowledgeIds(), chromosomeView.getTeacher());
		List<GeneView> fillBlanks = fillBlankServiceImp.getGeneViewsByKids(chromosomeView.getKnowledgeIds(), chromosomeView.getTeacher());
		/**
		 * 判断基因的数量是否达到创建试卷的指定标准
		 */
		if(singles==null || fillBlanks==null || judges==null || multiples==null){//如果为null说明出现了异常，因为默认是size=0
			showMessage = "错误信息:服务端异常，请联系管理员！";
		}else if(singles.size()<chromosomeView.getSingleChromosomeView().getGeneViewNumber()){
			showMessage = "错误信息:当前知识点范围内,单选试题数量不能满足指定参数！";
		}else if(fillBlanks.size()<chromosomeView.getFillBlankChromosomeView().getGeneViewNumber()){
			showMessage = "错误信息:当前知识点范围内,填空试题数量不能满足指定参数！";
		}else if(judges.size()<chromosomeView.getJudgeChromosomeView().getGeneViewNumber()){
			showMessage = "错误信息:当前知识点范围内,判断试题数量不能满足指定参数！";
		}else if(multiples.size()<chromosomeView.getMultipleChromosomeView().getGeneViewNumber()){
			showMessage = "错误信息:当前知识点范围内,多选试题数量不能满足指定参数！";
		}else{
			//达到了标准
			chromosomeView.getSingleChromosomeView().setGeneViews(singles);
			chromosomeView.getSingleChromosomeView().setType("单选");
			chromosomeView.getMultipleChromosomeView().setGeneViews(multiples);
			chromosomeView.getMultipleChromosomeView().setType("多选");
			chromosomeView.getJudgeChromosomeView().setGeneViews(judges);
			chromosomeView.getJudgeChromosomeView().setType("判断");
			chromosomeView.getFillBlankChromosomeView().setGeneViews(fillBlanks);
			chromosomeView.getFillBlankChromosomeView().setType("填空");
			WebLogger.showInfor("信息提示：成功获取知识点并生成试卷染色体组对象！");
		}
		return showMessage;
	}
}
