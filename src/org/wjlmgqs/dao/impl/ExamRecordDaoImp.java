/**
 **@author wjlmgqs
 **下午11:30:21
 **ExamDaoImp.java
 **org.wjlmgqs.dao.impl
 **OnLineTest
 */
package org.wjlmgqs.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.Career;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.Subject;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.ExamStatus;

/**
 * @see 考试记录Dao操作
 */
public class ExamRecordDaoImp {

	public List<ExamRecord> getAllExamRecord(ExamStatus examStatus) {
		String sql = null;
		if(examStatus==null){
			sql = " select * from t_examsRecords where 1=2";
		}else if(examStatus == ExamStatus.ALL){
			sql = "select id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id from t_examsRecords";
		}else{
			sql = "select id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id from t_examsRecords where status='"+examStatus.getIndex()+"'";
		}
		return getAllExamRecord(sql);
	}
	/**
	 * @see 抽取了任课教师和管理员获取试卷的方法
	 * @param 对应查询状态的考试记录
	 * @see 
	 * 	t_examsRecords：
	 *	   id                   int not null auto_increment,
	 *	   name                 varchar(80),
	 *	   description          varchar(200),
	 *	   createTime           timestamp,
	 *	   subject_id           int,
	 *	   joinedPaper_id       binary(400),
	 *	   startTime            timestamp,
	 *	   stopTime             timestamp,
	 *	   status               int,
	 *	   finishTime           int,
	 *	   totalMark            decimal(4,1),
	 */
	@SuppressWarnings("unchecked")
	private List<ExamRecord> getAllExamRecord(String sql) {
		SQLUtil sqlUtil = new SQLUtil();
		sqlUtil.dealSQLPrint(sql);
		List<ExamRecord> list = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ObjectInputStream oips1 = null;
		ObjectInputStream oips2 = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<ExamRecord>();
			while(rs.next()){
				/***************************************取值************************************************/
				String id = rs.getString("id");
				String name = rs.getString("name");;
				String description = rs.getString("description");
				Timestamp createTime = rs.getTimestamp("createTime");
				int subject_id = rs.getInt("subject_id");
				oips1 = new ObjectInputStream(rs.getBinaryStream("joinedPaper_id"));
				Timestamp startTime = rs.getTimestamp("startTime");
				Timestamp stopTime = rs.getTimestamp("stopTime");
				int status = rs.getInt("status");
				int finishTime = rs.getInt("finishTime");
				BigDecimal totalMark = rs.getBigDecimal("totalMark");
				oips2 = new ObjectInputStream(rs.getBinaryStream("career_id"));
				/****************************************设置***********************************************/
				SubjectDaoImp subjectDaoImp = new SubjectDaoImp();
				Subject subject = subjectDaoImp.getSubjectById(subject_id);
				ExamRecord examRecord = new ExamRecord(name, description, finishTime, totalMark,subject);
				examRecord.setId(id+"");
				examRecord.setCreateTime(new Date(createTime.getTime()));
				examRecord.setStartTime(new Date(startTime.getTime()));
				examRecord.setStopTime(new Date(stopTime.getTime()));
				examRecord.setStatus(ExamStatus.values()[status]);
				List<TestPaper> testPapers = (List<TestPaper>)oips1.readObject();
				examRecord.setTestPapers(testPapers);
				List<Career> careers = (List<Career>)oips2.readObject();
				examRecord.setCareers(careers);
				list.add(examRecord);
			}
		} catch (Exception e) {
			list = null;
			e.printStackTrace();
		} finally {
			try {
				if(oips1!=null){
					oips1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBUtils.closeConnection(conn, stmt, null);
		}
		return list;
	}
	
	public String createExamRecord(ExamRecord examRecord) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_examsRecords(id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = DBUtils.getConnection();
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++,examRecord.getId());
			ps.setString(_account++,examRecord.getName());
			ps.setString(_account++,examRecord.getDescription());
			ps.setTimestamp(_account++,new Timestamp(examRecord.getCreateTime().getTime()));
			ps.setInt(_account++, examRecord.getSubject().getId());
			ps.setObject(_account++,examRecord.getTestPapers());
			ps.setTimestamp(_account++,new Timestamp(examRecord.getStartTime().getTime()));
			ps.setTimestamp(_account++,new Timestamp(examRecord.getStopTime().getTime()));
			ps.setInt(_account++,examRecord.getStatus().ordinal());
			ps.setInt(_account++,examRecord.getFinishTime());
			ps.setBigDecimal(_account++,examRecord.getTotalMark());
			ps.setObject(_account++,examRecord.getCareers());
			rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				showMessage = "提示：试卷成功创建！";
			} else {
				showMessage = "提示：数据异常，试卷创建失败！";
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("回滚事务失败！");
			}
			showMessage = "提示：数据异常,试卷创建失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}
	public ExamRecord loadAdminExamById(String bulidId) {
		String sql = "select id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id from t_examsRecords where id="+bulidId;
		List<ExamRecord> allExamRecord = getAllExamRecord(sql);
		 if(allExamRecord==null || allExamRecord.size()==0){
			 return null;
		 }
		return allExamRecord.get(0);
	}
	public String updateExamRecord(ExamRecord examRecord) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_examsRecords set name=?,description=?,subject_id=?,joinedPaper_id=?,startTime=?,stopTime=?,status=?," +
				"finishTime=?,totalMark=?,career_id=? where id='"+examRecord.getId()+"'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			conn = DBUtils.getConnection();
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++,examRecord.getName());
			ps.setString(_account++,examRecord.getDescription());
			ps.setInt(_account++, examRecord.getSubject().getId());
			ps.setObject(_account++,examRecord.getTestPapers());
			ps.setTimestamp(_account++,new Timestamp(examRecord.getStartTime().getTime()));
			ps.setTimestamp(_account++,new Timestamp(examRecord.getStopTime().getTime()));
			ps.setInt(_account++,examRecord.getStatus().ordinal());
			ps.setInt(_account++,examRecord.getFinishTime());
			ps.setBigDecimal(_account++,examRecord.getTotalMark());
			ps.setObject(_account++,examRecord.getCareers());
			rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				showMessage = "提示：考试信息成功保存！";
			} else {
				showMessage = "提示：考试记录不存在,考试信息更新失败！";
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("回滚事务失败！");
			}
			showMessage = "提示：数据异常,考试信息更新失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}
	public ExamRecord getExamRecordById(String recordId) {
		String sql = "select id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id from t_examsRecords where id="+recordId;
		List<ExamRecord> allExamRecord = getAllExamRecord(sql);
		 if(allExamRecord==null || allExamRecord.size()==0){
			 return null;
		 }
		return allExamRecord.get(0);
	}
	public List<ExamRecord> getAllFinishExamRecordBySubjectId(int subject_id) {
		String sql = "select id,name,description,createTime,subject_id,joinedPaper_id,startTime,stopTime,status,finishTime,totalMark,career_id from t_examsRecords where subject_id="+subject_id+" and status="+ExamStatus.OPENDED.getIndex()+"";
		return getAllExamRecord(sql);
	}
	
}
