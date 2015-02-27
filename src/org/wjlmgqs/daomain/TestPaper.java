/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：TestPaper.java
 **包名：org.wjlmgqs.daomain
 **工程名：OnLineTest03
 */

package org.wjlmgqs.daomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;
import org.wjlmgqs.enums.TestPaperStatusTrack;
import org.wjlmgqs.enums.TestPaperUseType;
import org.wjlmgqs.web.util.BigDecimalValidatorSupport;

public class TestPaper implements Serializable{
	public void setTotalMark(BigDecimal totalMark) {
		this.totalMark = totalMark;
	}

	private static final long serialVersionUID = 1L;

	/** 
	 * @see 试卷生成性质：
	 * 		0：教师手动组卷
	 *		1：比例参数随机组卷
	 * @category HAND:手动组卷,AUTO:参数随机组卷
	 * */
	private TestPaperBulidType bulidType = null;

	/** 
	 * @see 创建试卷的时间
	 *  * */
	private Date createTime = new Date();

	@NotNull(message="错误信息：试卷难度类型指定错误！")
	private QuestionDifficultyType difficulty;

	/** 
	 * @see 填空题总分 
	 * * */
	@BigDecimalValidatorSupport(message="错误信息：填空题分值必须为0.5的整倍数！",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal fillBlankMark = new BigDecimal("0.5");

	/** 
	 * @see 填空题集合 
	 * * */
	private List<FillBlank> fillBlanks = new ArrayList<FillBlank>();

	/**
	 * @see 学生完成试卷的总时间 
	 * [1,720]
	 * * */
	@Min(value=1)
	@Max(value=720)
	private int finishTime = 120;

	/** 
	 * @see 用日期标注2009年08月08日18时18分36秒 
	 * * */
	private String id ;
	/**
	 * 状态为标志：是否已经保存当前编辑的试卷，该属性不加入数据库记录中
	 */
	private boolean isSave = false;
	
	/** 
	 * @see 判断总分 
	 * * */
	@BigDecimalValidatorSupport(message="错误信息：判断题分值必须为0.5的整倍数！",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal judgeMark = new BigDecimal("0.5");
	
	/** 
	 * @see 判断题集合 
	 * * */
	private List<Judge> judges = new ArrayList<Judge>();
	
	/** 
	 * @see 多选题总分
	 *  * */
	@BigDecimalValidatorSupport(message="错误信息：多选题分值必须为0.5的整倍数！",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal multipleMark = new BigDecimal("0.5");

	/** 
	 * @see 多选题集合 
	 * * */
	private List<Multiple> multiples = new ArrayList<Multiple>();
	/** 
	 * @see 试卷名称 
	 * * */
	@Pattern(message="错误信息：试卷名不能为空！",regexp=".*\\S+.*")
	private String name = "新建试卷";

	/** 
	 * @see 单选题总分 
	 * * */
	@BigDecimalValidatorSupport(message="错误信息：单选题分值必须为0.5的整倍数！",regex="(^0\\.50*$)|(^[1-9]+\\d*(\\.[05])?0*$)")
	private BigDecimal singleMark = new BigDecimal("0.5");

	/** 
	 * @see 单选题集合 
	 * * */
	private List<Single> singles = new ArrayList<Single>();

	/**
	 * @see 试卷状态
	 * */
	@NotNull(message="错误信息：试卷审核类型错误！")
	private TestPaperStatusTrack status = TestPaperStatusTrack.CREATED;
	/** 
	 * @see 属于教师的试卷仓库
	 *  * */
	private Teacher teacher;

	/** 
	 * @see 最近修改试卷的时间
	 *  * */
	private Date updateTime = new Date();

	@NotNull(message="错误信息：试卷使用方式指定错误！")
	private TestPaperUseType useType = TestPaperUseType.EXAM;

	/**
	 * @see 试卷总分，不保存在数据库，每次获取通过动态计算获得，本来是不打算写这个字段的，可是Gson貌似不支持用方法获得，没办法就写了
	 */
	private BigDecimal totalMark = new BigDecimal("0");

	/**
	 * @see 构造方法，创建试卷对象，并初始化相关数据(试卷编号)
	 * @param teacher 出卷教师（主要是绑定试卷的归属）
	 * @param bulidType 试卷的生成性质：手动，自动
	 * */
	public TestPaper(Teacher teacher,TestPaperBulidType bulidType,QuestionDifficultyType difficulty){
		this.teacher = teacher;
		this.bulidType = bulidType;
		this.difficulty = difficulty;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		synchronized (this) {
			Date dateId = new Date();
			this.id = sdf.format(dateId);
		}
	}

	public TestPaper() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestPaper other = (TestPaper) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public TestPaperBulidType getBulidType() {
		return bulidType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public QuestionDifficultyType getDifficulty() {
		return difficulty;
	}

	public BigDecimal getFillBlankMark() {
		return fillBlankMark;
	}


	public BigDecimal getFillBlankMarkUnit(){
		int size = 0;
		for(FillBlank fill:fillBlanks){
			size += fill.getResults().length;
		}
		return fillBlankMark.multiply(new BigDecimal(size));
	};

	public int getFillBlankMarkUnitNumber(){
		int size = 0;
		for(FillBlank fill:fillBlanks){
			size += fill.getResults().length;
		}
		return size;
	}

	public List<FillBlank> getFillBlanks() {
		return fillBlanks;
	}

	public int getFinishTime() {
		return finishTime;
	};

	public String getId() {
		return id;
	}

	public BigDecimal getJudgeMark() {
		return judgeMark;
	}

	public BigDecimal getJudgeMarkUnit(){
		return judgeMark.multiply(new BigDecimal(judges.size()));
	}

	public int getJudgeMarkUnitNumber(){
		return judges.size();
	}

	public List<Judge> getJudges() {
		return judges;
	};

	public BigDecimal getMultipleMark() {
		return multipleMark;
	}

	public BigDecimal getMultipleMarkUnit(){
		return multipleMark.multiply(new BigDecimal(multiples.size()));
	}

	public int getMultipleMarkUnitNumber(){
		return multiples.size();
	}

	public List<Multiple> getMultiples() {
		return multiples;
	}

	public String getName() {
		return name;
	}

	public boolean getSave() {
		return isSave;
	}

	public BigDecimal getSingleMark() {
		return singleMark;
	}

	/**
	 * 获取每种题型的总分
	 * */
	public BigDecimal getSingleMarkUnit(){
		return singleMark.multiply(new BigDecimal(singles.size()));
	}

	/**
	 * 获取每种题型计分的基本单位数
	 * */
	public int getSingleMarkUnitNumber(){
		return singles.size();
	}

	public List<Single> getSingles() {
		return singles;
	}


	public TestPaperStatusTrack getStatus() {
		return status;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * 获取当前试卷的总分
	 * */
	public BigDecimal getTotalMark(){
		this.totalMark  = getSingleMarkUnit().add(getMultipleMarkUnit().add(getJudgeMarkUnit().add(getFillBlankMarkUnit())));
		return totalMark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public TestPaperUseType getUseType() {
		return useType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public void setBulidType(TestPaperBulidType bulidType) {
		this.bulidType = bulidType;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setDifficulty(QuestionDifficultyType difficulty) {
		this.difficulty = difficulty;
	}

	public void setFillBlankMark(BigDecimal fillBlankMark) {
		this.fillBlankMark = fillBlankMark;
	}
	
	public void setFillBlanks(List<FillBlank> fillBlanks) {
		this.fillBlanks = fillBlanks;
	}
	
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setJudgeMark(BigDecimal judgeMark) {
		this.judgeMark = judgeMark;
	}
	
	public void setJudges(List<Judge> judges) {
		this.judges = judges;
	}
	
	public void setMultipleMark(BigDecimal multipleMark) {
		this.multipleMark = multipleMark;
	}
	
	public void setMultiples(List<Multiple> multiples) {
		this.multiples = multiples;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public void setSingleMark(BigDecimal singleMark) {
		this.singleMark = singleMark;
	}

	public void setSingles(List<Single> singles) {
		this.singles = singles;
	}
	public void setStatus(TestPaperStatusTrack status) {
		this.status = status;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setUseType(TestPaperUseType useType) {
		this.useType = useType;
	}

}
