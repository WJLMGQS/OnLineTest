/**
 **作者：翁加林
 **时间：2012-8-9
 **文件名：Account_professionSelf_update_info.java
 **包名：org.wjlmgqs.web.controller.profession
 **工程名：OnLineTest10
 */

package org.wjlmgqs.web.controller.profession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Account_professionSelf_update_info extends HttpServlet {
	private static final long serialVersionUID = 5922326787246506160L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Teacher.class,
				new String[]{"name","sex","telephone"},
				new Object[]{name,sex,telephone});
		
		if (showMessage == null) {
			ProfessionServiceImp professionService = new ProfessionServiceImp(); 
			Teacher teacher = (Teacher)request.getSession().getAttribute("professionTeacher");
			if (teacher == null) {
				showMessage = "提示:登陆超时，请重新登录！";
			} else {
				// 存入数据库
				String oldName = teacher.getName();
				teacher.setName(name);
				teacher.setSex(sex);
				teacher.setTelephone(telephone);
				boolean result = professionService.updateProfessionInfoBySelf(teacher);
				if (result) {
					HttpSession session = request.getSession();
					// 修改session中teacher对象密码信息
					if (!teacher.getName().equals(oldName)) {// 如果名称有发生变化，才更新导航栏
						request.setAttribute("refreshLeftMenu","<script language='JavaScript'>top.professionLeftMenu.location.reload();</script>");
					}
					session.setAttribute("professionTeacher", teacher);
					showMessage = "提示:账户信息更新成功！";
				} else {
					showMessage = "错误信息:服务异常,密码更新失败！";
				}
			}
		}
		//System.out.println("showMessage:" + showMessage);
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/account_professionSelf_update").forward(request, response);
	}

}
