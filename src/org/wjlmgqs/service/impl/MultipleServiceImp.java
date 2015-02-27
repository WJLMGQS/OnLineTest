/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.MultipleDaoImp;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Teacher;

public class MultipleServiceImp {

	public int getAllMultiplesNumberByTeacher(Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getAllSinglesNumberByTeacher(teacher);
	}

	public List<Multiple> getAllMultiplesByTeacher(Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getAllMultiplesByTeacher(teacher);
	}

	public String createMultiple(Multiple multiple) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.createMultiple(multiple);
	}

	public Multiple getMultipleById(int multiple_id, Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getMultipleById(multiple_id,teacher);
	}

	public boolean updateMultipleImage(Multiple multiple) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.updateMultipleImage(multiple);
	}

	public String updateMultiple(Multiple multiple) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.updateMultiple(multiple);
	}

	/**
	 * @see
	 * @param 
	 */
	public List<Multiple> getAllMultiplesByKnowledgeId(int knowledgeId,Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getAllMultiplesByKnowledgeId(knowledgeId,teacher);
	}

	/**
	 * @see
	 * @param 
	 */
	public List<Multiple> getMultiplesByIds(String[] Ids, Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getMultiplesByIds(Ids,teacher);
	}

	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds,
			Teacher teacher) {
		MultipleDaoImp dao = new MultipleDaoImp();
		return dao.getGeneViewsByKids(knowledgeIds,teacher);
	}




}
