package br.com.svvs.jdbc.redis.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class should test a simple storage and retrival
 * of key/values.
 * @author mavcunha
 *
 */
public class SimpleStorageTest {
	
	private static Connection conn = null;
	private static TestHelper tch;
	
	@BeforeClass
	public static void connect() throws SQLException, ClassNotFoundException {
		Class.forName("br.com.svvs.jdbc.redis.RedisDriver");
		tch = TestHelper.getInstance();
		conn = DriverManager.getConnection(tch.getConnectionString());
	}
	
	@Test
	public void store() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("SET " + tch.get("keyPrefix") + " some_value");
			ResultSet rs = st.getResultSet();
			while(rs.next()) {
				assertEquals("OK", rs.getString(0));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void retrieve() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("GET " + tch.get("keyPrefix"));
			ResultSet rs = st.getResultSet();
			while(rs.next()) {
				assertEquals("some_value", rs.getString(0));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void cleanup() {
		try {
			conn.createStatement().execute("DEL " + tch.get("keyPrefix"));
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
