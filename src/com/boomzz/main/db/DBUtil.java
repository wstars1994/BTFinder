/**
 * author : 王新晨
 * date : 2018年8月1日 下午2:39:55
 */
package com.boomzz.main.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
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
