/**
**作者：翁加林
**时间：2012-7-26
**文件名：Career.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Career implements Serializable{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Career other = (Career) obj;
		if (id != other.id)
			return false;
		return true;
	}
	private static final long serialVersionUID = 1L;
	@Pattern(regexp=".*\\S+.*",message="错误信息：专业代码不能为空！")
	private String code = null;
	@NotNull(message="错误信息：学院信息不能为空！")
	private Department department = null;
	@NotNull(message="错误信息：年级信息不能为空！")
	private Grade grade = null;
	@Min(message="错误信息：专业号范围不正确！",value=0)
	private int id;
	
	public String getCode() {
		return code;
	}
	public Department getDepartment() {
		return department;
	}
	public Grade getGrade() {
		return grade;
	}
	public int getId() {
		return id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public void setId(int id) {
		this.id = id;
	}
}
