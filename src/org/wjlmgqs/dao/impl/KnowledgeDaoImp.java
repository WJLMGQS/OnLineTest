/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;

public class KnowledgeDaoImp {
	public String updateKnowledgeInfo(Knowledge knowledge) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_knowledges set code=? where id='"
				+ knowledge.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (isExistedKnowledge_code(ps, knowledge)) {
				showMessage = "提示：创建失败：知识点--" + knowledge.getCode() + "--已经存在！";
			} else {
				ps.setString(1, knowledge.getCode());
				int rs = ps.executeUpdate();
				if (rs == 1) {
					showMessage = "提示：知识点更新成功！";
					conn.commit();
				} else {
					showMessage = "提示：数据异常，知识点更新失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，知识点更新失败！";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}
	
	public String createKnowledge(Knowledge knowledge) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_knowledges(code,teacher_id) values('" + knowledge.getCode() + "','"+knowledge.getTeacher().getUserId()+"')";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (isExistedKnowledge_code(stmt, knowledge)) {
				showMessage = "创建失败：知识点--" + knowledge.getCode() + "--已经存在！";
			} else {
				rs = stmt.executeUpdate(sql);
				if (rs == 1) {
					showMessage = "提示：新知识点创建成功！";
					conn.commit();
				} else {
					showMessage = "提示：新知识点创建失败！";
					conn.rollback();
				}
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常,新知识点创建失败！";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("回滚事务失败！");
			}
		} finally {
			DBUtils.closeConnection(conn, stmt, null);
		}
		return showMessage;
	}
	
	public boolean isExistedKnowledge_code(Statement stmt, Knowledge knowledge) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from  t_knowledges where code='"
			+ knowledge.getCode() + "' and teacher_id='"+knowledge.getTeacher().getUserId()+"'";
		sqlUtil.dealSQLPrint(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			DBUtils.closeConnection(null, null, rs);
		}
		return false;
	}
	
	public Knowledge getknowledgeById(int knowledge_id,Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id,code from  t_knowledges where id='" + knowledge_id
				+ "' and teacher_id="+teacher.getUserId();
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Knowledge konwledge = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				konwledge = new Knowledge();
				konwledge.setCode(rs.getString("code"));
				konwledge.setId(knowledge_id);
				konwledge.setTeacher(teacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return konwledge;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return konwledge;
	}
	
	public int getAllKnowledgesNumberByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select  count(*) as total from t_knowledges where teacher_id='"+teacher.getUserId()+"' ";
		return sqlUtil.getTableNumber(sql);
	}
	/*
	 * 获取指定教师下的所有知识点，根据教师的编号
	 * */
	public List<Knowledge> getAllKnowledgesByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Knowledge> list = new ArrayList<Knowledge>();
		String sql = "select t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id " +
				"from t_knowledges where t_knowledges.teacher_id='"+teacher.getUserId()+"' ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Knowledge knowledge = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("id"));
				knowledge.setCode(rs.getString("code"));
				knowledge.setTeacher(teacher);
				list.add(knowledge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * @see 为避免出现知识点加载教师和教师加载知识点循环，这里指加载教师编号。
	 */
	public List<Knowledge> getAllKnowledges() {
		SQLUtil sqlUtil = new SQLUtil();
		List<Knowledge> list = new ArrayList<Knowledge>();
		String sql = "select t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id from t_knowledges";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Knowledge knowledge = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("id"));
				knowledge.setCode(rs.getString("code"));
				String teacher_id = rs.getString("teacher_id");
				Teacher teacher = new Teacher();
				teacher.setUserId(teacher_id);
				knowledge.setTeacher(teacher);
				list.add(knowledge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	
}
