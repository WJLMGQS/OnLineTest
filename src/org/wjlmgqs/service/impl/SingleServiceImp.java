/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.SingleDaoImp;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;

public class SingleServiceImp {
	public Single getSingleById(int single_id,Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getSingleById(single_id,teacher);
	}
	public List<Single> getAllSinglesByTeacher(Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getAllSinglesByTeacher(teacher);
	}
	public int getAllSinglesNumberByTeacher(Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getAllSinglesNumberByTeacher(teacher);
	}

	public String createSingle(Single single) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.createSingle(single);
	}

	public List<Single> getAllSinglesByKnowledgeId(int knowledge_id,Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getAllSinglesByKnowledgeId(knowledge_id,teacher);
	}
	public boolean updateSingleImage(Single single) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.updateSingleImage(single);
	}
	public String updateSingleInfo(Single single) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.updateSingleInfo(single);
	}
	public List<Single> getSinglesByIds(String[] Ids, Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getSinglesByIds(Ids,teacher);
	}
	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds,Teacher teacher) {
		SingleDaoImp dao = new SingleDaoImp();
		return dao.getGeneViewsByKids(knowledgeIds,teacher);
	}
}
