/**
 **作者：翁加林
 **时间：2012-7-20
 **文件名：Admin_account_admin_updatePwd.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.AdminServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;
/*
 * 	验证原密码，更新管理员密码
 * */
public class Account_adminSelf_update_pwd extends HttpServlet {
	private static final long serialVersionUID = -1659794007073260601L;

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
		if(showMessage==null){
			MD5Code code = new MD5Code();
			original = code.getMD5ofStr(original);//数据库中存储加密后的密文
			HttpSession session = request.getSession();
			Teacher t = (Teacher)session.getAttribute("adminTeacher");
			if(t==null) {
				showMessage = "提示:登陆超时，请重新登录！";
			}else {
				if(!original.equals(t.getPassword())) {
					showMessage = "错误信息:原密码错误！";
				}else {//校验结果正确，可以更新密码
					String fresh = code.getMD5ofStr(freshF);//产生新的存储到数据库的密文
					//存入数据库
					AdminServiceImp adminService = new AdminServiceImp();
					boolean result = adminService.updateTeacherPwd(fresh, t.getUserId());
					if(result) {
						//修改session中teacher对象密码信息
						t.setPassword(fresh);
						session.setAttribute("adminTeacher", t);
						showMessage = "提示:密码更新成功！";
					}else {
						showMessage = "错误信息:服务异常,密码更新失败！";
					}
				}
			}
		}
		//System.out.println("showMessage:"+showMessage);
		request.setAttribute("showMessage",showMessage);
		request.getRequestDispatcher("/teacher/admin/account_adminSelf_update").forward(request, response);
	}
}
