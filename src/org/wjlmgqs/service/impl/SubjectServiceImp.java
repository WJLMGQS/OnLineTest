/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.SubjectDaoImp;
import org.wjlmgqs.daomain.Subject;

public class SubjectServiceImp {

	public String createSubject(Subject subject) {
		SubjectDaoImp dao = new SubjectDaoImp();
		return dao.createSubject(subject);
	}

	public String updateSubjectInfo(Subject subject) {
		SubjectDaoImp dao = new SubjectDaoImp();
		return dao.updateSubjectInfo(subject);
	}

	 

	public Subject getSubjectById(int subject_id) {
		SubjectDaoImp dao = new SubjectDaoImp();
		return dao.getSubjectById(subject_id);
	}	 
	public List<Subject> getAllSubjects() {
		SubjectDaoImp dao = new SubjectDaoImp();
		return dao.getAllSubjects();
	}
	

	public int getAllSubjectsNumber() {
		SubjectDaoImp dao = new SubjectDaoImp();
		return dao.getAllSubjectsNumber();
	}
}
