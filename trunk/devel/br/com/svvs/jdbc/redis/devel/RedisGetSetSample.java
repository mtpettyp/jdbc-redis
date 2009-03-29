package br.com.svvs.jdbc.redis.devel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RedisGetSetSample {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:redis://localhost");
		
		Statement statement = conn.createStatement();
		
		int TEST_LOOP = 10;
		
		long start = System.currentTimeMillis();
		
		for(int i = 1; i <= TEST_LOOP; i++) {
			if(statement.execute("SET k" + i + " value" + i)) {
				ResultSet rs = statement.getResultSet();
				while(rs.next()) {
					String s = rs.getString(0);
					System.out.println("response: " + s);
				}
			}
			else {
				throw new RuntimeException();
			}
		}
		
		long interval = (System.currentTimeMillis() - start)/1000;
		
		System.out.println("done SET in " + interval + " seconds.");
		
		start = System.currentTimeMillis();
		
		for(int i = 1; i <= TEST_LOOP; i++) {
			if(statement.execute("GET k" + i)) {
				ResultSet rs = statement.getResultSet();
				while(rs.next()) {
					String s = rs.getString(0);
					System.out.println("response: " + s);
				}
			}
			else {
				throw new RuntimeException();
			}			
		}
		
		interval = (System.currentTimeMillis() - start) /1000;
		
		System.out.println("done GET in " + interval + " seconds. ");

	}

}
