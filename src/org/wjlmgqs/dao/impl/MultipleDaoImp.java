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
import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.Knowledge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.enums.QuestionDifficultyType;

public class MultipleDaoImp {

	public int getAllSinglesNumberByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select count(*) as total from t_knowledges,t_multiples " +
				"where t_knowledges.id=t_multiples.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		return sqlUtil.getTableNumber(sql);
	}

	public List<Multiple> getAllMultiplesByTeacher(Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Multiple> list = new ArrayList<Multiple>();
		String sql = "select t_multiples.id,content,choices,results,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
				+ "from t_knowledges,t_multiples where t_knowledges.id=t_multiples.knowledge_id and t_knowledges.teacher_id='"
				+ teacher.getUserId() + "' ";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Multiple multiple = null;
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
				multiple = new Multiple();
				multiple.setAnalyse(rs.getString("analyse"));
				multiple.setCreateTime(rs.getTimestamp("createTime"));
				multiple.setContent(QuestionShowContentUtil.showShortContent(rs.getString("content")));
				multiple.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				multiple.setId(rs.getInt("t_multiples.id"));
				multiple.setImage(rs.getString("image"));
				multiple.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					multiple.setResults((String[])objR);
					oips = new ObjectInputStream(rs.getBinaryStream("choices"));
					Object objC = oips.readObject();//从流中读取对象
					multiple.setChoices((String[])objC);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}finally{
					try {
						oips.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(multiple);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			multiple = null;//读取失败
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public String createMultiple(Multiple multiple) {
		String showMessage = null;
		String sql = "insert into t_multiples(content,choices,results,image,difficulty,knowledge_id,analyse,createTime) values(?,?,?,?,?,?,?,?)";
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
			ps.setString(_account++, multiple.getContent());
			ps.setObject(_account++, multiple.getChoices());
			ps.setObject(_account++, multiple.getResults());
			ps.setString(_account++, multiple.getImage());
			ps.setInt(_account++, multiple.getDifficulty().ordinal());
			ps.setInt(_account++, multiple.getKnowledge().getId());
			ps.setString(_account++, multiple.getAnalyse());
			ps	.setTimestamp(_account++, new Timestamp(multiple.getCreateTime().getTime()));
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

	/**
	 * @see
	 */
	public Multiple getMultipleById(int multiple_id, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select t_multiples.id,content,choices,results,image,difficulty,analyse,createTime,t_knowledges.id,t_knowledges.code,t_knowledges.teacher_id "
			+ "from t_knowledges,t_multiples where t_knowledges.id=t_multiples.knowledge_id and t_knowledges.teacher_id='"
			+ teacher.getUserId() +  "' and t_multiples.id=" + multiple_id;
		sqlUtil.dealSQLPrint(sql);
		conn = DBUtils.getConnection();
		Multiple multiple = null;
		Knowledge knowledge = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				knowledge = new Knowledge();
				knowledge.setId(rs.getInt("t_knowledges.id"));
				knowledge.setCode(rs.getString("t_knowledges.code"));
				knowledge.setTeacher(teacher);
				multiple = new Multiple();
				multiple.setAnalyse(rs.getString("analyse"));
				multiple.setCreateTime(rs.getTimestamp("createTime"));
				multiple.setContent(rs.getString("content"));
				multiple.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				multiple.setId(rs.getInt("t_multiples.id"));
				multiple.setImage(rs.getString("image"));
				multiple.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					multiple.setResults((String[])objR);
					oips = new ObjectInputStream(rs.getBinaryStream("choices"));
					Object objC = oips.readObject();//从流中读取对象
					multiple.setChoices((String[])objC);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}finally{
					try {
						oips.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return multiple;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return multiple;
	}

	public boolean updateMultipleImage(Multiple multiple) {
		SQLUtil sqlUtil = new SQLUtil();
		boolean result = false;
		String sql = "update t_multiples set image=? where id='" + multiple.getId()
				+ "'";
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtils.getConnection();
		try {
			boolean isCommited = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setString(1, multiple.getImage());
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

	public String updateMultiple(Multiple multiple) {
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_multiples set content=?,choices=?,results=?,image=?,difficulty=?,knowledge_id=?,analyse=?,createTime=? where id="+multiple.getId();
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
			ps.setString(_account++, multiple.getContent());
			ps.setObject(_account++, multiple.getChoices());
			ps.setObject(_account++, multiple.getResults());
			ps.setString(_account++, multiple.getImage());
			ps.setInt(_account++, multiple.getDifficulty().ordinal());
			ps.setInt(_account++, multiple.getKnowledge().getId());
			ps.setString(_account++, multiple.getAnalyse());
			ps.setTimestamp(_account++, new Timestamp(multiple.getCreateTime().getTime()));
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

	/**
	 * @see
	 * @param 
	 */
	public List<Multiple> getAllMultiplesByKnowledgeId(int knowledge_id,Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Multiple> list = null;
		String sql = "select t_multiples.id,content,choices,results,image,difficulty,analyse,createTime " +
				"from t_multiples,t_knowledges where knowledge_id='"+knowledge_id+"' and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Multiple multiple = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Multiple>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				multiple = new Multiple();
				multiple.setAnalyse(rs.getString("analyse"));
				multiple.setCreateTime(rs.getTimestamp("createTime"));
				multiple.setContent(rs.getString("content"));
				multiple.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				multiple.setId(rs.getInt("t_multiples.id"));
				multiple.setImage(rs.getString("image"));
				multiple.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					multiple.setResults((String[])objR);
					oips = new ObjectInputStream(rs.getBinaryStream("choices"));
					Object objC = oips.readObject();//从流中读取对象
					multiple.setChoices((String[])objC);
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						oips.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(multiple);
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
	public List<Multiple> getMultiplesByIds(String[] ids, Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<Multiple> list = null;
		String sql = "select t_multiples.id,content,choices,results,image,difficulty,analyse,createTime " +
				"from t_multiples,t_knowledges where "+sqlUtil.dealIds2Or(ids,"t_multiples.id")
				+" and t_knowledges.id=knowledge_id and t_knowledges.teacher_id='"+teacher.getUserId()+"'";
		//sql = sqlUtil.LimitSQLByIndex(0,-1,sql);
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Multiple multiple = null;
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Multiple>();
			Knowledge knowledge = new Knowledge();
			while (rs.next()) {
				multiple = new Multiple();
				multiple.setAnalyse(rs.getString("analyse"));
				multiple.setCreateTime(rs.getTimestamp("createTime"));
				multiple.setContent(rs.getString("content"));
				multiple.setDifficulty(QuestionDifficultyType.values()[rs.getInt("difficulty")]);
				multiple.setId(rs.getInt("t_multiples.id"));
				multiple.setImage(rs.getString("image"));
				multiple.setKnowledge(knowledge);
				ObjectInputStream oips = null;
				try {
					oips = new ObjectInputStream(rs.getBinaryStream("results"));
					Object objR = oips.readObject();//从流中读取对象
					multiple.setResults((String[])objR);
					oips = new ObjectInputStream(rs.getBinaryStream("choices"));
					Object objC = oips.readObject();//从流中读取对象
					multiple.setChoices((String[])objC);
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						oips.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				list.add(multiple);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	public List<GeneView> getGeneViewsByKids(String[] knowledgeIds,
			Teacher teacher) {
		SQLUtil sqlUtil = new SQLUtil();
		List<GeneView> list = null;
		String sql = "select t_multiples.id,difficulty " +
				"from t_multiples,t_knowledges where "+sqlUtil.dealIds2Or(knowledgeIds,"t_knowledges.id")  
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
				geneView.setId(rs.getInt("t_multiples.id"));
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
