/**
**作者：翁加林
**时间：2012-8-14
**文件名：CareerDaoImp.java
**包名：org.wjlmgqs.dao.impl
**工程名：OnLineTest12
*/


package org.wjlmgqs.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
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
import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;

public class FillBlankDaoImp{

	public FillBlank getFillBlankById(int fillBlank_id, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select t_fillBlanks.id,content,results,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
			+ "from t_knowledges,t_fillBlanks where t_knowledges.id=t_fillBlanks.knowledge_id and t_knowledges.teacher_id='"
			+ teacher.getUserId() +  "' and t_fillBlanks.id=" + fillBlank_id;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		FillBlank fillBlank = null;
		Knowledge knowledge = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("t_knowledges.id"));
				knowledge.setCode(rs.getString("t_knowledges.code"));
				knowledge.setTeacher(teacher);
				fillBlank = new FillBlank();
				fillBlank.setAnalyse(rs.getString("analyse"));
				fillBlank.setCreateTime(rs.getTimestamp("createTime"));
				fillBlank.setContent(rs.getString("content"));
				fillBlank.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);		
				fillBlank.setId(rs.getInt("t_fillBlanks.id"));
				fillBlank.setImage(rs.getString("image"));
				fillBlank.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					fillBlank.setResults((String[])objR);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(oips!=null){
							oips.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return fillBlank;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return fillBlank;
	}

	public String createFillBlank(FillBlank fillBlank) {
		String showMessage = null;
		String sql = "insert into t_fillBlanks(content,results,image,difficulty,knowledge_id,analyse,createTime) values(?,?,?,?,?,?,?)";
		// sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = 0;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			int _account = 1;
			ps.setString(_account++, fillBlank.getContent());
			ps.setObject(_account++, fillBlank.getResults());
			ps.setString(_account++, fillBlank.getImage());
			ps.setInt(_account++, fillBlank.getDifficulty().ordinal());
			ps.setInt(_account++, fillBlank.getKnowledge().getId());
			ps.setString(_account++, fillBlank.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(fillBlank.getCreateTime().getTime()));
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
			showMessage = "提示：数据异常,新试题创建失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}

	public String updateFillBlank(FillBlank fillBlank) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_fillBlanks set content=?,results=?,image=?,difficulty=?,knowledge_id=?,analyse=?,createTime=? where id="+fillBlank.getId();
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
			ps.setString(_account++, fillBlank.getContent());
			ps.setObject(_account++, fillBlank.getResults());
			ps.setString(_account++, fillBlank.getImage());
			ps.setInt(_account++, fillBlank.getDifficulty().ordinal());
			ps.setInt(_account++, fillBlank.getKnowledge().getId());
			ps.setString(_account++, fillBlank.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(fillBlank.getCreateTime().getTime()));
			rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				showMessage = "提示：试题更新成功！";
			} else {
				showMessage = "提示：账号异常,新试题更新失败！";
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

	public boolean updateFillBlankImage(FillBlank fillBlank) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean result = false;
		String sql = "update t_fillBlanks set image=? where id='" + fillBlank.getId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, fillBlank.getImage());
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

	public List<FillBlank> getAllFillBlanksByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<FillBlank> list = new ArrayList<FillBlank>();
		String sql = "select t_fillBlanks.id,content,results,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_fillBlanks where t_knowledges.id=t_fillBlanks.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		FillBlank fillBlank = null;
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
				fillBlank = new FillBlank();
				fillBlank.setAnalyse(rs.getString("analyse"));
				fillBlank.setCreateTime(rs.getTimestamp("createTime"));
				fillBlank.setContent(QuestionShowContentUtil.showShortContent(rs.getString("content")));
				fillBlank.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);		
				fillBlank.setId(rs.getInt("t_fillBlanks.id"));
				fillBlank.setImage(rs.getString("image"));
				fillBlank.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					fillBlank.setResults((String[])objR);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(oips!=null){
							oips.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(fillBlank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			fillBlank = null;//读取失败
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public int getAllFillBlanksNumberByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_knowledges,t_fillBlanks " +
				"where t_knowledges.id=t_fillBlanks.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		return sqlUtil.getTableNumber(sql);
	}

	/**
	 * @see
	 * @param 
	 */
	public List<FillBlank> getFillBlanksByIds(String[] ids, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<FillBlank> list = null;
		String sql = "select t_fillBlanks.id,content,results,image,difficulty,analyse,createTime " +
				"from t_fillBlanks,t_knowledges where "+sqlUtil.dealIds2Or(ids,"t_fillBlanks.id")
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		FillBlank fillBlank = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<FillBlank>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				fillBlank = new FillBlank();
				fillBlank.setAnalyse(rs.getString("analyse"));
				fillBlank.setCreateTime(rs.getTimestamp("createTime"));
				fillBlank.setContent(rs.getString("content"));
				fillBlank.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);		
				fillBlank.setId(rs.getInt("t_fillBlanks.id"));
				fillBlank.setImage(rs.getString("image"));
				fillBlank.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					fillBlank.setResults((String[])objR);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(oips!=null){
							oips.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(fillBlank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * @see
	 * @param 
	 */
	public List<FillBlank> getAllFillBlanksByKnowledgeId(int knowledge_id,Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<FillBlank> list = null;
		String sql = "select t_fillBlanks.id,content,results,image,difficulty,analyse,createTime " +
				"from t_fillBlanks,t_knowledges where knowledge_id='"+knowledge_id
				+"' and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		FillBlank fillBlank = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<FillBlank>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				fillBlank = new FillBlank();
				fillBlank.setAnalyse(rs.getString("analyse"));
				fillBlank.setCreateTime(rs.getTimestamp("createTime"));
				fillBlank.setContent(QuestionShowContentUtil.showShortContent(rs.getString("content")));
				fillBlank.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);		
				fillBlank.setId(rs.getInt("t_fillBlanks.id"));
				fillBlank.setImage(rs.getString("image"));
				fillBlank.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					fillBlank.setResults((String[])objR);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(oips!=null){
							oips.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(fillBlank);
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
		String sql = "select t_fillBlanks.id,results,difficulty " +
				"from t_fillBlanks,t_knowledges where "+sqlUtil.dealIds2Or(knowledgeIds,"t_knowledges.id")  
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		GeneView geneView = null;
		conn = DBUtils.getConnection();
		ObjectInputStream oips = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<GeneView>();
			
			while (rs.next()) {
				geneView = new GeneView();
				geneView.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				geneView.setId(rs.getInt("t_fillBlanks.id"));
				oips = new ObjectInputStream(rs.getBinaryStream("results"));
				String[] objR = (String[])oips.readObject();//从流中读取对象
				geneView.setLength(objR.length);
				list.add(geneView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(oips!=null){
					oips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

}
