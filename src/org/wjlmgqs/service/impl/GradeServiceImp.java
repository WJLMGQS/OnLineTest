/**
 **作者：翁加林
 **时间：2012-8-14
 **文件名：CareerDaoImp.java
 **包名：org.wjlmgqs.dao.impl
 **工程名：OnLineTest12
 */

package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.GradeDaoImp;
import org.wjlmgqs.daomain.Grade;

public class GradeServiceImp {
	public Grade getGradeById(int grade_id) {
		GradeDaoImp dao = new GradeDaoImp();
		return dao.getGradeById(grade_id);
	}

	public List<Grade> getAllGrades() {
		GradeDaoImp dao = new GradeDaoImp();
		return dao.getAllGrades();
	}

	public int getAllGradesNumber() {
		GradeDaoImp dao = new GradeDaoImp();
		return dao.getAllGradesNumber();
	}

	public String createGrade(Grade grade) {
		GradeDaoImp dao = new GradeDaoImp();
		return dao.createGrade(grade);
	}

	public String updateGradeInfo(Grade grade) {
		GradeDaoImp dao = new GradeDaoImp();
		return dao.updateGradeInfo(grade);
	}
}
