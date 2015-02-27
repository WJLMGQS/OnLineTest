/**
**作者：翁加林
**时间：2012-7-26
**文件名：Class_subject_selected.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class CareerSubjectSelected implements Serializable{
	private static final long serialVersionUID = 1L;
	@Min(message="错误信息：选修号范围不正确！",value=0)
	private int id;
	@NotNull(message="错误信息：学科信息不能为空！")
	private Subject subject;
	@NotNull(message="错误信息：专业信息不能为空！")
	private Career career;
	
	public Career getCareer() {
		return career;
	}
	public void setCareer(Career career) {
		this.career = career;
	}
	public int getId() {
		return id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
