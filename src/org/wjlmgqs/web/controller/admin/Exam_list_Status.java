/**
 **@author wjlmgqs
 **下午11:36:09
 **Exam_list.java
 **org.wjlmgqs.web.controller
 **OnLineTest
 */
package org.wjlmgqs.web.controller.admin;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.enums.ExamStatus;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

/**
 * @see 考试记录列表，针对教师爷可能查看自己对应学科的考试记录情况，保留Web.xml配置中的命名规范Admin_Exam_list
 */
public class Exam_list_Status extends HttpServlet {

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
		String showMessage = null;
		String status = request.getParameter("status");
		ExamStatus examStatus = null;
		try {
			int index_status = Integer.parseInt(status);
			examStatus = ExamStatus.values()[index_status];
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(ExamRecord.class,"status",examStatus);
		//检测接受到得数据是否为空，如果为空就直接返回，并提示消息：数据更新失败
		if(showMessage==null){//数据检测通过后更新试卷中的数据信息
			//从Session中获取当前教师对象
			//查询数据库获得教师对应的所有考试记录，并保存到Request中---allExamRecords
			ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
			List<ExamRecord> allExamRecords = examRecordServiceImp.getAllExamRecord(examStatus);
			request.setAttribute("allExamRecords", allExamRecords);
			request.setAttribute("examsStatus", examStatus);
			//转向显示所有试卷的页面
			request.getRequestDispatcher("/teacher/admin/exam_list_status.jsp").forward(request,response);
		}else{
			request.setAttribute("showMessage",showMessage);
			request.getRequestDispatcher("/actionFailed.jsp").forward(request, response);	
		}
	}
}
