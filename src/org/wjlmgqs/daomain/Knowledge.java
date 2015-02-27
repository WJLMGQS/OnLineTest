/**
**作者：翁加林
**时间：2012-7-26
**文件名：Knowledge.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Knowledge implements Serializable{
	/**
	 * @see
	 */
	private static final long serialVersionUID = 1L;
	@Min(message="错误信息：知识点号范围不正确！",value=0)
	private int id;
	@Pattern(message="错误信息：知识点信息不能为空！",regexp=".*\\S+.*")
	private String code ;
	private Teacher teacher = null;
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
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
}
