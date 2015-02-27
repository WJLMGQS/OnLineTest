/**
 **作者：翁加林
 **时间：2012-8-6
 **文件名：Admin_account_student_update.java
 **包名：org.wjlmgqs.web.controller
 **工程名：OnLineTest07
 */

package org.wjlmgqs.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.Classes;
import org.wjlmgqs.daomain.Department;
import org.wjlmgqs.daomain.Grade;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.service.impl.CareerServiceImp;
import org.wjlmgqs.service.impl.ClassesServiceImp;
import org.wjlmgqs.service.impl.DepartmentServiceImp;
import org.wjlmgqs.service.impl.GradeServiceImp;
import org.wjlmgqs.service.impl.StudentServiceImp;
import org.wjlmgqs.service.impl.SubjectServiceImp;
import org.wjlmgqs.web.util.BeanValidateUtil;

public class Account_student_update extends HttpServlet {
	private static final long serialVersionUID = -7762271223503885182L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String showMessage = null;
		String userId = request.getParameter("studentId");
		showMessage = BeanValidateUtil.validatorVoProperty(Student.class,"userId", userId);
		// 验证客户端资料
		if (showMessage == null ) {
			// 数据库信息核对成功后删除，并返回删除结果
			StudentServiceImp studentService = new StudentServiceImp();
			Student student = studentService.getStudentById(userId);
			if (student == null) {
				// 出现错误，返回查看所有监考员账号界面
				showMessage = "错误信息:学生编号--" + userId + "--不存在！";
			} else {
				DepartmentServiceImp departmentServiceImp = new DepartmentServiceImp();
				GradeServiceImp gradeServiceImp = new GradeServiceImp();
				ClassesServiceImp classesServiceImp = new ClassesServiceImp();
				CareerServiceImp careerServiceImp = new CareerServiceImp();
				SubjectServiceImp subjectServiceImp = new SubjectServiceImp();
				
				List<Department> departments = departmentServiceImp.getAllDepartments();
				List<Grade> grades = gradeServiceImp.getAllGrades();
				Career c = student.getClasses().getCareer();
//				System.out.println("c:"+c.getDepartment().getId());
//				System.out.println("c:"+c.getGrade().getId());
				List<Career> careers = careerServiceImp.getAllCareersByDepartmentId_GradeId( c.getDepartment().getId(), c.getGrade().getId());
				List<Classes> classess = classesServiceImp.getAllClassessByCareerId(student.getClasses().getCareer().getId());
				List<Subject> subjects = subjectServiceImp.getAllSubjects();
				request.setAttribute("departments", departments);
				request.setAttribute("grades", grades);
				request.setAttribute("careers", careers);
				request.setAttribute("classess", classess);
				request.setAttribute("subjects", subjects);
				request.setAttribute("student", student);
				request.getRequestDispatcher("/teacher/admin/account_student_update.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("showMessage", showMessage);
		request.getRequestDispatcher("/teacher/admin/account_student").forward(request, response);
	}
}
