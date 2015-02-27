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
import java.util.Random;

import org.wjlmgqs.dao.util.DBUtils;
import org.wjlmgqs.dao.util.SQLUtil;
import org.wjlmgqs.daomain.FillBlank;
import org.wjlmgqs.daomain.Judge;
import org.wjlmgqs.daomain.Multiple;
import org.wjlmgqs.daomain.Single;
import org.wjlmgqs.daomain.Teacher;
import org.wjlmgqs.daomain.TestPaper;
import org.wjlmgqs.enums.QuestionDifficultyType;
import org.wjlmgqs.enums.TestPaperBulidType;
import org.wjlmgqs.enums.TestPaperStatusTrack;
import org.wjlmgqs.enums.TestPaperUseType;
import org.wjlmgqs.web.util.WebLogger;

/*所有字段：
	teacher_id,id,name,             
	finishTime,studentScope,      
	mark,createTime,       
	singles,multiples,fillblanks,          
	judges, singleMark,multipleMark,fillBlankMark,judgeMark,         
	statusTrack,bulidType,useType 
*/   
public class TestPaperDaoImp { 

	public boolean isExistedTestPaper(String paperId) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select * from t_papers where id='" + paperId + "'";
		sqlUtil.dealSQLPrint(sql);
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			DBUtils.closeConnection(conn, stmt, rs);
		}
		return false;
	}
	
	public String createTestPaper(TestPaper testPaper){
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "insert into t_papers(teacher_id,id,name,finishTime,createTime,updateTime,singles,multiples,fillblanks,judges," +
				"singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			ps.setString(_account++,testPaper.getTeacher().getUserId());
			ps.setString(_account++,testPaper.getId());
			ps.setString(_account++,testPaper.getName());
			ps.setInt(_account++,testPaper.getFinishTime());
			ps.setTimestamp(_account++,new Timestamp(testPaper.getCreateTime().getTime()));
			ps.setTimestamp(_account++,new Timestamp(testPaper.getUpdateTime().getTime()));
			ps.setObject(_account++,testPaper.getSingles());
			ps.setObject(_account++,testPaper.getMultiples());
			ps.setObject(_account++,testPaper.getFillBlanks());
			ps.setObject(_account++,testPaper.getJudges());
			ps.setBigDecimal(_account++,testPaper.getSingleMark());
			ps.setBigDecimal(_account++,testPaper.getMultipleMark());
			ps.setBigDecimal(_account++,testPaper.getFillBlankMark());
			ps.setBigDecimal(_account++,testPaper.getJudgeMark());
			ps.setInt(_account++,testPaper.getStatus().ordinal());
			ps.setInt(_account++,testPaper.getBulidType().ordinal());
			ps.setInt(_account++,testPaper.getUseType().ordinal());
			ps.setInt(_account++,testPaper.getDifficulty().ordinal());
			ps.setBigDecimal(_account++,testPaper.getTotalMark());
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
	
	public String updateTestPaper(TestPaper testPaper){
		SQLUtil sqlUtil = new SQLUtil();
		String showMessage = null;
		String sql = "update t_papers set name=?,finishTime=?,updateTime=?,singles=?,multiples=?,fillblanks=?,judges=?," +
				"singleMark=?,multipleMark=?,fillBlankMark=?,judgeMark=?,statusTrack=?,useType=?,difficulty=?,totalMark=? where id='"+testPaper.getId()+"'";
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
			ps.setString(_account++,testPaper.getName());
			ps.setInt(_account++,testPaper.getFinishTime());
			ps.setTimestamp(_account++,new Timestamp(testPaper.getUpdateTime().getTime()));
			ps.setObject(_account++,testPaper.getSingles());
			ps.setObject(_account++,testPaper.getMultiples());
			ps.setObject(_account++,testPaper.getFillBlanks());
			ps.setObject(_account++,testPaper.getJudges());
			ps.setBigDecimal(_account++,testPaper.getSingleMark());
			ps.setBigDecimal(_account++,testPaper.getMultipleMark());
			ps.setBigDecimal(_account++,testPaper.getFillBlankMark());
			ps.setBigDecimal(_account++,testPaper.getJudgeMark());
			ps.setInt(_account++,testPaper.getStatus().ordinal());
			ps.setInt(_account++,testPaper.getUseType().ordinal());
			ps.setInt(_account++,testPaper.getDifficulty().ordinal());
			ps.setBigDecimal(_account++,testPaper.getTotalMark());
			rs = ps.executeUpdate();
			if (rs == 1) {
				conn.commit();
				showMessage = "提示：试卷信息成功保存！";
			} else {
				showMessage = "提示：试卷不存在,试卷信息更新失败！";
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
			showMessage = "提示：数据异常,试卷信息更新失败！";
		} finally {
			DBUtils.closeConnection(conn, ps, null);
		}
		return showMessage;
	}
	
	public String createOrUpdateTestPaper(TestPaper testPaper) {		
		boolean results = isExistedTestPaper(testPaper.getId());
		if(!results){
			return createTestPaper(testPaper);
		}else{
			return updateTestPaper(testPaper);
		}
	}

	/**
	 * @see 返回当前使用的管理员管理的所有试卷
	 * @param 
	 */
	public List<TestPaper> getAllTestPaperByAdminId(Teacher teacher,TestPaperStatusTrack paperStatusTrack) {
		String sql = null;
		if(paperStatusTrack==null){
			sql = " select * from t_papers where 1=2";
		}else if(paperStatusTrack==TestPaperStatusTrack.ALL){
			sql = "select id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark from t_papers where teacher_id='";
		}else{
			sql = "select id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark from t_papers where statusTrack='"+paperStatusTrack.getIndex()+"' and teacher_id='";
		}
		TeacherDaoImp teacherDaoImp = new TeacherDaoImp();
		List<Teacher> teachers = teacherDaoImp.getAllProfessionIds();
		List<TestPaper> testPapers = new ArrayList<TestPaper>();
		for(Teacher t : teachers){
			testPapers.addAll(getAllTestPaperByTeacher(t,sql+t.getUserId()+"'"));
		}
		return testPapers ;
	}

	/**
	 * @see 抽取了任课教师和管理员获取试卷的方法
	 * @return list 
	 * 		null 异常时
	 * 		size==0 没有数据
	 * 		其他正常
	 */
	@SuppressWarnings("unchecked")
	private List<TestPaper> getAllTestPaperByTeacher(Teacher teacher, String sql) {
		SQLUtil sqlUtil = new SQLUtil();
		sqlUtil.dealSQLPrint(sql);
		List<TestPaper> list = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ObjectInputStream oips = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<TestPaper>();
			while(rs.next()){
				/***************************************取值************************************************/
				int useType = rs.getInt("useType");
				int bulidType = rs.getInt("bulidType");
				int difficulty = rs.getInt("difficulty");
				int statusTrack = rs.getInt("statusTrack");
				String name = rs.getString("name");;
				int finishTime = rs.getInt("finishTime");
				Timestamp updateTime = rs.getTimestamp("updateTime");
				Timestamp createTime = rs.getTimestamp("updateTime");
				
				oips = new ObjectInputStream(rs.getBinaryStream("singles"));
				List<Single> singles = (List<Single>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("multiples"));
				List<Multiple> multiples = (List<Multiple>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("fillblanks"));
				List<FillBlank> fillBlanks = (List<FillBlank>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("judges"));
				List<Judge> judges = (List<Judge>) oips.readObject();//从流中读取对象
				
				BigDecimal singleMark = rs.getBigDecimal("singleMark");
				BigDecimal multipleMark = rs.getBigDecimal("multipleMark");
				BigDecimal fillBlankMark = rs.getBigDecimal("fillBlankMark");
				BigDecimal judgeMark = rs.getBigDecimal("judgeMark");
				BigDecimal totalMark = rs.getBigDecimal("totalMark");
				String id = rs.getString("id");
				/****************************************设置***********************************************/
				TestPaper testPaper = new TestPaper(teacher, TestPaperBulidType.values()[bulidType],QuestionDifficultyType.values()[difficulty]);
				testPaper.setStatus(TestPaperStatusTrack.values()[statusTrack]);
				testPaper.setUseType(TestPaperUseType.values()[useType]);
				testPaper.setName(name);
				testPaper.setFinishTime(finishTime);
				testPaper.setCreateTime(new Date(createTime.getTime()));
				testPaper.setUpdateTime(new Date(updateTime.getTime()));
				testPaper.setSingles(singles);
				testPaper.setMultiples(multiples);
				testPaper.setFillBlanks(fillBlanks);
				testPaper.setJudges(judges);
				testPaper.setSingleMark(singleMark);
				testPaper.setMultipleMark(multipleMark);
				testPaper.setJudgeMark(judgeMark);
				testPaper.setFillBlankMark(fillBlankMark);
				testPaper.setTotalMark(totalMark);
				testPaper.setTeacher(teacher);
				testPaper.setId(id);
				testPaper.getTotalMark();//统计虚拟总分
				list.add(testPaper);
			}
		} catch (Exception e) {
			list = null;
			e.printStackTrace();
		} finally {
			try {
				if(oips!=null){
					oips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBUtils.closeConnection(conn, stmt, null);
		}
		return list;
	}
	
	/**
	 * @see 返回当前使用的任课教师管理的所有试卷
	 * @param 
	 */
	public List<TestPaper> getAllTestPaperByProfessionId(Teacher teacher,TestPaperStatusTrack paperStatusTrack) {
		String sql = null;
		if(paperStatusTrack==null){
			sql = " select * from t_papers where 1=2";
		}else if(paperStatusTrack==TestPaperStatusTrack.ALL){
			sql = "select  id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark  from t_papers where teacher_id='"+teacher.getUserId()+"'";
		}else{
			sql = "select  id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark  from t_papers where teacher_id='"+teacher.getUserId()+"' and statusTrack='"+paperStatusTrack.getIndex()+"'";
		}
		return getAllTestPaperByTeacher(teacher, sql);
	}

	/**
	 * @see 根据试卷的ID指定加载试卷
	 */
	public TestPaper loadProfessionTestPaperById(Teacher teacher, String bulidId) {
		String sql = "select id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark  from t_papers where teacher_id='"+teacher.getUserId()+"' and id="+bulidId;
		List<TestPaper> allTestPaperByTeacher = getAllTestPaperByTeacher(teacher, sql);
		 if(allTestPaperByTeacher==null || allTestPaperByTeacher.size()==0){
			 return null;
		 }
		return allTestPaperByTeacher.get(0);
	}
	/**
	 * @see 根据试卷的ID指定加载试卷
	 */
	public TestPaper loadAdminTestPaperById(Teacher teacher, String bulidId) {
		String sql = "select id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark  from t_papers where id="+bulidId;
		List<TestPaper> allTestPaperByTeacher = getAllTestPaperByTeacher(teacher, sql);
		 if(allTestPaperByTeacher==null || allTestPaperByTeacher.size()==0){
			 return null;
		 }
		return allTestPaperByTeacher.get(0);
	}

	public List<TestPaper> getTestPapersBySubjectId_UserType_Teacher(int subject_id,int finishtime,int totalmark) {
		List<TestPaper> testPapers = new ArrayList<TestPaper>();
		TeacherDaoImp teacherDaoImp = new TeacherDaoImp();
		List<Teacher> teachers = teacherDaoImp.getTeacherIdsBySubjectId(subject_id);
		for(Teacher t : teachers){
			String sql = "select id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges,singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark " +
					"from t_papers where statusTrack="+TestPaperStatusTrack.PUBLISHED.getIndex()+" and useType in (0,2) and teacher_id='"+t.getUserId()+"' and finishTime='"+finishtime+"' and totalMark='"+totalmark+"'";
			testPapers.addAll(getAllTestPaperByTeacher(t, sql));
		}
		return testPapers;
	}

	/**
	 * @see 根据试卷Id去查找对应的试卷，如果试卷存在就返回试卷的代理对象，只包含试卷Id
	 */
	public List<TestPaper> getAdminTestPaperByIds(Teacher teacher,String[] options) {
		SQLUtil sqlUtil = new SQLUtil();
		String dealIds2Or = sqlUtil.dealIds2Or(options, "id");
		String sql = "select id from t_papers where "+dealIds2Or;
		sqlUtil.dealSQLPrint(sql);
		List<TestPaper> testPapers = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ObjectInputStream oips = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			testPapers = new ArrayList<TestPaper>();
			TestPaper testPaper = null;
			while(rs.next()){
				String id = rs.getString("id");
				testPaper = new TestPaper();
				testPaper.setId(id);
				testPapers.add(testPaper);
			}
		} catch (Exception e) {
			WebLogger.showInfor(e.getMessage());
			return testPapers = null;
		} finally {
			try {
				if(oips!=null){
					oips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBUtils.closeConnection(conn, stmt, null);
		}
		if(testPapers.size()==0){
			return null;
		}
		return testPapers;
	}

	public TestPaper randomLoadTestPaperByIds(List<TestPaper> testPapers) {
		int size = testPapers.size();
		Random random = new Random();
		int nextInt = random.nextInt(size);
		String paperId = testPapers.get(nextInt).getId();
		return loadTestPaperById(paperId);
	}
	@SuppressWarnings("unchecked")
	public TestPaper loadTestPaperById(String paperId) {
		SQLUtil sqlUtil = new SQLUtil();
		String sql = "select id,teacher_id,name,finishTime,updateTime,createTime,singles,multiples,fillblanks,judges," +
				"singleMark,multipleMark,fillBlankMark,judgeMark,statusTrack,bulidType,useType,difficulty,totalMark  from t_papers where id="+paperId;
		sqlUtil.dealSQLPrint(sql);
		TestPaper testPaper = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ObjectInputStream oips = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				/***************************************取值************************************************/
				int useType = rs.getInt("useType");
				int bulidType = rs.getInt("bulidType");
				int difficulty = rs.getInt("difficulty");
				int statusTrack = rs.getInt("statusTrack");
				String name = rs.getString("name");;
				String teacher_id = rs.getString("teacher_id");;
				int finishTime = rs.getInt("finishTime");
				Timestamp updateTime = rs.getTimestamp("updateTime");
				Timestamp createTime = rs.getTimestamp("updateTime");
				
				oips = new ObjectInputStream(rs.getBinaryStream("singles"));
				List<Single> singles = (List<Single>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("multiples"));
				List<Multiple> multiples = (List<Multiple>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("fillblanks"));
				List<FillBlank> fillBlanks = (List<FillBlank>) oips.readObject();//从流中读取对象
				oips = new ObjectInputStream(rs.getBinaryStream("judges"));
				List<Judge> judges = (List<Judge>) oips.readObject();//从流中读取对象
				
				BigDecimal singleMark = rs.getBigDecimal("singleMark");
				BigDecimal multipleMark = rs.getBigDecimal("multipleMark");
				BigDecimal fillBlankMark = rs.getBigDecimal("fillBlankMark");
				BigDecimal judgeMark = rs.getBigDecimal("judgeMark");
				BigDecimal totalMark = rs.getBigDecimal("totalMark");
				String id = rs.getString("id");
				/****************************************设置***********************************************/
				TeacherDaoImp teacherDaoImp = new TeacherDaoImp();
				Teacher teacher = teacherDaoImp.getTeacherById(teacher_id,1);
				testPaper = new TestPaper(teacher, TestPaperBulidType.values()[bulidType],QuestionDifficultyType.values()[difficulty]);
				testPaper.setStatus(TestPaperStatusTrack.values()[statusTrack]);
				testPaper.setUseType(TestPaperUseType.values()[useType]);
				testPaper.setName(name);
				testPaper.setFinishTime(finishTime);
				testPaper.setCreateTime(new Date(createTime.getTime()));
				testPaper.setUpdateTime(new Date(updateTime.getTime()));
				testPaper.setSingles(singles);
				testPaper.setMultiples(multiples);
				testPaper.setFillBlanks(fillBlanks);
				testPaper.setJudges(judges);
				testPaper.setSingleMark(singleMark);
				testPaper.setMultipleMark(multipleMark);
				testPaper.setJudgeMark(judgeMark);
				testPaper.setFillBlankMark(fillBlankMark);
				testPaper.setTotalMark(totalMark);
				testPaper.setId(id);
				testPaper.getTotalMark();//统计虚拟总分
			}
		} catch (Exception e) {
			testPaper = null;
			e.printStackTrace();
		} finally {
			try {
				if(oips!=null){
					oips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			DBUtils.closeConnection(conn, stmt, null);
		}
		return testPaper;
	}

}
