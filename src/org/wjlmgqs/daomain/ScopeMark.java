package org.wjlmgqs.daomain;

import java.math.BigDecimal;
import java.util.Date;

public class ScopeMark{


	public MarkFlag getMarkFlag() {
		return markFlag;
	}
	public void setMarkFlag(MarkFlag markFlag) {
		this.markFlag = markFlag;
	}
	public String getResponse_id() {
		return response_id;
	}
	public void setResponse_id(String response_id) {
		this.response_id = response_id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public ExamRecord getExamRecord() {
		return examRecord;
	}
	public void setExamRecord(ExamRecord examRecord) {
		this.examRecord = examRecord;
	}
	public BigDecimal getStudent_scope() {
		return student_scope;
	}
	public void setStudent_scope(BigDecimal student_scope) {
		this.student_scope = student_scope;
	}
	public BigDecimal getTest_paper_mark() {
		return test_paper_mark;
	}
	public void setTest_paper_mark(BigDecimal test_paper_mark) {
		this.test_paper_mark = test_paper_mark;
	}
	public Date getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}
	public ScopeMark(Student student, ExamRecord examRecord,
			BigDecimal student_scope, BigDecimal test_paper_mark,String response_id,
			Date finish_time) {
		this.student = student;
		this.examRecord = examRecord;
		this.student_scope = student_scope;
		this.test_paper_mark = test_paper_mark;
		this.response_id = response_id;
		this.finish_time = finish_time;
	}
	private Student student;
	private ExamRecord examRecord;
	private BigDecimal student_scope;
	private BigDecimal test_paper_mark;
	private Date finish_time;
	private String response_id;
	//扩展属性
	private MarkFlag markFlag = null;
}
