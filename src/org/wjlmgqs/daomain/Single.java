/**
**作者：翁加林
**时间：2012-7-26
**文件名：Single.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

public class Single extends TestUnit implements Serializable{
	private static final long serialVersionUID = 1L;
	@Pattern(message="错误信息：答案类型不匹配！",regexp="^[ABCD]$")
	private String result;
	@Pattern(message="错误信息：选项A不能为空！",regexp=".*\\S+.*")
	private String SA;
	@Pattern(message="错误信息：选项B不能为空！",regexp=".*\\S+.*")
	private String SB;
	@Pattern(message="错误信息：选项C不能为空！",regexp=".*\\S+.*")
	private String SC;
	@Pattern(message="错误信息：选项D不能为空！",regexp=".*\\S+.*")
	private String SD;
	public String getResult() {
		return result;
	}
	public String getSA() {
		return SA;
	}
	public String getSB() {
		return SB;
	}
	public String getSC() {
		return SC;
	}
	public String getSD() {
		return SD;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setSA(String sA) {
		SA = sA;
	}
	public void setSB(String sB) {
		SB = sB;
	}
	public void setSC(String sC) {
		SC = sC;
	}
	public void setSD(String sD) {
		SD = sD;
	}
}
