/**
 **作者：翁加林
 **时间：2012-7-16
 **文件名：Distribute2Master.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest01
 */

package org.wjlmgqs.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;
import org.wjlmgqs.web.util.WebLogger;

public class Redirect2Student extends HttpServlet {
	private static final long serialVersionUID = -8695292588639622402L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 校验：
	 * 	* userId
	 * 	* password
	 * 	* power
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		//检测格式，返回校验结果
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Student.class, 
				new String[]{"userId","password"}, 
				new Object[]{userId,password});
		//输入格式正确，开始检查账户信息是否正确
		if(showMessage==null) {	
			Student student = null;
			HttpSession session = request.getSession();
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);//数据库中存储加密后的密文
			StudentServiceImp studentServiceImp = new StudentServiceImp();
			student = studentServiceImp.studentLogin(userId,password);
			if(student!=null){
				session.setAttribute("studentRole", student);
				response.sendRedirect(request.getContextPath()+"/student/student_main");
				return;
			}else{
				showMessage = "错误信息:用户账号和密码不匹配或账号为激活！";
			}
		}
		//有错误信息，返回到登录界面	
		request.setAttribute("showMessage", showMessage);
		WebLogger.showInfor(showMessage);
		request.getRequestDispatcher("/studentLogin").forward(request, response);
		return;
	}
	
	public Teacher packageVo(String password,String power,String userId){
		Teacher validateVo = new Teacher();
		validateVo.setPassword(password);
		validateVo.setPower(power);
		validateVo.setUserId(userId);
		return validateVo;
	}
}
