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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class HelpMan implements Serializable{
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

	@Min(message="错误信息：科目号范围不正确！",value=0)
	private int id ;

	@NotNull(message="错误信息：部门信息不能为空！")
	private Department  department ;

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Pattern(regexp=".*\\S+.*",message="错误信息：辅导员号代码不能为空")
	private  String code = null;                 
}
