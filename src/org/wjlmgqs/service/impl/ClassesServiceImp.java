/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.ClassesDaoImp;
import org.wjlmgqs.daomain.Classes;

public class ClassesServiceImp {
	public List<Classes> getAllClassessByCareerId(int career_id) {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.getAllClassessByCareerId(career_id);
	}
	
	public Classes getClassesById(int class_id) {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.getClassesById(class_id);
	}
	
	public List<Classes> getAllClassess() {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.getAllClassess();
	}
	public int getAllClassessNumber() {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.getAllClassessNumber();
	}
	public String createClasses(Classes classes) {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.createClasses(classes);
	}
	public String updateClassesInfo(Classes classes) {
		ClassesDaoImp dao = new ClassesDaoImp();
		return dao.updateClassesInfo(classes);
	}
}
