/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.CareerSubjectSelectedDaoImp;
import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.CareerSubjectSelected;

public class CareerSubjectSelectedServiceImp {

	public CareerSubjectSelected getCareerSubjectSelectedById(int css_id) {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.getCareerSubjectSelectedById(css_id);
	}

	public int getAllCareerSubjectSelectedsNumber() {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.getAllCareerSubjectSelectedsNumber();
	}

	public String createCareerSubjectSelected(	CareerSubjectSelected careerSubjectSelected) {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.createCareerSubjectSelected(careerSubjectSelected);
	}
	public String updateCareerSubjectSelectedInfo(CareerSubjectSelected careerSubjectSelected) {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.updateCareerSubjectSelectedInfo(careerSubjectSelected);
	}

	public List<CareerSubjectSelected> getAllCareerSubjectSelecteds() {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.getAllCareerSubjectSelecteds();
	}

	public List<Career> getAllCareersBySubjectID(int subject_id) {
		CareerSubjectSelectedDaoImp dao = new CareerSubjectSelectedDaoImp();
		return dao.getAllCareersBySubjectID(subject_id);
	}

}
