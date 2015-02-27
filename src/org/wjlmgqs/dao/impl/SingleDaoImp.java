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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.QuestionShowContentUtil;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;

public class SingleDaoImp {
	
	public boolean isExistedSingle(PreparedStatement ps, Single single) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_singles where id='" + single.getId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery(sql);
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
	public Single getSingleById(int single_id, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select t_singles.id,content,SA,SB,SC,SD,result,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_singles where  t_knowledges.id=t_singles.knowledge_id and  t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' and t_singles.id=" + single_id;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Single single = null;
		Knowledge knowledge = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("t_knowledges.id"));
				knowledge.setCode(rs.getString("t_knowledges.code"));
				knowledge.setTeacher(teacher);
				single = new Single();
				single.setAnalyse(rs.getString("analyse"));
				single.setCreateTime(rs.getTimestamp("createTime"));
				single.setContent(rs.getString("content"));
				single.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				single.setId(rs.getInt("t_singles.id"));
				single.setImage(rs.getString("image"));
				single.setKnowledge(knowledge);
				single.setResult(rs.getString("result"));
				single.setSA(rs.getString("SA"));
				single.setSB(rs.getString("SB"));
				single.setSC(rs.getString("SC"));
				single.setSD(rs.getString("SD"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return single;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return single;
	}

	/*
	 * 获取指定教师下的所有单选题，根据教师的编号
	 */
	public List<Single> getAllSinglesByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Single> list = new ArrayList<Single>();
		String sql = "select t_singles.id,content,SA,SB,SC,SD,result,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_singles where  t_knowledges.id=t_singles.knowledge_id and  t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Single single = null;
		Knowledge knowledge = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("t_knowledges.id"));
				knowledge.setCode(rs.getString("t_knowledges.code"));
				knowledge.setTeacher(teacher);
				single = new Single();
				single.setAnalyse(rs.getString("analyse"));
				single.setContent(QuestionShowContentUtil.showShortContent(rs.getString("content")));
				single.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				single.setId(rs.getInt("t_singles.id"));
				single.setImage(rs.getString("image"));
				single.setKnowledge(knowledge);
				single.setResult(rs.getString("result"));
				single.setCreateTime(rs.getTimestamp("createTime"));
				single.setSA(rs.getString("SA"));
				single.setSB(rs.getString("SB"));
				single.setSC(rs.getString("SC"));
				single.setSD(rs.getString("SD"));
				list.add(single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public int getAllSinglesNumberByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_knowledges,t_singles " +
				"where t_knowledges.id=t_singles.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		return sqlUtil.getTableNumber(sql);
	}	/*
	 * id int not null auto_increment, content varchar(800), SA varchar(120), SB
	 * varchar(120), SC varchar(120), SD varchar(120), result char(1) comment
	 * 'A,B,C,D', image varchar(200), difficulty int, knowledge_id int, analyse
	 * varchar(300), createTime timestamp,
	 */
	public String createSingle(Single single) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_singles(content,SA,SB,SC,SD,result,image,difficulty,knowledge_id,analyse,createTime) values(?,?,?,?,?,?,?,?,?,?,?)";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, single.getContent());
			ps.setString(_account++, single.getSA());
			ps.setString(_account++, single.getSB());
			ps.setString(_account++, single.getSC());
			ps.setString(_account++, single.getSD());
			ps.setString(_account++, single.getResult());
			ps.setString(_account++, single.getImage());
			ps.setInt(_account++, single.getDifficulty().ordinal());
			ps.setInt(_account++, single.getKnowledge().getId());
			ps.setString(_account++, single.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(single.getCreateTime().getTime()));
			rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				showMessage = "提示：试题创建成功！";
			} else {
				showMessage = "提示：账号异常,新试题创建失败！";
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
			showMessage = "提示：数据异常,新试题更新失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}
	

	public boolean updateSingleImage(Single single) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean result = false;
		String sql = "update t_singles set image=? where id='" + single.getId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, single.getImage());
			int rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				result = true;
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return result;
	}

	public String updateSingleInfo(Single single) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_singles set content=?,SA=?,SB=?,SC=?,SD=?,result=?,difficulty=?,knowledge_id=?,analyse=?,createTime=? "
				+ " where id='" + single.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, single.getContent());
			ps.setString(_account++, single.getSA());
			ps.setString(_account++, single.getSB());
			ps.setString(_account++, single.getSC());
			ps.setString(_account++, single.getSD());
			ps.setString(_account++, single.getResult());
			ps.setInt(_account++, single.getDifficulty().ordinal());
			ps.setInt(_account++, single.getKnowledge().getId());
			ps.setString(_account++, single.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(single.getCreateTime().getTime()));
			int rs = ps.executeUpdate();
			if (rs == 1) {
				showMessage = "提示：试题信息更新成功！";
				conn.commit();
			} else {
				showMessage = "提示：数据异常，试题信息更新失败！";
				conn.rollback();
			}
			conn.setAutoCommit(isCommited);
		} catch (SQLException e) {
			e.printStackTrace();
			showMessage = "提示：数据异常，试题信息更新失败！";
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

	public List<Single> getAllSinglesByKnowledgeId(int knowledge_id,Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Single> list = null;
		String sql = "select t_singles.id,content,SA,SB,SC,SD,result,image,difficulty,analyse,createTime " +
				"from t_singles,t_knowledges where knowledge_id='"+knowledge_id+"' and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Single single = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Single>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				single = new Single();
				single.setAnalyse(rs.getString("analyse"));
				single.setCreateTime(rs.getTimestamp("createTime"));
				single.setContent(rs.getString("content"));
				single.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				single.setId(rs.getInt("t_singles.id"));
				single.setImage(rs.getString("image"));
				single.setKnowledge(knowledge);
				single.setResult(rs.getString("result"));
				single.setSA(rs.getString("SA"));
				single.setSB(rs.getString("SB"));
				single.setSC(rs.getString("SC"));
				single.setSD(rs.getString("SD"));
				list.add(single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public List<Single> getSinglesByIds(String[] ids, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Single> list = null;
		String sql = "select t_singles.id,content,SA,SB,SC,SD,result,image,difficulty,analyse,createTime " +
				"from t_singles,t_knowledges where "+sqlUtil.dealIds2Or(ids,"t_singles.id")
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Single single = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Single>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				single = new Single();
				single.setAnalyse(rs.getString("analyse"));
				single.setCreateTime(rs.getTimestamp("createTime"));
				single.setContent(rs.getString("content"));
				single.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				single.setId(rs.getInt("t_singles.id"));
				single.setImage(rs.getString("image"));
				single.setKnowledge(knowledge);
				single.setResult(rs.getString("result"));
				single.setSA(rs.getString("SA"));
				single.setSB(rs.getString("SB"));
				single.setSC(rs.getString("SC"));
				single.setSD(rs.getString("SD"));
				list.add(single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<GeneView> list = null;
		String sql = "select t_singles.id,difficulty " +
				"from t_singles,t_knowledges where "+sqlUtil.dealIds2Or(knowledgeIds,"t_knowledges.id")  
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		GeneView geneView = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<GeneView>();
			while (rs.next()) {
				geneView = new GeneView();
				geneView.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				geneView.setId(rs.getInt("t_singles.id"));
				list.add(geneView);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}
}
