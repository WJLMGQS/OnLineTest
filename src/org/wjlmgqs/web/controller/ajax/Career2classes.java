/**
 **作者：翁加林
 **时间：2012-8-3
 **文件名：Admin_name_ajax_career2classes.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest04
 */

package org.wjlmgqs.web.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.service.impl.ClassesServiceImp;

public class Career2classes extends HttpServlet {
	private static final long serialVersionUID = -2706914717916522209L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String careersId = request.getParameter("opvalue").trim();
		System.out.println("careersId:" + careersId);
		PrintWriter out = response.getWriter();
		String responseContext = "<option value='0' checked>----请选择以下班级----</option>";
		if (careersId == null) {
		} else {
			int career_id = Integer.parseInt(careersId);
			ClassesServiceImp classesServiceImp = new ClassesServiceImp();
			List<Classes> classess = classesServiceImp.getAllClassessByCareerId(career_id);
			if (classess == null) {// 年级号有误！
			} else if (classess.isEmpty()) {// 年级下未分配专业！
			} else {
				for (Classes classes : classess) {
					responseContext += "<option value='" + classes.getId()
							+ "'>" + classes.getCode() + "</option>";
				}
				System.out.println("responseContext:" + responseContext);
			}
		}
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}
}
