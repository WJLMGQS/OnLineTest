/**
 **@author wjlmgqs
 **下午11:22:13
 **ExamShape.java
 **org.wjlmgqs.daomain
 **OnLineTest
 */
package org.wjlmgqs.daomain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.wjlmgqs.service.impl.ScopeMarkServiceImp;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.WebLogger;

/**
 *  @see 描述了当前考试编号下某学生的考试状态
 *  	学生、试卷、是否已经提交、考试总时间、完成时间、定时器
 */
public class ExamShape {

	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}

	@Override
	public String toString() {
		return "ExamShape [student=" + student.getUserId() + ", testPaper=" + testPaper.getId()
				+ "]";
	}

	public ExamRecord getExamRecord() {
		return examRecord;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Student getStudent() {
		return student;
	}

	public TestPaper getTestPaper() {
		return testPaper;
	}

	public boolean isCommit() {
		return isCommit;
	}

	public void setCommit(boolean isCommit) {
		this.isCommit = isCommit;
	}

	public int getTotalTime() {
		return totalTime;
	}

	private ExamRecord examRecord = null;
	private Student student;
	private TestPaper testPaper;
	private boolean isCommit =false;
	private int totalTime;//考试总时间，默认120分钟，这里的单位是分钟
	private Timer timer = new Timer(); 
	private Date finishTime = null;//考生完成考试时间
	private ServletContext context = null;
	private int bufferTime = 10;	//延迟10分钟
	private int splitTime = 1000*10;//扫描时间间隔1分钟
	
	/**
	 *	@see 为学生创建该对象的时候，该对象自动加入容器中并开启交卷定时任务，没一分钟扫描一次。
	 *	@param
	 *		student			当前参加考试的学生
	 *		testPaper		选中的试卷
	 *		examRecord		当前参加的考试
	 *		context			保存学生考试状态的上下文容器
	 */
	public ExamShape(final Student student,TestPaper testPaper,ExamRecord examRecord,ServletContext context){
		this.student = student;
		this.testPaper = testPaper;
		this.examRecord = examRecord;
		this.totalTime = examRecord.getFinishTime();
		this.context = context;
		/**
		 * 从容器中获取考试状态，并将当前状态加入
		 */
		@SuppressWarnings("unchecked")
		Map<String,List<ExamShape>> examStudentStatus = (Map<String, List<ExamShape>>) context.getAttribute("examStudentStatus");
		List<ExamShape> examShapes = null;
		if(examStudentStatus==null){
			examStudentStatus = new HashMap<String, List<ExamShape>>();
		}
		examShapes = examStudentStatus.get(this.examRecord.getId());//考试记录
		if(examShapes==null){
			examShapes = new ArrayList<ExamShape>();
		}
		examShapes.add(this);//加入当前状态到考前编号的考试
		examStudentStatus.put(this.examRecord.getId(),examShapes);//新添加一种考试编号
		context.setAttribute("examStudentStatus",examStudentStatus);//保存考试记录到容器中
	    timer.schedule(new TimerTask() {
			@Override
			public void run() {
				WebLogger.showInfor("学生："+student.getUserId()+"定时任务执行中。。。。。。。。");
				//考试状态为true或者当考试时间结束,就自动结束当前学生的考试任务(学生主动提交)
				if(isCommit || (totalTime + bufferTime<=0)){//比客户端多出10分钟时间
					finishExam();//结束
				}
				totalTime--;
			}
		},1000,splitTime);//1分钟执行一次扫描  
	}
	
	/**
	 *	@see 结束当前考试的考试，并保存考试结果到数据库及清楚在容器中的考试状态
	 */
	private void finishExam(){
		finishTime = new Date();
		timer.cancel();//结束任务
		//统计考试分数
		BigDecimal examMark = getExamMark();
		//保存答卷试卷到数据库
		saveExamMark(examMark);
		WebLogger.showInfor("学生："+student.getUserId()+"结束考试任务。。。。。。。。");
		
	}
	
	/**
	 * @see  获得考试得分
	 */
	private BigDecimal getExamMark(){
		WebLogger.showInfor("保存考试分数，保存考试成绩结果");
		//从数据库加载保存有答案的同一试卷
		TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
		TestPaper loadTestPaper = testPaperServiceImp.loadTestPaperById(testPaper.getId());
		//单选题
		List<Single> loadSingles = loadTestPaper.getSingles();
		List<Single> singles = testPaper.getSingles();
		BigDecimal singleTotal = new BigDecimal("0");
		for(int i=0;i<singles.size();i++){
			Single single = singles.get(i);
			String loadSingle = loadSingles.get(i).getResult().trim();
			String result = single.getResult();
			if(result==null){
				continue;
			}else{
				result = result.trim();
			}
			if(loadSingle.equals(result)){//得分
				singleTotal = singleTotal.add(testPaper.getSingleMark());
			}
		}
		//多选题
		List<Multiple> loadMultiples = loadTestPaper.getMultiples();
		List<Multiple> multiples = testPaper.getMultiples();
		BigDecimal multipleTotal = new BigDecimal("0");
		for(int i=0;i<singles.size();i++){//必须所有选项都对才给分
			String[] loadMultiple = loadMultiples.get(i).getResults();
			Multiple multiple = multiples.get(i);
			String[] results = multiple.getResults();
			boolean flag = true;
			for(int j=0;j<results.length;j++){
				if("1".equals(loadMultiple[j]) && (results[j]==null || !"1".equals(results[j].trim()))){//只要有一个选项对不上就错误
					flag = false;
					break;
				}
			}
			if(flag){
				multipleTotal = multipleTotal.add(testPaper.getMultipleMark());
			}
		}
		//判断题
		List<Judge> loadJudges = loadTestPaper.getJudges();
		List<Judge> judges = testPaper.getJudges();
		BigDecimal judgeTotal = new BigDecimal("0");
		for(int i=0;i<judges.size();i++){
			String loadJudge = loadJudges.get(i).getResult().trim();
			Judge judge = judges.get(i);
			String result = judge.getResult();
			if(result==null){
				continue;
			}else{
				result = result.trim();
			}
			if(loadJudge.equals(result)){//得分
				judgeTotal = judgeTotal.add(testPaper.getJudgeMark());
			}
		}
		//填空题
		List<FillBlank> loadFillBlanks = loadTestPaper.getFillBlanks();
		List<FillBlank> fillBlanks = testPaper.getFillBlanks();
		BigDecimal fillBlankTotal = new BigDecimal("0");
		for(int i=0;i<fillBlanks.size();i++){
			String[] loadFillBlank = loadFillBlanks.get(i).getResults();
			FillBlank fillBlank = fillBlanks.get(i);
			String[] results = fillBlank.getResults();
			for(int j=0;j<results.length;j++){
				String[] split = loadFillBlank[j].split("░");
				for(String s : split){
					if(results[j]==null){
						continue;
					}
					String trim = results[j].trim();
					if(trim!=null && trim.equals(s.trim()) && !trim.equals("")){//一个格加一次分,排除空格情况
						fillBlankTotal = fillBlankTotal.add(testPaper.getFillBlankMark());
						break;
					}
				}
			}
		}
		//统计总分
		return singleTotal.add(judgeTotal).add(fillBlankTotal).add(multipleTotal);
	}
	
	/**
	 * @see 保存考试成绩到数据库
	 */
	private void saveExamMark(BigDecimal examMark){
		ScopeMark scopeMark = new ScopeMark(student, examRecord, examMark, testPaper.getTotalMark(),testPaper.getId(), finishTime);
		ScopeMarkServiceImp scopeMarkServiceImp = new ScopeMarkServiceImp();
		scopeMarkServiceImp.createScopeMark(scopeMark);
		WebLogger.showInfor("保存答卷试卷到数据库"); 
	}
	
	
	
	
	
	/**
	 * @see 学生主动提交试卷
	 */
	public void commitedExam(){
		this.isCommit = true;
	}

	
}
