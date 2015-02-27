/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.FillBlankDaoImp;
import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Teacher;

public class FillBlankServiceImp {

	public FillBlank getFillBlankById(int fillBlank_id, Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getFillBlankById(fillBlank_id,teacher);
	}

	public String createFillBlank(FillBlank fillBlank) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.createFillBlank(fillBlank);
	}

	public String updateFillBlank(FillBlank fillBlank) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.updateFillBlank(fillBlank);
	}

	public boolean updateFillBlankImage(FillBlank fillBlank) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.updateFillBlankImage(fillBlank);
	}

	public List<FillBlank> getAllFillBlanksByTeacher(Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getAllFillBlanksByTeacher(teacher);
	}

	public int getAllFillBlanksNumberByTeacher(Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getAllFillBlanksNumberByTeacher(teacher);
	}

	public List<FillBlank> getFillBlanksByIds(String[] ids, Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getFillBlanksByIds(ids,teacher);
	}

	public List<FillBlank> getAllFillBlanksByKnowledgeId(int knowledgeIds,Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getAllFillBlanksByKnowledgeId(knowledgeIds,teacher);
	}

	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds,Teacher teacher) {
		FillBlankDaoImp dao = new FillBlankDaoImp();
		return dao.getGeneViewsByKids(knowledgeIds,teacher);
	}


}
