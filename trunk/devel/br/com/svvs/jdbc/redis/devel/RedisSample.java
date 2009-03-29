package br.com.svvs.jdbc.redis.devel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RedisSample {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:redis://localhost");
		
		Statement statement = conn.createStatement();
		
		long start = System.currentTimeMillis();
		
		for(int i = 1; i <= 10000; i++) {
			if(statement.execute("SET " + i + "a")) {
				ResultSet rs = statement.getResultSet();
				while(rs.next()) {
					String s = rs.getString(0);
					//System.out.println("response: " + s);
				}
			}
			else {
				throw new RuntimeException();
			}
		}
		
		long interval = System.currentTimeMillis() - start;
		
		System.out.println("done in " + interval/1000 + " seconds.");

	}

}
