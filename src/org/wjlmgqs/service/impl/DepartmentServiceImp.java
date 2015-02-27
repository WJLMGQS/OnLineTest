/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.DepartmentDaoImp;
import org.wjlmgqs.daomain.Department;

public class DepartmentServiceImp {

	public String createDepartment(Department department) {
		DepartmentDaoImp dao = new DepartmentDaoImp();
		return dao.createDepartment(department);
	}
	
	public String updateDepartmentInfo(Department department) {
		DepartmentDaoImp dao = new DepartmentDaoImp();
		return dao.updateDepartmentInfo(department);
	}
	
	public Department getDepartmentById(int subject_id) {
		DepartmentDaoImp dao = new DepartmentDaoImp();
		return dao.getDepartmentById(subject_id);
	}
	public List<Department> getAllDepartments() {
		DepartmentDaoImp dao = new DepartmentDaoImp();
		return dao.getAllDepartments();
	}
	public int getAllDepartmentsNumber() {
		DepartmentDaoImp dao = new DepartmentDaoImp();
		return dao.getAllDepartmentsNumber();
	}
}
