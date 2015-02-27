/**
**作者：翁加林
**时间：2012-7-26
**文件名：Classes.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Classes implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotNull(message="错误信息：专业信息不能为空！")
	private Career career = null;
	@Pattern(regexp=".*\\S+.*",message="错误信息：班级代码不能为空！")
	private String code = null;
	@NotNull(message="错误信息：辅导员信息不能为空！")
	private HelpMan helpMan = null;
	@Min(message="错误信息：班级号范围不正确！",value=0)
	private int id ;
	
	public Career getCareer() {
		return career;
	}
	public String getCode() {
		return code;
	}
	public HelpMan getHelpMan() {
		return helpMan;
	}
	public int getId() {
		return id;
	}
	public void setCareer(Career career) {
		this.career = career;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setHelpMan(HelpMan helpMan) {
		this.helpMan = helpMan;
	}
	public void setId(int id) {
		this.id = id;
	}
}
