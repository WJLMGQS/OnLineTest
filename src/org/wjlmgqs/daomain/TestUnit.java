/**
**作者：翁加林
**时间：2012-7-26
**文件名：TestTopic.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.wjlmgqs.enums.QuestionDifficultyType;

public class TestUnit implements Serializable{

	private static final long serialVersionUID = 1L;
	@Pattern(message="错误信息:答案解析不能为空！",regexp=".*\\S+.*")
	private String analyse ;
	@Pattern(message="错误信息:试题内容不能为空！",regexp=".*\\S+.*")
	private String content ;
	private Date createTime ;
	@NotNull(message="错误信息:试题难度尚未指定！")
	private QuestionDifficultyType difficulty;

	@Min(message="错误信息:试题编号不存在！",value=0)
	private int id;
	private String image ;
	@NotNull(message="错误信息:指定知识点不存在！")
	private Knowledge knowledge;
	
	
	
	public String getAnalyse() {
		return analyse;
	}
	public String getContent() {
		return content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public QuestionDifficultyType getDifficulty() {
		return difficulty;
	}
	public int getId() {
		return id;
	}
	public String getImage() {
		return image;
	}
	
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setDifficulty(QuestionDifficultyType difficulty) {
		this.difficulty = difficulty;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	
}
