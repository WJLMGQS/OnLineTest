package org.wjlmgqs.service.impl;

import java.util.List;

import org.wjlmgqs.dao.impl.ExamRecordDaoImp;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.enums.ExamStatus;

/**
 * @see 考试记录service操作
 */
public class ExamRecordServiceImp {

	public List<ExamRecord> getAllExamRecord(ExamStatus examStatus) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.getAllExamRecord(examStatus);
	}

	public String createExamRecord(ExamRecord examRecord) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.createExamRecord(examRecord);
	}

	public ExamRecord loadAdminExamById(String bulidId) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.loadAdminExamById(bulidId);
	}

	public String updateExamRecord(ExamRecord examRecord) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.updateExamRecord(examRecord);
	}

	public ExamRecord getExamRecordById(String recordId) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.getExamRecordById(recordId);
	}

	public List<ExamRecord> getAllFinishExamRecordBySubjectId(int id) {
		ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
		return examRecordDaoImp.getAllFinishExamRecordBySubjectId(id);
	}

}
