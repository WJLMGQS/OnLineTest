/**
**作者：翁加林
**时间：2012-7-22
**文件名：Subjects.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest01
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Subject implements Serializable{
	private static final long serialVersionUID = 1L;

	@Pattern(regexp=".*\\S+.*",message="错误信息：科目不能为空")
	private String code = null;
	
	@Min(message="错误信息：科目号范围不正确！",value=0)
	private int id;
	
	public String getCode() {
		return code;
	}
	public int getId() {
		return id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setId(int id) {
		this.id = id;
	}
}
