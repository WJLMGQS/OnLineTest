/**
 **@author wjlmgqs
 **下午4:10:56
 **BulidView.java
 **org.wjlmgqs.daomain
 **OnLineTest
 */
package org.wjlmgqs.daomain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 试卷染色体组视图
 * 遗传组卷需要的参数对象：一份试卷=四份染色体
 */
public class ChromosomeView {
	public String[] getKnowledgeIds() {
		return knowledgeIds;
	}
	public void setKnowledgeIds(String[] knowledgeIds) {
		this.knowledgeIds = knowledgeIds;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public FillBlankChromosomeView getFillBlankChromosomeView() {
		return fillBlankChromosomeView;
	}
	public void setFillBlankChromosomeView(
			FillBlankChromosomeView fillBlankChromosomeView) {
		this.fillBlankChromosomeView = fillBlankChromosomeView;
	}
	public MultipleChromosomeView getMultipleChromosomeView() {
		return multipleChromosomeView;
	}
	public void setMultipleChromosomeView(
			MultipleChromosomeView multipleChromosomeView) {
		this.multipleChromosomeView = multipleChromosomeView;
	}
	public SingleChromosomeView getSingleChromosomeView() {
		return singleChromosomeView;
	}
	public void setSingleChromosomeView(SingleChromosomeView singleChromosomeView) {
		this.singleChromosomeView = singleChromosomeView;
	}
	public JudgeChromosomeView getJudgeChromosomeView() {
		return judgeChromosomeView;
	}
	public void setJudgeChromosomeView(JudgeChromosomeView judgeChromosomeView) {
		this.judgeChromosomeView = judgeChromosomeView;
	}
	@NotNull(message="错误信息：您尚未指定组卷的知识点范围")
	private String[] knowledgeIds = null;
	private Teacher teacher = null;
	
	public ChromosomeView() {
		this.fillBlankChromosomeView = new FillBlankChromosomeView();
		this.multipleChromosomeView = new MultipleChromosomeView();
		this.singleChromosomeView = new SingleChromosomeView();
		this.judgeChromosomeView = new JudgeChromosomeView();
	}
	@Valid
	FillBlankChromosomeView fillBlankChromosomeView = null;
	@Valid
	MultipleChromosomeView multipleChromosomeView = null;
	@Valid
	SingleChromosomeView singleChromosomeView = null;
	@Valid
	JudgeChromosomeView judgeChromosomeView = null;
}
