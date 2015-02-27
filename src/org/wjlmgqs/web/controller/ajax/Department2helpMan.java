/**
 **作者：翁加林
 **时间：2012-8-2
 **文件名：Admin_name_ajax_department2helpMan.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.HelpMan;
import org.wjlmgqs.service.impl.HelpManServiceImp;

public class Department2helpMan extends HttpServlet {
	private static final long serialVersionUID = 2882322354710856167L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String departmentId = request.getParameter("opvalue").trim();
		System.out.println("departmentId:" + departmentId);
		PrintWriter out = response.getWriter();
		String responseContext = "<option value='0' checked>----请选择以下辅导员----</option>";
		if (departmentId == null) {
		} else {
			int department_id = Integer.parseInt(departmentId);
			HelpManServiceImp helpManServiceImp = new HelpManServiceImp();
			List<HelpMan> helpMans = helpManServiceImp.getAllHelpMansByDepartmentId(department_id);
			if (helpMans == null) {// 学院号有误！
			} else if (helpMans.isEmpty()) {// 学院下未分配年级！
			} else {
				for (HelpMan helpMan : helpMans) {
					responseContext += "<option value='" + helpMan.getId() + "'>"+ helpMan.getCode() + "</option>";
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
