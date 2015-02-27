/**
**作者：翁加林
**时间：2012-8-1
**文件名：Admin_name_career_ajax_grades.java
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

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.service.impl.CareerServiceImp;

public class Grade_Department2career extends HttpServlet {

	private static final long serialVersionUID = -1319191335761497462L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String departmentId = request.getParameter("opvalue1").trim();
		String gradeId = request.getParameter("opvalue2").trim();
		PrintWriter out = response.getWriter();
		String responseContext = "<option value='0' checked>----请选择以下专业----</option>";
		if (departmentId == null) {
		} else {
			try {
				int department_id = Integer.parseInt(departmentId);
				try {
					int grade_id = Integer.parseInt(gradeId);
					CareerServiceImp careerServiceImp = new CareerServiceImp();
					List<Career> careers = careerServiceImp.getAllCareersByDepartmentId_GradeId(department_id,grade_id);
					if(careers==null) {//学院号有误！
					}else	if(careers.isEmpty()){//学院下未分配专业！
					}else {
						for(Career career : careers){
							responseContext += "<option value='"+career.getId()+"'>"+career.getCode()+"</option>";
						}
						System.out.println("responseContext:"+responseContext);
					}
				} catch (NumberFormatException e) {
					//e.printStackTrace();
					System.out.println("年级编号错误");
				}
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				System.out.println("学院编号错误");
			}
		}
		out.print(responseContext);
		out.flush();
		out.close();
		return;
	}
}
