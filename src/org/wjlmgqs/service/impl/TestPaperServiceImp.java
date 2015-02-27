/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.TestPaperDaoImp;
import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.TestPaperStatusTrack;

public class TestPaperServiceImp {

	public String createTestPaper(TestPaper testPaper) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.createTestPaper(testPaper);
	}

	public String updateTestPaper(TestPaper testPaper) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.updateTestPaper(testPaper);
	}

	public String createOrUpdateTestPaper(TestPaper testPaper) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.createOrUpdateTestPaper(testPaper);
	}

	public List<TestPaper> getAllTestPaperByProfessionId(Teacher teacher,TestPaperStatusTrack paperStatusTrack) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.getAllTestPaperByProfessionId(teacher,paperStatusTrack);
	}
	

	public List<TestPaper> getAllTestPaperByAdminId(Teacher teacher,TestPaperStatusTrack paperStatusTrack) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.getAllTestPaperByAdminId(teacher,paperStatusTrack);
	}

	public TestPaper loadProfessionTestPaperById(Teacher teacher, String bulidId) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.loadProfessionTestPaperById(teacher,bulidId);
	}
	public TestPaper loadAdminTestPaperById(Teacher teacher, String bulidId) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.loadAdminTestPaperById(teacher,bulidId);
	}

	public List<TestPaper> getTestPapersBySubjectId_UserType_Teacher(int subject_id,int finishtime,int totalmark) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.getTestPapersBySubjectId_UserType_Teacher(subject_id,finishtime,totalmark);
	}

	public List<TestPaper> getAdminTestPaperByIds(Teacher teacher,String[] options) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.getAdminTestPaperByIds(teacher,options);
	}

	public TestPaper randomLoadTestPaperByIds(List<TestPaper> testPapers) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.randomLoadTestPaperByIds(testPapers);
	}

	/**
	 * @see 擦出指定试卷的答案啊
	 */
	public void warpResults(TestPaper testPaper) {
		//单选题
		List<Single> singles = testPaper.getSingles();
		for(Single single : singles){
			single.setResult("W");
		}
		//多选题
		List<Multiple>  multiples = testPaper.getMultiples();
		for(Multiple multiple : multiples){
			multiple.setResults(new String[multiple.getResults().length]);
		}
		//判断题
		List<Judge> judges = testPaper.getJudges();
		for(Judge judge : judges){
			judge.setResult("W");
		}
		//填空题
		List<FillBlank> fillBlanks = testPaper.getFillBlanks();
		for(FillBlank fillBlank : fillBlanks){
			fillBlank.setResults(new String[fillBlank.getResults().length]);
		}
	}

	public TestPaper loadTestPaperById(String paperId) {
		TestPaperDaoImp testPaperDaoImp = new TestPaperDaoImp();
		return testPaperDaoImp.loadTestPaperById(paperId);
	}

}
