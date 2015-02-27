/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.HelpManDaoImp;
import org.wjlmgqs.daomain.HelpMan;

public class HelpManServiceImp {
	public List<HelpMan> getAllHelpMansByDepartmentId(int department_id) {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.getAllHelpMansByDepartmentId(department_id);
	}
	public HelpMan getHelpManById(int help_id) {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.getHelpManById(help_id);
	}
	 
	public List<HelpMan> getAllHelpMans() {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.getAllHelpMans();
	}

	public int getAllHelpMansNumber() {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.getAllHelpMansNumber();
	}
	public String createHelpMan(HelpMan helpMan) {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.createHelpMan(helpMan);
	}
	public String deleteHelpManById(int help_id) {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.deleteHelpManById(help_id);
	}
	
	public String updateHelpManInfo(HelpMan helpMan) {
		HelpManDaoImp dao = new HelpManDaoImp();
		return dao.updateHelpManInfo(helpMan);
	}

}
