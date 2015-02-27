/**
 **作者：翁加林
 **时间：2012-7-20
 **文件名：Admin_account_admin_updateInfo.java
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
/*
 * 	校验管理员信息格式，更新
 * */
public class Account_adminSelf_update_info extends HttpServlet {
	private static final long serialVersionUID = 5209619864110919290L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String showMessage = null;
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Teacher.class,
				new String[]{"name","sex","telephone"},
				new Object[]{name,sex,telephone});
		//检测格式及正确性
		if(showMessage==null) {		
			AdminServiceImp adminService = new AdminServiceImp();
			Teacher teacher = (Teacher)request.getSession().getAttribute("adminTeacher");
			if(teacher==null) {
				showMessage = "提示:登陆超时，请重新登录！";
			}else {
				//存入数据库
				String oldName = teacher.getName();
				teacher.setName(name.trim());
				teacher.setSex(sex);
				teacher.setTelephone(telephone);
				boolean result = adminService.updateAdminInfo(teacher);
				if(result) {
					HttpSession session = request.getSession();
					//修改session中teacher对象密码信息
					if(!teacher.getName().equals(oldName)) {//如果名称有发生变化，才更新导航栏
						request.setAttribute("refreshLeftMenu", "<script language='JavaScript'>top.adminLeftMenu.location.reload();</script>");
					}
					session.setAttribute("adminTeacher", teacher);
					showMessage = "提示:账户信息更新成功！";
				}else {
					showMessage = "错误信息:服务异常,密码更新失败！";
				}
			}
		}
		request.setAttribute("showMessage",showMessage);
		request.getRequestDispatcher("/teacher/admin/account_adminSelf_update").forward(request, response);
	}

}
