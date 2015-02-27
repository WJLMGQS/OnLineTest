/**
**作者：翁加林
**时间：2012-7-26
**文件名：Judge.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import javax.validation.constraints.Pattern;

public class Judge extends TestUnit{

	private static final long serialVersionUID = 1L;
	@Pattern(message="错误信息：试题答案类型错误！",regexp="^[TF]$")
	private String result;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
