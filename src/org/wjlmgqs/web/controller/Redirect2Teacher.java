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

import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.AdminServiceImp;
import org.wjlmgqs.service.impl.ProfessionServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;
import org.wjlmgqs.web.util.MD5Code;

public class Redirect2Teacher extends HttpServlet {
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
		String power = request.getParameter("power");
		//检测格式，返回校验结果
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(
				Teacher.class, 
				new String[]{"userId","power","password"}, 
				new Object[]{userId,power,password});
		//输入格式正确，开始检查账户信息是否正确
		if(showMessage==null) {	
			Teacher teacher = null;
			HttpSession session = request.getSession();
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);//数据库中存储加密后的密文
			if(power.equals("0")){//管理员登陆
				AdminServiceImp adminServiceImp = new AdminServiceImp();
				teacher = adminServiceImp.teacherLogin(userId,password);
				if(teacher!=null){
					if(teacher.getPower().equals("0")){
						session.setAttribute("adminTeacher", teacher);
						response.sendRedirect(request.getContextPath()+"/teacher/admin_main");
						return;
					}else{//权限不够
						showMessage = "错误信息:用户权限不足！";
					}
				}else{
					showMessage = "错误信息:用户账号和密码不匹配或账号为激活！";
				}
			}else{//任课教师登陆
				ProfessionServiceImp professionServiceImp = new ProfessionServiceImp();
				teacher = professionServiceImp.teacherLogin(userId,password);
				if(teacher!=null){
					if(teacher.getPower().equals("1")){
						session.setAttribute("professionTeacher", teacher);
						response.sendRedirect(request.getContextPath()+"/teacher/profession_main");
						return;
					}else{
						showMessage ="尊敬的管理员,您好！您需要手动创建自己的教师账号才能登陆！";
					}
				}else{
					showMessage = "错误信息:用户账号和密码不匹配或账号为激活！";
				}
			}
		}
		//有错误信息，返回到登录界面	
		request.setAttribute("showMessage", showMessage);
		System.out.println("执行showMessage:"+showMessage);
		request.getRequestDispatcher("/teacherLogin").forward(request, response);
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
