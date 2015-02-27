/**
**作者：翁加林
**时间：2012-7-26
**文件名：Grade.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Grade implements Serializable{
	private static final long serialVersionUID = 1L;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Min(message="错误信息：年级号范围不正确！",value=0)
	private int id;
	@Pattern(regexp="\\d{4}",message="错误信息：年级代码格式不正确！")
	private String code = null;
}
