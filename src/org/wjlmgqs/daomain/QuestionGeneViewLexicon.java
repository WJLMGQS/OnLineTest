/**
 **@author wjlmgqs
 **下午5:01:17
 **QuestionChromosomeView.java
 **org.wjlmgqs.daomain
 **OnLineTest
 */
package org.wjlmgqs.daomain;

import java.util.List;

import org.wjlmgqs.enums.QuestionDifficultyType;


/**
 * 保存指定类型试题的所有基因
 */
public class QuestionGeneViewLexicon {
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
	protected int geneViewNumber = 0;
	protected QuestionDifficultyType geneViewDifficulty = null;
	
	
	private String type = null;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
