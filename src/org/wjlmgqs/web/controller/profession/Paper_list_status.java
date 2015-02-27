/**
 **@author wjlmgqs
 **下午08:42:29
 **Paper_all.java
 **org.wjlmgqs.web.controller.profession
 **OnLineTest20.5
 */
package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.TestPaperStatusTrack;
import org.wjlmgqs.service.impl.TestPaperServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Paper_list_status extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}


	/* 
	 * @see 获取该教师下的所有自己的试卷
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String status = request.getParameter("status");
		TestPaperStatusTrack statusTrack = null;
		try {
			int index_status = Integer.parseInt(status);
			statusTrack = TestPaperStatusTrack.values()[index_status];
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(TestPaper.class,"status",statusTrack);
		//检测接受到得数据是否为空，如果为空就直接返回，并提示消息：数据更新失败
		if(showMessage==null){//数据检测通过后更新试卷中的数据信息
			//从Session中获取当前教师对象
			Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
			//查询数据库获得教师对应的所有试卷，并保存到Request中---allTestPapers
			TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
			List<TestPaper> allTestPapers = testPaperServiceImp.getAllTestPaperByProfessionId(teacher,statusTrack);
			request.setAttribute("allTestPapers", allTestPapers);
			request.setAttribute("paperStatus", statusTrack);
			//转向显示所有试卷的页面
			request.getRequestDispatcher("/teacher/profession/paper_list_status.jsp").forward(request,response);
		}else{
			request.setAttribute("showMessage",showMessage);
			request.getRequestDispatcher("/actionFailed.jsp").forward(request, response);	
		}
	}

}
