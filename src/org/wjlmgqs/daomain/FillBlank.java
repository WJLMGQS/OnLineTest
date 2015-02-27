/**
**作者：翁加林
**时间：2012-7-26
**文件名：FillBlank.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import javax.validation.constraints.Size;


public class FillBlank extends TestUnit{

	private static final long serialVersionUID = 1L;
	
	@Size(min=1,message="错误信息：答案不能为空！")
	String[] results ;

	public String[] getResults() {
		return results;
	}

	public void setResults(String[] results) {
		this.results = results;
	}
}
