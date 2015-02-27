/**
 **作者：翁加林
 **时间：2012-7-21
 **文件名：AdminDaoImp.java
 **包名：org.wjlmgqs.dao.impl
 **工程名：OnLineTest01
 */

package org.wjlmgqs.dao.impl;

import org.wjlmgqs.daomain.Teacher;

public class AdminDaoImp extends TeacherDaoImp {

	public boolean updateAdminInfo(Teacher teacher) {
		return super.updateTeacherInfoBySelf(teacher);
	}


}
