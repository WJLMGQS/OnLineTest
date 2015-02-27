/**
 **作者：翁加林
 **时间：2012-7-21
 **文件名：AdminServiceImp.java
 **包名：org.wjlmgqs.service.impl
 **工程名：OnLineTest01
 */

package org.wjlmgqs.service.impl;

import org.wjlmgqs.dao.impl.AdminDaoImp;
import org.wjlmgqs.daomain.Teacher;


public class AdminServiceImp{

	public boolean updateAdminInfo(Teacher teacher) {
		AdminDaoImp adminDaoImp = new AdminDaoImp();
		return adminDaoImp.updateAdminInfo(teacher);
	}
	
	public boolean updateTeacherPwd(String fresh,String userId) {
		AdminDaoImp adminDaoImp = new AdminDaoImp();
		return adminDaoImp.updateTeacherPwd(fresh,userId);
	}
	public Teacher teacherLogin(String userId,String password) {
		AdminDaoImp adminDaoImp = new AdminDaoImp();
		return adminDaoImp.teacherLogin(userId,password);
	}
}
