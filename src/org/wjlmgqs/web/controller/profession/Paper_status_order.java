/**
 **@author wjlmgqs
 **下午11:05:26
 **Paper_status_order.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest
 */
package org.wjlmgqs.web.controller.profession;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.TestPaperStatusTrack;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.WebLogger;

public class Paper_status_order extends HttpServlet {
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
		TestPaperStatusTrack statusTrack = null;
		TestPaperStatusTrack localTrack = null;
		try {
			int index_status = Integer.parseInt(status);
			statusTrack = TestPaperStatusTrack.values()[index_status];
		} catch (Exception e) {}
		try {
			int index_local = Integer.parseInt(local);
			localTrack = TestPaperStatusTrack.values()[index_local];
		} catch (Exception e) {localTrack = TestPaperStatusTrack.ALL;}
		showMessage = BeanValidateUtil.validatorVoProperty(TestPaper.class,"status",statusTrack);
		//检测接受到得数据是否为空，如果为空就直接返回，并提示消息：数据更新失败
		if(showMessage==null){//数据检测通过后更新试卷中的数据信息
			//从Session中获取当前教师对象
			Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
			//查询数据库获得教师对应的所有试卷，并保存到Request中---allTestPapers
			TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
			TestPaper testPaper = testPaperServiceImp.loadProfessionTestPaperById(teacher, bulidId);
			if(testPaper==null){//flag=false
				showMessage = "错误信息：您指定的编号为："+bulidId+"的试卷不存在！";
			}else{
				if(statusTrack == TestPaperStatusTrack.CREATED || statusTrack == TestPaperStatusTrack.VERIFING){//教师拥有的权限
					testPaper.setStatus(statusTrack);
					showMessage = testPaperServiceImp.updateTestPaper(testPaper);
				}else{
					showMessage = "错误信息：您无权限操作编号为："+bulidId+"的试卷的当前状态！";
				}
			}
		}
		request.setAttribute("showMessage",showMessage);
		WebLogger.showInfor(showMessage);
		//转向显示所有试卷的页面
		response.sendRedirect(request.getContextPath()+"/teacher/profession/paper_list?status="+localTrack.getIndex());
	}

}
