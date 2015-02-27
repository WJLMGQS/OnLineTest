/**
 **作者：翁加林
 **时间：2012-7-31
 **文件名：Admin_name_department_update_info.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest03
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Name_department_update_info extends HttpServlet {
	private static final long serialVersionUID = 5687502705693296361L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String departmentId = request.getParameter("departmentId");
		String code = request.getParameter("code");
		int department_id = -1;
		try {
			department_id = Integer.parseInt(departmentId);
		} catch (Exception e) {}
		// 检测格式及正确性
		showMessage = BeanValidateUtil.validatorOnlyVoPropertys(Department.class,new String[]{"id","code"},new Object[]{department_id,code});
		if (showMessage == null) {
			DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
			Department department = departmentServiceImp.getDepartmentById(department_id);
			if(department!=null){
				department.setCode(code);
				showMessage = departmentServiceImp.updateDepartmentInfo(department);
			}else{
				showMessage = "错误信息：指定学院不存在！";
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/name_department_update?departmentId=" + departmentId)
				.forward(request, response);
	}

}
