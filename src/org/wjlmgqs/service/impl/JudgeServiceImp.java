/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.JudgeDaoImp;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Teacher;
public class JudgeServiceImp {

	public int getAllJudgesNumberByTeacher(Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getAllJudgesNumberByTeacher(teacher);
	}

	public List<Judge> getAllJudgesByTeacher(Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getAllJudgesByTeacher(teacher);
	}

	public String createJudge(Judge judge) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.createJudge(judge);
	}

	public Judge getJudgeById(int judge_id, Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getJudgeById(judge_id, teacher);
	}

	public boolean updateJudgeImage(Judge judge) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.updateJudgeImage(judge);
	}

	public String updateJudgeInfo(Judge judge) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.updateJudgeInfo(judge);
	}

	/**
	 * @see
	 * @param 
	 */
	public List<Judge> getAllJudgesByKnowledgeId(int knowledge_id,Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getAllJudgesByKnowledgeId(knowledge_id,teacher);
	}

	/**
	 * @see
	 * @param 
	 */
	public List<Judge> getJudgesByIds(String[] ids, Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getJudgesByIds(ids,teacher);
	}

	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds, Teacher teacher) {
		JudgeDaoImp dao = new JudgeDaoImp();
		return dao.getGeneViewsByKids(knowledgeIds,teacher);
	}

}
