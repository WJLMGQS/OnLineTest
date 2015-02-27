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

public class Department implements Serializable{
	private static final long serialVersionUID = 1L;
	@Min(message="错误信息：学院号范围不正确！",value=0)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Pattern(regexp=".*\\S+.*",message="错误信息：学院代码不能为空")
	private String code = null;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
