/**
 **@author wjlmgqs
 **下午11:46:27
 **Exam_record_create.java
 **org.wjlmgqs.web.controller.admin
 **OnLineTest
 */
package org.wjlmgqs.web.controller.admin;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.WebLogger;
public class Exam_record_create extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *	@see 创建考试记录
	 *	@category	创建需要的参数：
	 *		考试名称	name 			试卷描述	description
	 *		考试总时间	finishTime  	考试总分	totalMark
	 *		参与的试卷编号	options 	参与的专业	careers
	 *		考试的科目	subjectId 		考试开始时间startTime
	 *		考试结束时间stopTime 		参与考试的专业 careers
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String[] options = request.getParameterValues("options");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String _finishTime = request.getParameter("finishTime");
		String _startTime = request.getParameter("startTime");
		String _stopTime = request.getParameter("stopTime");
		String _totalMark = request.getParameter("totalMark");
		String _subjectId = request.getParameter("subjectId");
		String[] careerIds = request.getParameterValues("careerId");
		int finishTime = 0;
		BigDecimal totalMark = new BigDecimal("0.1");
		Subject subject = null;
		List<TestPaper> testPapers = null;
		List<Career> careers = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date startTime = null;
		Date stopTime = null;
		try {finishTime = Integer.parseInt(_finishTime);} catch (Exception e) {}
		try {startTime = dateFormat.parse(_startTime);} catch (Exception e) {}
		try {stopTime = dateFormat.parse(_stopTime);} catch (Exception e) {}
		try {totalMark = new BigDecimal(_totalMark);} catch (Exception e) {}
		try {
			SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
			int subjectId = Integer.parseInt(_subjectId);
			subject = subjectServiceImp.getSubjectById(subjectId);
		} catch (Exception e) {}
		try {
			CareerServiceImp careerServiceImp = new CareerServiceImp();
			careers = careerServiceImp.getCareerByIds(careerIds);
		} catch (Exception e) {}
		try {
			Teacher teacher = (Teacher) request.getSession().getAttribute("adminTeacher");
			TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
			testPapers = testPaperServiceImp.getAdminTestPaperByIds(teacher,options);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(ExamRecord.class, 
				new String[]{"startTime","stopTime","name","description","finishTime","totalMark","subject","testPapers","careers"}, 
				new Object[]{startTime,stopTime,name,description,finishTime,totalMark,subject,testPapers,careers});
		if(showMessage==null){
			ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
			ExamRecord examRecord = new ExamRecord(name, description, finishTime, totalMark, subject);
			examRecord.setTestPapers(testPapers);
			examRecord.setStartTime(startTime);
			examRecord.setStopTime(stopTime);
			examRecord.setCareers(careers);
			showMessage = examRecordServiceImp.createExamRecord(examRecord);
		}
		WebLogger.showInfor(showMessage);
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/exam_createUI").forward(request, response);
	}
	
	
	
}
