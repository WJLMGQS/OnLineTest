/**
 **@author wjlmgqs
 **上午11:58:35
 **SubmitExam.java
 **org.wjlmgqs.web.controller.ajax
 **OnLineTest
 */
package org.wjlmgqs.web.controller.ajax;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.ExamShape;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.web.util.WebLogger;

public class SubmitExam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String examID = request.getParameter("examID");
		if(examID==null){
			showMessage = "错误信息：您尚未指定参加的考试号！";
		}else{
			Map<String,List<ExamShape>> examStudentStatus = (Map<String,List<ExamShape>>) request.getSession().getServletContext().getAttribute("examStudentStatus");
			List<ExamShape> examShapes = null;
			boolean flag = false;
			if(examStudentStatus!=null){
				examShapes = examStudentStatus.get(examID);//考试记录
				if(examShapes!=null){
					Student student = (Student) request.getSession().getAttribute("studentRole");
					for(ExamShape shape : examShapes){
						if(shape.getStudent().getUserId().equals(student.getUserId())){//当前考试存在考试记录
							WebLogger.showInfor("-----------开始结束学生"+student.getUserId()+"考试-----------------");
							TestPaper testPaper = (TestPaper)request.getSession().getAttribute("testPaper");
							shape.setTestPaper(testPaper);
							shape.commitedExam();//改变试卷状态，等待提交
							flag = true;
							WebLogger.showInfor("-----------成功结束学生"+student.getUserId()+"考试-----------------");
							break;
						}
					}
				}
			}
			if(flag){//考试结束
				WebLogger.showInfor("返回考试结束信息到客户端！");//默认返回信息null到客户端，表示成功！
			}else{
				showMessage = "错误信息：学生考试状态丢失！不能提交试卷！";
			}
		}
		response.getWriter().write(showMessage==null?"T":showMessage);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
}
