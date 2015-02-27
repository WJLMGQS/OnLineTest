package org.wjlmgqs.web.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.service.impl.SingleServiceImp;

public class Question2imageUrl extends HttpServlet {

	/**
	 * @see
	 */
	private static final long serialVersionUID = 827116439074605896L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String singleId = request.getParameter("opvalue").trim();
		PrintWriter out = response.getWriter();
		
		ServletContext servletContext = request.getSession().getServletContext();
		String saveFolder = servletContext.getInitParameter("questionBankFolder");
		String contextPath = servletContext.getContextPath();
		
		String responseContext = "images/loadError.jpg" + new Date().getTime();
		if (singleId == null) {
		} else {
			try {
				int single_id = Integer.parseInt(singleId);
				SingleServiceImp singleServiceImp = new SingleServiceImp();
				//返回所有记录
				Single single = singleServiceImp.getSingleById(single_id,(Teacher)request.getSession().getAttribute("professionTeacher"));
				if (single == null) {// 试题不存在！
				}else {
					responseContext = saveFolder + "/" +single.getImage();
				}
			} catch (NumberFormatException e) {
				System.out.println("发现错误！");
			}
		}
		responseContext = contextPath + "/" +responseContext;
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}
}
