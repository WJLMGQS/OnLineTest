/**
 **@author wjlmgqs
 **下午4:10:56
 **BulidView.java
 **org.wjlmgqs.daomain
 **OnLineTest
 */
package org.wjlmgqs.daomain;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.wjlmgqs.enums.QuestionDifficultyType;


/**
 * 遗传组卷需要的参数对象：一份试卷=四份染色体
 */
public class FillBlankChromosomeView extends QuestionGeneViewLexicon{
	public List<GeneView> getGeneViews() {
		return geneViews;
	}
	public void setGeneViews(List<GeneView> geneViews) {
		this.geneViews = geneViews;
	}
	public int getGeneViewNumber() {
		return geneViewNumber;
	}
	public void setGeneViewNumber(int geneViewNumber) {
		this.geneViewNumber = geneViewNumber;
	}
	public QuestionDifficultyType getGeneViewDifficulty() {
		return geneViewDifficulty;
	}
	public void setGeneViewDifficulty(QuestionDifficultyType geneViewDifficulty) {
		this.geneViewDifficulty = geneViewDifficulty;
	}
	protected List<GeneView> geneViews = null;
	@Min(message="错误信息：填空题数量输入有误！",value=0) 
	protected int geneViewNumber = 0;
	@NotNull(message="错误信息:填空试题难度尚未指定！") 
	protected QuestionDifficultyType geneViewDifficulty = null;
}
