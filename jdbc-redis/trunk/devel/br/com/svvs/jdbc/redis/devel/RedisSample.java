package br.com.svvs.jdbc.redis.devel;

import java.sql.Connection;
import java.sql.DriverManager;
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
		
		for(int i = 1; i <= 10000; i++)
			statement.execute("SET key" + i + " value1");
		
		long interval = System.currentTimeMillis() - start;
		
		System.out.println("done?" + interval/1000);

	}

}
