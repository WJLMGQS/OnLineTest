/**
**作者：翁加林
**时间：2012-7-18
**文件名：CharacterFilter.java
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

public class CharacterFilter implements Filter {
	private FilterConfig config = null;
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//System.out.println("执行了Filter------------------");
		 // 强制类型转换  
		HttpServletRequest req = (HttpServletRequest)request;  
		HttpServletResponse res = (HttpServletResponse)response;
		// 获取web.xm设置的编码集，设置到Request、Response中           
		req.setCharacterEncoding(config.getInitParameter("charset"));  
		res.setContentType(config.getInitParameter("contentType")); 
		res.setCharacterEncoding(config.getInitParameter("charset"));
		// 将请求转发到目的地
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
