/**
 **作者：翁加林
 **时间：2012-9-18
 **文件名：Paper_labour.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest18
 */
package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;

public class Paper_create_labour extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 *@see	创建新试卷，标注试卷创建者信息(depository_id)"
	 * */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//构建试卷对象
		Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
		TestPaper testPaper = new TestPaper(teacher,TestPaperBulidType.LABOUR,QuestionDifficultyType.SIMPLE);
		List<TestPaper> testPapers = (ArrayList<TestPaper>)request.getSession().getAttribute("testPapers");
		if(testPapers==null){
			testPapers = new ArrayList<TestPaper>();
		}
		testPapers.add(testPaper);
		request.getSession().setAttribute("testPapers",testPapers);
		//为后面直接编辑当前试卷
		request.getSession().setAttribute("testPaper",testPaper);
		request.setAttribute("showMessage","创建试卷成功,请及时保存！");
		request.getRequestDispatcher("/teacher/profession/paper_update").forward(request,response);
	}

}
