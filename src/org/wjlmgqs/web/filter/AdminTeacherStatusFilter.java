/**
**作者：翁加林
**时间：2012-7-22
**文件名：TeacherStatusFilter.java
**包名：org.wjlmgqs.web.filter
**工程名：OnLineTest01
*/


package org.wjlmgqs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Teacher;

public class AdminTeacherStatusFilter implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 // 强制类型转换  
		HttpServletRequest req = (HttpServletRequest)request;  
		HttpServletResponse res = (HttpServletResponse)response;
		Teacher t = (Teacher)req.getSession().getAttribute("adminTeacher");
		String showMessage = "您尚未登录！3秒后自动跳转到登录界面<meta http-equiv='refresh' content=3;URL='"+
		req.getContextPath()+"/teacherLogin'><script language='JavaScript'>if (window != top)top.location.href = location.href;</script>";
		if(t==null) {
			request.setAttribute("showMessage", showMessage);
			System.out.println("用户状态丢失showMessage:"+showMessage);
			request.getRequestDispatcher("/showMessage.jsp").forward(request, response);
			return;
		}
		// 将请求转发到目的地
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
