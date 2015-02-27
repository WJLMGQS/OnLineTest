/**
 **作者：翁加林
 **时间：2012-8-9
 **文件名：Account_professionSelf_update_pwd.java
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
import org.wjlmgqs.web.util.MD5Code;

public class Account_professionSelf_update_pwd extends HttpServlet {
	private static final long serialVersionUID = -2793297310256827870L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String original = request.getParameter("original");
		String freshF = request.getParameter("freshF");
		//检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Teacher.class,
				new String[]{"password","password"},
				new Object[]{original,freshF});
		if (showMessage == null) {
			MD5Code code = new MD5Code();
			original = code.getMD5ofStr(original);// 数据库中存储加密后的密文
			HttpSession session = request.getSession();
			Teacher t = (Teacher) session.getAttribute("professionTeacher");
			if (t == null) {
				showMessage = "提示:登陆超时，请重新登录！";
			} else {
				if (!original.equals(t.getPassword())) {
					showMessage = "错误信息:原密码错误！";
				} else {// 校验结果正确，可以更新密码
					String fresh = code.getMD5ofStr(freshF);// 产生新的存储到数据库的密文
					// 存入数据库
					ProfessionServiceImp professionServiceImp = new ProfessionServiceImp();
					boolean result = professionServiceImp.updateTeacherPwd(fresh,t.getUserId());
					if (result) {
						// 修改session中teacher对象密码信息
						t.setPassword(fresh);
						session.setAttribute("professionTeacher", t);
						showMessage = "提示:密码更新成功！";
					} else {
						showMessage = "错误信息:服务异常,密码更新失败！";
					}
				}
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/profession/account_professionSelf_update").forward(request, response);
	}
}
