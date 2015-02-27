/**
 **@author wjlmgqs
 **下午2:17:01
 **Name_examRecord.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest
 */
package org.wjlmgqs.web.controller.profession;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;

public class Name_examRecord extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
		List<ExamRecord> allExamRecords = examRecordServiceImp.getAllFinishExamRecordBySubjectId(teacher.getSubject().getId());
		request.setAttribute("allExamRecords", allExamRecords);
		request.getRequestDispatcher("/teacher/profession/name_exam_record.jsp").forward(request,response);
	
	}
}
