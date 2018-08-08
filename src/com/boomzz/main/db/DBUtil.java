/**
 * author : 王新晨
 * date : 2018年8月1日 下午2:39:55
 */
package com.boomzz.main.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomzz.main.DHTClientBoot;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
	private static boolean isProduct = false;

	private static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void init() {
		if(!DHTClientBoot.isProduct) return;
		//创建node记录表
		String dhtNodeCreateSql = "CREATE TABLE IF NOT EXISTS BT_DHT_NODE ( ID INT (11) NOT NULL PRIMARY KEY AUTO_INCREMENT, NODE_ID VARCHAR (60) NOT NULL, NODE_IP VARCHAR (64) NOT NULL, NODE_PORT INT (5) NOT NULL, REQ_COUNT INT (1) DEFAULT 1 NOT NULL, LIFE_STATUS INT (1) DEFAULT 1 NOT NULL );";
		execute(dhtNodeCreateSql);
	}
	
	public static void execute(String sql) {
		if(!DHTClientBoot.isProduct) return;
		try {
			Connection connection = getConnection();
			Statement createStatement = connection.createStatement();
			createStatement.execute(sql);
			close(connection,createStatement,null,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Map<String, Object>> search(String sql) {
//		if(!DHTClientBoot.isProduct) return null;
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			Connection connection = getConnection();
			Statement createStatement = connection.createStatement();
			ResultSet executeQuery = createStatement.executeQuery(sql);
			ResultSetMetaData md = executeQuery.getMetaData();
			int columnCount = md.getColumnCount();
	        while (executeQuery.next()) {
	            Map rowData = new HashMap();
	            for (int i = 1; i <= columnCount; i++) {
	                rowData.put(md.getColumnName(i), executeQuery.getObject(i));
	            }
	            list.add(rowData);
	        }
	        close(connection,createStatement,null,null);
	        return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void close(Connection conn, Statement st,PreparedStatement pst,ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				
			}
		}
	}

}
