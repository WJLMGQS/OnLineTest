package org.wjlmgqs.daomain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.wjlmgqs.enums.ExamStatus;
import org.wjlmgqs.web.util.BigDecimalValidatorSupport;

public class ExamRecord {
	
	public List<Career> getCareers() {
		return careers;
	}

	public void setCareers(List<Career> careers) {
		this.careers = careers;
	}

	public List<TestPaper> getTestPapers() {
		return testPapers;
	}

	public void setTestPapers(List<TestPaper> testPapers) {
		this.testPapers = testPapers;
	}

	public ExamRecord() {
		super();
	}

	public ExamRecord(String name, String description, int finishTime,
			BigDecimal totalMark,Subject subject) {
		super();
		this.name = name;
		this.description = description;
		this.finishTime = finishTime;
		this.totalMark = totalMark;
		this.subject = subject;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof TestPaper){
			TestPaper vo = (TestPaper)arg0;
			if(vo.getId()!=null){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "考试记录："+"[编号："+this.id+"名称："+this.name+"描述："+this.description+"总分："+this.totalMark+"时间："+this.finishTime+"]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public ExamStatus getStatus() {
		return status;
	}

	public void setStatus(ExamStatus status) {
		this.status = status;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public BigDecimal getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(BigDecimal totalMark) {
		this.totalMark = totalMark;
	}

	/**
	 * @see 考试编号
	 */
	private String id = null;
	
	/**
	 * @see 考试名称
	 */
	@Pattern(message="错误信息：考试名成不能为空！",regexp=".*\\S+.*")
	private String name = null;
	
	/**
	 * @see 考试描述
	 */
	@Pattern(message="错误信息：考试描述不能为空！",regexp=".*\\S+.*")
	private String description = null;
	
	/**
	 * @see 创建该考试记录的时间
	 */
	private Date createTime = new Date();
	
	/**
	 * @see 考试有效时间（不具有代码意义，只是为了提供给管理员识别）
	 */
	@NotNull(message="错误信息：考试开始时间不能为空！")
	private Date startTime = null;
	
	/**
	 * @see 考试结束时间（不具有代码意义，只是为了提供给管理员识别）
	 */
	@NotNull(message="错误信息：考试结束时间不能为空！")
	private Date stopTime = null;
	
	/**
	 * @see 该考试对应的学科
	 */
	@NotNull(message="错误信息：考试学科不能为空！")
	private Subject subject = null;
	
	/**
	 * @see 考试参与的试卷
	 */
	@NotNull(message="错误信息：考试试卷不能为空！")
	private List<TestPaper> testPapers = new ArrayList<TestPaper>();
	
	/**
	 * @see 参与考试的专业
	 */
	@NotNull(message="错误信息：考试专业不能为空！")
	private List<Career> careers = new ArrayList<Career>();
	
	
	
	/**
	 * @see 该考试记录的状态
	 * @category	
	 * 		0：UNOPEN		待开放的考试
	 * 		1：OPENING		开放中的考试
	 * 		2：OPENDED		已结束的考试
	 *  	3：ALL			所有的考试
	 */
	@NotNull(message="错误信息：考试状态错误！")
	private ExamStatus status = ExamStatus.UNOPEN;//默认没有开放
	
	/**
	 * @see 完成该考试需要时间：抽取的试卷会满足指定的完成时间条件显示
	 * [1,720]
	 */
	@Min(value=1)
	@Max(value=720)
	private int finishTime = 120;//默认120分钟
	
	/**
	 * @see 该考试的总分数：抽取的试卷会满足指定的总分数条件显示
	 */
	@BigDecimalValidatorSupport(message="错误信息：考试总分值必须为0.5的整倍数！",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal totalMark = new BigDecimal("100");//默认试卷总分100分
}
