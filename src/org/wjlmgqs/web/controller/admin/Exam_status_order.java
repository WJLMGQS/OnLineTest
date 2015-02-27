/**
 **@author wjlmgqs
 **上午3:16:15
 **Exam_status_order.java
 **org.wjlmgqs.web.controller.admin
 **OnLineTest
 */
package org.wjlmgqs.web.controller.admin;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.ExamShape;
import org.wjlmgqs.enums.ExamStatus;
import org.wjlmgqs.service.impl.ExamRecordServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.WebLogger;

/**
 */
public class Exam_status_order extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String status = request.getParameter("status");
		String local = request.getParameter("local");
		String bulidId = request.getParameter("bulidId");
		ExamStatus examStatus = null;
		ExamStatus localStatus = null;
		try {
			int index_status = Integer.parseInt(status);
			examStatus = ExamStatus.values()[index_status];
		} catch (Exception e) {}
		try {
			int index_local = Integer.parseInt(local);
			localStatus = ExamStatus.values()[index_local];
		} catch (Exception e) {localStatus = ExamStatus.ALL;}
		showMessage = BeanValidateUtil.validatorVoProperty(ExamRecord.class,"status",examStatus);
		if(showMessage==null){
			ExamRecordServiceImp examRecordServiceImp = new ExamRecordServiceImp();
			ExamRecord examRecord = examRecordServiceImp.loadAdminExamById(bulidId);
			if(examRecord==null){
				showMessage = "错误信息：您指定的编号为："+bulidId+"的考试记录不存在！";
			}else{
				examRecord.setStatus(examStatus);
				@SuppressWarnings("unchecked")
				HashMap<String,List<ExamShape>> examShapes = (HashMap<String, List<ExamShape>>) request.getSession().getServletContext().getAttribute("examStudentStatus");
				if(examShapes!=null){
					examShapes.remove(examRecord);//移除该考试的所有考试状态
				}
				showMessage = examRecordServiceImp.updateExamRecord(examRecord);
			}
		}
		request.setAttribute("showMessage",showMessage);
		WebLogger.showInfor(showMessage);
		//转向显示所有试卷的页面
		response.sendRedirect(request.getContextPath()+"/teacher/admin/exam_list?status="+localStatus.getIndex());
	}

}
