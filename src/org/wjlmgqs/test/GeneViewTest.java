package org.wjlmgqs.test;

import org.junit.Test;
import org.wjlmgqs.daomain.ChromosomeView;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;
import org.wjlmgqs.service.impl.ChromosomeViewServiceImp;

public class GeneViewTest {
	@Test
	public void testGetGeneView(){
		TestPaper testPaper = new TestPaper(null,TestPaperBulidType.AUTO,null);
		ChromosomeView chromosomeView = initChormosomeView();
		ChromosomeViewServiceImp geneViewServiceImp = new ChromosomeViewServiceImp();
		String message = geneViewServiceImp.bulidRandomTestPaper(chromosomeView,testPaper);
	}

	private ChromosomeView initChormosomeView() {
		ChromosomeView chromosomeView = new ChromosomeView();
		chromosomeView.getFillBlankChromosomeView().setGeneViewDifficulty(QuestionDifficultyType.GENERAL_EASY);
		chromosomeView.getJudgeChromosomeView().setGeneViewDifficulty(QuestionDifficultyType.GENERAL_HARD);
		chromosomeView.getMultipleChromosomeView().setGeneViewDifficulty(QuestionDifficultyType.HARD);
		chromosomeView.getSingleChromosomeView().setGeneViewDifficulty(QuestionDifficultyType.EASY);
		chromosomeView.getFillBlankChromosomeView().setGeneViewNumber(1);
		chromosomeView.getMultipleChromosomeView().setGeneViewNumber(2);
		chromosomeView.getJudgeChromosomeView().setGeneViewNumber(3);
		chromosomeView.getSingleChromosomeView().setGeneViewNumber(4);
		chromosomeView.setKnowledgeIds(new String[]{"1","2","3","4","5","6","7","8","9","10"});
		Teacher t = new Teacher();
		t.setUserId("20090531164");
		chromosomeView.setTeacher(t);
		return chromosomeView;
	}
	

}
