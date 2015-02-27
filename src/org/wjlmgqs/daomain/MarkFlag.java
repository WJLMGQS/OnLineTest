package org.wjlmgqs.daomain;

import java.math.BigDecimal;

public class MarkFlag {
	
	private BigDecimal student_scope = null;
	
	public MarkFlag(BigDecimal student_scope ){
		this.student_scope = student_scope;
	}
	
	public BigDecimal getClasses_avg() {
		return classes_avg;
	}
	public void setClasses_avg(BigDecimal classes_avg) {
		this.classes_avg = classes_avg;
	}
	public int getClass_order() {
		return class_order;
	}
	public void setClass_order(int class_order) {
		this.class_order = class_order;
	}
	public BigDecimal getCareer_avg() {
		return career_avg;
	}
	public void setCareer_avg(BigDecimal career_avg) {
		this.career_avg = career_avg;
	}
	public int getCareer_order() {
		return career_order;
	}
	public void setCareer_order(int career_order) {
		this.career_order = career_order;
	}
	public BigDecimal getTotal_avg() {
		return total_avg;
	}
	public void setTotal_avg(BigDecimal total_avg) {
		this.total_avg = total_avg;
	}
	public int getTotal_order() {
		return total_order;
	}
	public void setTotal_order(int total_order) {
		this.total_order = total_order;
	}
	private BigDecimal classes_avg = null;//
	private int class_order = 0;
	private BigDecimal career_avg = null;
	private int career_order = 0;
	private BigDecimal total_avg = null;
	private int total_order = 0;
	
	public void setAVG(BigDecimal classes_avg,BigDecimal career_avg,BigDecimal total_avg){
		this.career_avg = classes_avg;
		this.career_avg = career_avg;
		this.total_avg = total_avg;
	}
	public void setOrder(int class_order,int career_order,int total_order){
		this.class_order = class_order;
		this.career_order = career_order;
		this.total_order = total_order;
	}
	
}
