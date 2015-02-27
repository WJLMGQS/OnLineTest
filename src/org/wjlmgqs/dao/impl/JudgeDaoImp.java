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
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;

public class JudgeDaoImp {

	public int getAllJudgesNumberByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_knowledges,t_judges " +
				"where t_knowledges.id=t_judges.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		return sqlUtil.getTableNumber(sql);
	}

	/*   
	 * id                   int not null auto_increment,
	   content              varchar(800),
	   result               char(1) comment '0：F   1：T',
	   image                varchar(200),
	   difficulty           int,
	   knowledge_id         int,
	   analyse              varchar(800),
	   createTime           timestamp,
	 */
	public List<Judge> getAllJudgesByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Judge> list = new ArrayList<Judge>();
		String sql = "select t_judges.id,content,result,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_judges where  t_knowledges.id=t_judges.knowledge_id and  t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Judge judge = null;
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
				judge = new Judge();
				judge.setAnalyse(rs.getString("analyse"));
				judge.setContent(QuestionShowContentUtil.showShortContent(rs.getString("content")));
				judge.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				judge.setId(rs.getInt("t_judges.id"));
				judge.setImage(rs.getString("image"));
				judge.setKnowledge(knowledge);
				judge.setResult(rs.getString("result"));
				judge.setCreateTime(rs.getTimestamp("createTime"));
				list.add(judge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	
	public String createJudge(Judge judge) {
		String showMessage = null;
		String sql = "insert into t_judges(content,result,image,difficulty,knowledge_id,analyse,createTime) values(?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, judge.getContent());
			ps.setString(_account++, judge.getResult());
			ps.setString(_account++, judge.getImage());
			ps.setInt(_account++, judge.getDifficulty().ordinal());
			ps.setInt(_account++, judge.getKnowledge().getId());
			ps.setString(_account++, judge.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(judge.getCreateTime().getTime()));
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

	public Judge getJudgeById(int judge_id, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select t_judges.id,content,result,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_judges where  t_knowledges.id=t_judges.knowledge_id and  t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' and t_judges.id=" + judge_id;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Judge judge = null;
		Knowledge knowledge = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("t_knowledges.id"));
				knowledge.setCode(rs.getString("t_knowledges.code"));
				knowledge.setTeacher(teacher);
				judge = new Judge();
				judge.setAnalyse(rs.getString("analyse"));
				judge.setCreateTime(rs.getTimestamp("createTime"));
				judge.setContent(rs.getString("content"));
				judge.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);		
				judge.setId(rs.getInt("t_judges.id"));
				judge.setImage(rs.getString("image"));
				judge.setKnowledge(knowledge);
				judge.setResult(rs.getString("result"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return judge;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return judge;
	}

	public String updateJudgeInfo(Judge judge) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_judges set content=?,result=?,difficulty=?,knowledge_id=?,analyse=?,createTime=? "
				+ " where id='" + judge.getId() + "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, judge.getContent());
			ps.setString(_account++, judge.getResult());
			ps.setInt(_account++, judge.getDifficulty().ordinal());
			ps.setInt(_account++, judge.getKnowledge().getId());
			ps.setString(_account++, judge.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(judge.getCreateTime().getTime()));
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
	public boolean updateJudgeImage(Judge judge) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean result = false;
		String sql = "update t_judges set image=? where id='" + judge.getId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, judge.getImage());
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

	/**
	 * @see
	 * @param 
	 */
	public List<Judge> getAllJudgesByKnowledgeId(int knowledge_id,Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Judge> list = null;
		String sql = "select t_judges.id,content,result,image,difficulty,analyse,createTime " +
				"from t_Judges,t_knowledges where knowledge_id='"+knowledge_id+"' and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Judge judge = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Judge>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				judge = new Judge();
				judge.setAnalyse(rs.getString("analyse"));
				judge.setCreateTime(rs.getTimestamp("createTime"));
				judge.setContent(rs.getString("content"));
				judge.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				judge.setId(rs.getInt("t_judges.id"));
				judge.setImage(rs.getString("image"));
				judge.setKnowledge(knowledge);
				judge.setResult(rs.getString("result"));
				list.add(judge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	
	public List<Judge> getJudgesByIds(String[] ids, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Judge> list = null;
		String sql = "select t_judges.id,content,result,image,difficulty,analyse,createTime " +
				"from t_Judges,t_knowledges where "+sqlUtil.dealIds2Or(ids,"t_Judges.id")
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Judge judge = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Judge>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				judge = new Judge();
				judge.setAnalyse(rs.getString("analyse"));
				judge.setCreateTime(rs.getTimestamp("createTime"));
				judge.setContent(rs.getString("content"));
				judge.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				judge.setId(rs.getInt("t_Judges.id"));
				judge.setImage(rs.getString("image"));
				judge.setKnowledge(knowledge);
				judge.setResult(rs.getString("result"));
				list.add(judge);
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
		String sql = "select t_judges.id,difficulty " +
				"from t_Judges,t_knowledges where "+sqlUtil.dealIds2Or(knowledgeIds,"t_knowledges.id")  
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
				geneView.setId(rs.getInt("t_Judges.id"));
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
