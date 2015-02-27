/**
 **@author wjlmgqs
 **下午7:04:35
 **Join_exam.java
 **org.wjlmgqs.web.controller.student
 **OnLineTest
 */
package org.wjlmgqs.web.controller.student;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.enums.ExamStatus;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;

public class Join_exam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	/* 
	 * @see 获取该教师下的所有自己的试卷
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
		List<ExamRecord> allExamRecords = examRecordServiceImp.getAllExamRecord(ExamStatus.ALL);
		request.setAttribute("allExamRecords", allExamRecords);
		request.getRequestDispatcher("/student/exam_list_join.jsp").forward(request,response);
	}
	
}
