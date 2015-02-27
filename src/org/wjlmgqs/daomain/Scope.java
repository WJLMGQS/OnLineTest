/**
**作者：翁加林
**时间：2012-7-26
**文件名：Scope.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;
import java.util.Date;

public class Scope implements Serializable{
	private static final long serialVersionUID = 1L;
	private Subject subject;
	private Student student = null;
	private float student_scope;
	private Response response = null;
	private float test_paper_mark;
	private Date finish_time = null;
	
	
	public Date getFinish_time() {
		return finish_time;
	}
	public Response getResponse() {
		return response;
	}
	public Student getStudent() {
		return student;
	}
	public float getStudent_scope() {
		return student_scope;
	}
	public Subject getSubject() {
		return subject;
	}
	public float getTest_paper_mark() {
		return test_paper_mark;
	}
	public void setFinish_time(Date finishTime) {
		finish_time = finishTime;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setStudent_scope(float studentScope) {
		student_scope = studentScope;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public void setTest_paper_mark(float testPaperMark) {
		test_paper_mark = testPaperMark;
	}
}
