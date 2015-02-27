package org.wjlmgqs.daomain;

import org.wjlmgqs.enums.QuestionDifficultyType;

public class GeneView {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public QuestionDifficultyType getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(QuestionDifficultyType difficulty) {
		this.difficulty = difficulty;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	private int id;//试题编号
	private QuestionDifficultyType difficulty;//试题难度
	private int length =1;//基因长度：算分的基本单位

	
	
}
