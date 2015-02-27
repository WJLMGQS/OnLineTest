/**
 **@author wjlmgqs
 **下午11:37:53
 **Subject2career.java
 **org.wjlmgqs.web.controller.ajax
 **OnLineTest
 */
package org.wjlmgqs.web.controller.ajax;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.service.impl.CareerSubjectSelectedServiceImp;

/**
 */
public class Subject2career extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String subjectId = request.getParameter("opvalue").trim();
		PrintWriter out = response.getWriter();
		String responseContext = "";
		if (subjectId == null) {
		} else {
			int subject_id = Integer.parseInt(subjectId);
			CareerSubjectSelectedServiceImp careerSubjectSelectedServiceImp = new CareerSubjectSelectedServiceImp();
			List<Career> careers = careerSubjectSelectedServiceImp.getAllCareersBySubjectID(subject_id);
			if (careers == null) {// 年级号有误！
			} else if (careers.isEmpty()) {// 年级下未分配专业！
			} else {
				for (Career career : careers) {
					responseContext += "<option value='" + career.getId()
							+ "'>" + career.getCode() + "</option>";
				}
				System.out.println("responseContext:" + responseContext);
			}
		}
		if(responseContext.trim().length()==0){
			responseContext = "<option value='0' checked>----请选择以下专业----</option>";
		}
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}	
}
