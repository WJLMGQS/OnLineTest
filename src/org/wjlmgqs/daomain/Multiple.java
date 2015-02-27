/**
**作者：翁加林
**时间：2012-7-26
**文件名：Multipe.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import javax.validation.constraints.Size;

public class Multiple extends TestUnit {

	private static final long serialVersionUID = 1L;
	public String[] getChoices() {
		return choices;
	}
	public void setChoices(String[] choices) {
		this.choices = choices;
	}
	public String[] getResults() {
		return results;
	}
	public void setResults(String[] results) {
		this.results = results;
	}
	@Size(message="错误信息：试题选项不能为空！",min=1)
	private String[] choices;
	@Size(message="错误信息：答案不能为空！",min=1)
	private String[] results;
	
}
