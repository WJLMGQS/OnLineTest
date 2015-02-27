/**
**作者：翁加林
**时间：2012-8-15
**文件名：SQLUtil.java
**包名：org.wjlmgqs.dao.util
**工程名：OnLineTest12
*/


package org.wjlmgqs.dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class SQLUtil {
	/*
	 * 封装sql语句，拼接上limit语句
	 * */
/*	public String LimitSQLByIndex(int i, int pageSize,String sql) {
		String rooter = "";
		if(pageSize>0) {
			rooter = "limit " +i+","+pageSize;
		}
		return sql+rooter;
	}*/

	public int getTableNumber(String sql) {
		SQLUtil sqlUtil = new SQLUtil();
		sqlUtil.dealSQLPrint(sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int totalSize =0;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				totalSize = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据异常");
		}
		return totalSize;
	}
	
	
	public String dealIds2Or(String[] ids,String title){
		String results = Arrays.asList(ids).toString().replace("[", "(").replace("]",")");
		results = title+" in " +results;
		System.out.println("test:"+results);
		return results;
	}


	/*
	 * @see 控制是否输出sql语句
	 * */
	public void dealSQLPrint(String sql) {
		System.out.println("sql:"+sql);
	}
}
