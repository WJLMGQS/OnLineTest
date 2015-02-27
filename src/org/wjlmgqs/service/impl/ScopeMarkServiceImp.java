package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.ScopeMarkDaoImp;
import org.wjlmgqs.daomain.ScopeMark;
import org.wjlmgqs.daomain.Student;

public class ScopeMarkServiceImp {

	public void createScopeMark(ScopeMark scopeMark) {
		ScopeMarkDaoImp scopeMarkDaoImp = new ScopeMarkDaoImp();
		scopeMarkDaoImp.createScopeMark(scopeMark);
	}

	public List<ScopeMark> getAllMarkByStudent(Student student) {
		ScopeMarkDaoImp scopeMarkDaoImp = new ScopeMarkDaoImp();
		return scopeMarkDaoImp.getAllMarkByStudent(student);
	}

	public List<ScopeMark> loadMarkList(int value) {
		ScopeMarkDaoImp scopeMarkDaoImp = new ScopeMarkDaoImp();
		return scopeMarkDaoImp.loadMarkList(value);
	}

}
