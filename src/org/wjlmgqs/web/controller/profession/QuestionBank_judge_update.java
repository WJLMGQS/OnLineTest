/**
 **作者：翁加林
 **时间：2012-8-20
 **文件名：QuestionBank_judge_update.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest14
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.JudgeServiceImp;
import org.wjlmgqs.service.impl.KnowledgeServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class QuestionBank_judge_update extends HttpServlet {

	private static final long serialVersionUID = 4961840528794873092L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = "错误信息:试题编号有误！";
		String judgeId = request.getParameter("judgeId");
		// 验证客户端资料
		int judge_id = -1;
		try {
			judge_id = Integer.parseInt(judgeId);
		} catch (Exception e) {}
		showMessage = BeanValidateUtil.validatorVoProperty(Judge.class,"id",judge_id);
		if (showMessage == null) {
			JudgeServiceImp judgeService = new JudgeServiceImp();
			Teacher teacher = (Teacher) request.getSession().getAttribute("professionTeacher");
			// 数据库信息核对成功后删除，并返回删除结果
			Judge judge = judgeService.getJudgeById(judge_id, teacher);
			if (judge == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:学生编号--" + judgeId + "--不存在！";
			} else {
				KnowledgeServiceImp knowledgeServiceImp = new KnowledgeServiceImp();
				List<Knowledge> knowledges = knowledgeServiceImp.getAllKnowledgesByTeacher(teacher);
				request.setAttribute("judge", judge);
				request.setAttribute("knowledges", knowledges);
				request	.getRequestDispatcher("/teacher/profession/questionBank_judge_update.jsp")
						.forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/questionBank_judge").forward(request, response);
	}
}
