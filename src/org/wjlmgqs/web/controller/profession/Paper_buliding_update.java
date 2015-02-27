package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.service.impl.TestPaperServiceImp;

public class Paper_buliding_update extends HttpServlet {

	/**
	 * @see
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@see 将需要编辑的页面放入session中，在update页面跳转到编辑页
	 *		作为所有试卷的编辑入口，在这里根据用户需要的试卷ID从数据库中获取，然后放入Session中（testPapers,testPaper）,在编辑页面得到testPaper,在编辑过的页面显示testPapers
	 * */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bulidId = request.getParameter("bulidId");
		HttpSession session = request.getSession();
		TestPaper testPaper = null;
		List<TestPaper> testPapers = (List<TestPaper>) session.getAttribute("testPapers");
		//先从Session中查找
		if(testPapers==null || testPapers.size()==0){
			testPapers = new ArrayList<TestPaper>();
			session.setAttribute("testPapers", testPapers);
		}else{
			for(TestPaper t : testPapers){
				if(t.getId().equals(bulidId)){
					testPaper = t;
					break;
				}
			}
		}
		//如果Session中没有就从数据库加载
		if(testPaper==null){
			TestPaperServiceImp testPaperServiceImp = new TestPaperServiceImp();
			Teacher teacher = (Teacher)session.getAttribute("professionTeacher");
			testPaper = testPaperServiceImp.loadProfessionTestPaperById(teacher,bulidId);
			if(testPaper!=null){
				testPaper.setSave(true);
				testPapers.add(testPaper);
				session.setAttribute("testPapers",testPapers);//为正在编辑提供列表页
			}
		}
		if(testPaper==null){
			request.setAttribute("showMessage", "您指定的试卷尚未保存呢，不能编辑！");
			request.getRequestDispatcher("/teacher/profession/paper_buliding").forward(request, response);
			return;
		}else{
			session.setAttribute("testPaper",testPaper);//为编辑页提供数据
			request.getRequestDispatcher("/teacher/profession/paper_update.jsp").forward(request, response);
			return;
		}
	}

}
