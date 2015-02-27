/**
**作者：翁加林
**时间：2012-7-26
**文件名：Response.java
**包名：org.wjlmgqs.daomain
**工程名：OnLineTest03
*/


package org.wjlmgqs.daomain;

import java.io.Serializable;

public class Response implements Serializable{
	public Response(String student_id, String resposne_id, TestPaper testPaper) {
		this.student_id = student_id;
		this.resposne_id = resposne_id;
		this.testPaper = testPaper;
	}
	private static final long serialVersionUID = 1L;
	private String student_id;
	private String resposne_id;
	private TestPaper testPaper = null;
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String studentId) {
		student_id = studentId;
	}
	public String getResposne_id() {
		return resposne_id;
	}
	public void setResposne_id(String resposneId) {
		resposne_id = resposneId;
	}
	public TestPaper getTestPaper() {
		return testPaper;
	}
	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}
}
