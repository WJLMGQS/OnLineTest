/**
 **@author wjlmgqs
 **下午2:43:00
 **ScopeMarkDaoImp.java
 **org.wjlmgqs.dao.impl
 **OnLineTest
 */
package org.wjlmgqs.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.ExamRecord;
import org.wjlmgqs.daomain.MarkFlag;
import org.wjlmgqs.daomain.ScopeMark;
import org.wjlmgqs.daomain.Student;
import org.wjlmgqs.web.util.WebLogger;

public class ScopeMarkDaoImp {

	public boolean createScopeMark(ScopeMark scopeMark) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean flag = false;
		String sql = "insert into t_student_scopes(student_id,testRecord_id,student_scope,response_id,test_paper_mark,finish_time) values(?,?,?,?,?,?)";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			int index = 1;
			stmt.setString(index++, scopeMark.getStudent().getUserId());
			stmt.setInt(index++,Integer.parseInt(scopeMark.getExamRecord().getId()));
			stmt.setBigDecimal(index++, scopeMark.getStudent_scope());
			stmt.setString(index++,scopeMark.getResponse_id());
			stmt.setBigDecimal(index++, scopeMark.getTest_paper_mark());
			stmt.setTimestamp(index++,new Timestamp(scopeMark.getFinish_time().getTime()));
			rs = stmt.executeUpdate();
			if(rs==1){
				conn.commit();
				flag = true;
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			WebLogger.showInfor("考生成绩创建失败！");
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		return flag;
	}

	public List<ScopeMark> getAllMarkByStudent(Student student) {
		SQLUtil sqlUtil = new SQLUtil();
		List<ScopeMark> list = new ArrayList<ScopeMark>();
		String sql = "select testRecord_id,student_scope,test_paper_mark,finish_time from t_student_scopes where student_id=?";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, student.getUserId());
			rs = stmt.executeQuery();
			ScopeMark scopeMark = null;
			ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
			ExamRecord examRecordById = null;
			while (rs.next()) {
				examRecordById = examRecordDaoImp.getExamRecordById(rs.getInt("testRecord_id")+"");
				scopeMark = new ScopeMark(student,examRecordById, rs.getBigDecimal("student_scope"),  rs.getBigDecimal("test_paper_mark"), null,new Date(rs.getTimestamp("finish_time").getTime()));
				list.add(scopeMark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public List<ScopeMark> loadMarkList(int value) {
		SQLUtil sqlUtil = new SQLUtil();
		List<ScopeMark> list = new ArrayList<ScopeMark>();
		String sql = "select student_id,testRecord_id,student_scope,test_paper_mark,(select avg(student_scope) from t_student_scopes where testRecord_id=?) " +
				"total_avg from t_student_scopes where testRecord_id=?";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, value);
			stmt.setInt(2, value);
			rs = stmt.executeQuery();
			ScopeMark scopeMark = null;
			ExamRecordDaoImp examRecordDaoImp = new ExamRecordDaoImp();
			StudentDaoImp studentDaoImp = new StudentDaoImp();
			ExamRecord examRecord = null;
			Student student = null;
			MarkFlag markFlag = null;
			while (rs.next()) {
				String student_id = rs.getString("student_id");
				int testRecord_id = rs.getInt("testRecord_id");
				BigDecimal student_scope = rs.getBigDecimal("student_scope");
				BigDecimal test_paper_mark = rs.getBigDecimal("test_paper_mark");
				BigDecimal total_avg = rs.getBigDecimal("total_avg");
				student = studentDaoImp.getStudentById(student_id);
				examRecord = examRecordDaoImp.getExamRecordById(testRecord_id+"");
				scopeMark = new ScopeMark(student,examRecord,student_scope,test_paper_mark, null,null);
				markFlag = new MarkFlag(student_scope);
				markFlag.setTotal_avg(total_avg);
				scopeMark.setMarkFlag(markFlag);
				list.add(scopeMark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

}
