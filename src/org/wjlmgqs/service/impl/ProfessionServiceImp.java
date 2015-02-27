/**
**作者：翁加林
**时间：2012-7-21
**文件名：AdminServiceImp.java
**包名：org.wjlmgqs.service.impl
**工程名：OnLineTest01
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.ProfessionDaoImp;
import org.wjlmgqs.daomain.Teacher;


public class ProfessionServiceImp{
	public Teacher teacherLogin(String userId,String password) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.teacherLogin(userId,password);
	}
	
	public Teacher getProfessionById(String userId) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.getProfessionById(userId);
	}

	public int getAllProfessionsNumber() {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.getAllProfessionsNumber();
	}
	

	public String createProfession(Teacher teacher) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.createProfession(teacher);
	}

	public List<Teacher> getAllProfessions() {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.getAllProfessions();
	}

	public String deleteProfessionById(String userId) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.deleteProfessionById(userId);
	}
	
	public String updateProfessionInfoByAdmin(Teacher teacher) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.updateProfessionInfoByAdmin(teacher);
	}

	public String updateProfessionPwd(String fresh, String userId) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.updateProfessionPwd(fresh, userId);
	}
	public boolean updateProfessionInfoBySelf(Teacher teacher){
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.updateProfessionInfoBySelf(teacher);
	}
	
	public boolean updateTeacherPwd(String fresh,String userId) {
		ProfessionDaoImp dao = new ProfessionDaoImp();
		return dao.updateTeacherPwd(fresh,userId);
	}
	
}
