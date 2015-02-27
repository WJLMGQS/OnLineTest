/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.CareerDaoImp;
import org.wjlmgqs.daomain.Career;

public class CareerServiceImp {
	
	public Career getCareerById(int career_id) {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.getCareerById(career_id);
	}
	public List<Career> getAllCareers() {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.getAllCareers();
	}
	public int getAllCareersNumber() {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.getAllCareersNumber();
	}
	public String createCareer(Career career) {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.createCareer(career);
	}
	public String updateCareerInfo(Career career) {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.updateCareerInfo(career);
	}

	public List<Career> getAllCareersByDepartmentId_GradeId(int department_id,int grade_id) {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.getAllCareersByDepartmentId_GradeId(department_id,grade_id);
	}
	public List<Career> getCareerByIds(String[] careerIds) {
		CareerDaoImp dao = new CareerDaoImp();
		return dao.getCareerByIds(careerIds);
	}

	
	
	
}
