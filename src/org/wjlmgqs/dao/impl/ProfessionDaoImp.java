/**
**作者：翁加林
**时间：2012-7-21
**文件名：AdminDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest01
*/


package org.wjlmgqs.dao.impl;

import java.util.List;

import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.Teacher;


public class ProfessionDaoImp  extends TeacherDaoImp{

	public int getAllProfessionsNumber() {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_teachers where power='1'";
		return sqlUtil.getTableNumber(sql);
	}

	public Teacher getProfessionById(String userId) {
		return super.getTeacherById(userId, 1);
	}

	public String updateProfessionInfoByAdmin(Teacher teacher) {
		return super.updateTeacherInfoByAdmin(teacher);
	}

	public String updateProfessionPwd(String fresh, String userId) {
		return super.updateTeacherPwdByAdmin(fresh, userId, 1);
	}
	
	public List<Teacher> getAllProfessions() {
		return super.getAllTeacher("1");
	}

	public String createProfession(Teacher teacher) {
		return super.createTeacherByAdmin(teacher);
	}

	public String deleteProfessionById(String userId) {
		return super.deleteTeacherById(userId);
	}
	
	public  boolean updateProfessionInfoBySelf(Teacher teacher){
		return super.updateTeacherInfoBySelf(teacher);
	}

}
