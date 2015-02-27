/**
 **@author wjlmgqs
 **下午11:00:28
 **Exam_join_testUI.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.ExamShape;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.WebLogger;

public class Exam_join_testUI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 *	@see 完成学生参加考试的页面跳转
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		//根据参数获取考试编号
		String recordId = request.getParameter("recordId");
		ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
		ExamRecord examRecord = examRecordServiceImp.getExamRecordById(recordId);
		if(examRecord==null || examRecord.getTestPapers().size()==0 || examRecord.getCareers().size()==0){
			showMessage = "提示信息：您指定的考试不存在！";
		}
		//获得当前参加考试的学生对象
		Student student = (Student) request.getSession().getAttribute("studentRole");
		int careerId = student.getClasses().getCareer().getId();
		Career career = new Career();
		career.setId(careerId);
		if(!examRecord.getCareers().contains(career)){//不具有考试的权限
			showMessage = "提示信息：当前用户不具备参加该考试的资格！";
		}
		if(showMessage==null){
			//判断当前学生是否是继续参加考试
			ServletContext context = request.getSession().getServletContext();
			@SuppressWarnings("unchecked")
			Map<String,List<ExamShape>> examStudentStatus = (Map<String, List<ExamShape>>) context.getAttribute("examStudentStatus");
			List<ExamShape> examShapes = null;
			ExamShape examShape = null;
			if(examStudentStatus==null){
				examStudentStatus = new HashMap<String, List<ExamShape>>();
				context.setAttribute("examStudentStatus", examStudentStatus);
			}
			examShapes = examStudentStatus.get(recordId);//考试记录
			if(examShapes!=null){
				for(ExamShape shape : examShapes){
					if(shape.getStudent().getUserId().equals(student.getUserId())){//当前考试存在考试记录
						examShape = shape;
						if(examShape.isCommit()){//表示已经考过了
							showMessage = "提示信息：当前用户已经参加过该考试！";
							WebLogger.showInfor("当前用户已经参加过该考试："+student.getUserId());
						}else{
							WebLogger.showInfor("该学生继续参加考试："+student.getUserId());
						}
						break;
					}
				}
			}else{
				examShapes = new ArrayList<ExamShape>();
				examStudentStatus.put(recordId, examShapes);
			}
			if(showMessage==null){
				TestPaper testPaper = null;//考试试卷
				if(examShape==null){//需要建立新的考试
					List<TestPaper> testPapers = examRecord.getTestPapers();
					TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
					testPaper = testPaperServiceImp.randomLoadTestPaperByIds(testPapers);
					testPaperServiceImp.warpResults(testPaper);//擦出试题答案
					examShape = new ExamShape(student, testPaper, examRecord, context);//自动加入容器中并开始定时任务
					WebLogger.showInfor("该学生开始参加考试："+student.getUserId());
				}else{
					testPaper = examShape.getTestPaper();
				}
				WebLogger.showInfor("返回给学生的试卷："+testPaper.getId());
				request.getSession().setAttribute("testPaper",testPaper);
				request.setAttribute("lastTime",examShape.getTotalTime()*60);
				request.setAttribute("examID",examShape.getExamRecord().getId());
				request.getRequestDispatcher("/student/start_exam.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/student/join_exam").forward(request, response);
	}

	
}
