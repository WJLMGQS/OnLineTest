/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.KnowledgeDaoImp;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;

public class KnowledgeServiceImp {
	public String createKnowledge(Knowledge knowledge) {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.createKnowledge(knowledge);
	}
	public String updateKnowledgeInfo(Knowledge knowledge) {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.updateKnowledgeInfo(knowledge);
	}
	public Knowledge getknowledgeById(int konwledge_id,Teacher teacher) {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.getknowledgeById(konwledge_id,teacher);
	}
	public List<Knowledge> getAllKnowledgesByTeacher(Teacher teacher) {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.getAllKnowledgesByTeacher(teacher);
	}
	public int getAllKnowledgesNumberByTeacher(Teacher teacher) {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.getAllKnowledgesNumberByTeacher(teacher);
	}
	public List<Knowledge> getAllKnowledges() {
		KnowledgeDaoImp dao = new KnowledgeDaoImp();
		return dao.getAllKnowledges();
	}
	
}
