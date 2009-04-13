package br.com.svvs.jdbc.redis.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringCommandsTest {
	
	private static Connection conn;
	private static SortedMap<String,String> map;
	private static String keyPrefix;
	
	// Not all keys will be retrieved from MGET since 
	// there's a size limit for the query.
	// Issue 24 in Redis Database project.
	private static int MAX_MGET_KEYS = 15;

	@BeforeClass
	public static void connect() {
		TestHelper t = TestHelper.getInstance();
		conn = t.getConnection();
		map  = t.genKeyValContents();
		keyPrefix = t.get("keyPrefix");
		
		// this is a setup and a SET test at
		// the same time.
		try {
			Statement st = conn.createStatement();
			for(String key : map.keySet()) {
				st.execute("SET " + key + " " + map.get(key));
				ResultSet rs = st.getResultSet();
				while(rs.next()) {
					assertEquals("OK",rs.getString(0));
				}
			}
			st.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void set() {
		// look at the BeforeClass method.
	}

	@Test
	public void get() {
		try {
			Statement st = conn.createStatement();
			for(String key : map.keySet()) {
				st.execute("GET " + key);
				ResultSet rs = st.getResultSet();
				while(rs.next()) {
					assertEquals(map.get(key), rs.getString(0));
				}
			}
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void mget() {
		try {
			Statement st = conn.createStatement();
			StringBuilder sb = new StringBuilder();
			
			int i = 0;
			for(String key : map.keySet()) {
				if(i++ >= MAX_MGET_KEYS) break;
				sb.append(key + " ");
			}
			
			ResultSet rs = st.executeQuery("MGET " + sb.toString());
			
			// put all in a list...
			List<String> result = new ArrayList<String>();
			while(rs.next()) {
				result.add(rs.getString(0));
			}
			
			// convert expected values in a string array.
			String[] expected = new String[MAX_MGET_KEYS];
			// careful with these index stuff, only the MAX_MGET_KEYS should
			// be created, in the same order that were stored.
			i = 0; 
			for(String r : (String[]) map.values().toArray(new String[0])) {
				if(i >= MAX_MGET_KEYS) break;
				expected[i] = r;
				i++;
			}
			
			//check if all keys were retrieved through MGET
			assertArrayEquals(expected, result.toArray(new String[0]));
			
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void setnx() {
		try {
			Statement st = conn.createStatement();
			String prefix = keyPrefix;
			// let's store store a non-existent key, which should be true as return.
			ResultSet resultSet = st.executeQuery("SETNX " + prefix + "_SETNX_NON_EXISTENT_KEY value");
			while(resultSet.next()) {
				assertTrue(resultSet.getBoolean(0));
			}
			resultSet.close();
			
			// now let's do it again, now SETNX should return false.
			resultSet = st.executeQuery("SETNX " + prefix + "_SETNX_NON_EXISTENT_KEY value");
			while(resultSet.next()) {
				assertFalse(resultSet.getBoolean(0));
			}
			resultSet.close();
			
			// remove the test key..
			conn.createStatement().execute("DEL " + prefix + "_SETNX_NON_EXISTENT_KEY");
			
		} catch(SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void incr() {
		try {
			String key = keyPrefix + "_INCR_TEST_KEY";
			ResultSet rs = conn.createStatement().executeQuery("INCR " + key);
			while(rs.next()) {
				assertEquals(1,rs.getInt(0));
			}
			
			// let's increment more times...
			for(int i = 0; i < 999; i++) {
				rs = conn.createStatement().executeQuery("INCR " + key);
			}
			// checking the result...
			while(rs.next()) {
				assertEquals(1000,rs.getInt(0));
			}
			
			conn.createStatement().executeQuery("DEL " + key); //cleanup.
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void incrby() {
		try {
			String key = keyPrefix + "_INCRBY_TEST_KEY";
			ResultSet rs = conn.createStatement().executeQuery("INCRBY " + key + " 1");
			while(rs.next()) {
				assertEquals(1,rs.getInt(0));
			}
			
			// let's increment more times...
			for(int i = 0; i < 1000; i++) {
				rs = conn.createStatement().executeQuery("INCRBY " + key + " 10");
			}
			// checking the result...
			while(rs.next()) {
				assertEquals(10001,rs.getInt(0));
			}
			
			conn.createStatement().executeQuery("DEL " + key); //cleanup.
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void decr() {
		try {
			String key = keyPrefix + "_DECR_TEST_KEY";
			ResultSet rs = conn.createStatement().executeQuery("DECR " + key);
			while(rs.next()) {
				assertEquals(-1,rs.getInt(0));
			}
			
			// let's decrement more times...
			for(int i = 0; i < 999; i++) {
				rs = conn.createStatement().executeQuery("DECR " + key);
			}
			// checking the result...
			while(rs.next()) {
				assertEquals(-1000,rs.getInt(0));
			}
			
			conn.createStatement().executeQuery("DEL " + key); //cleanup.
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void decrby() {
		try {
			String key = keyPrefix + "_DECRBY_TEST_KEY";
			ResultSet rs = conn.createStatement().executeQuery("DECRBY " + key + " 1");
			while(rs.next()) {
				assertEquals(-1,rs.getInt(0));
			}
			
			// let's decrement more times...
			for(int i = 0; i < 1000; i++) {
				rs = conn.createStatement().executeQuery("DECRBY " + key + " 10");
			}
			// checking the result...
			while(rs.next()) {
				assertEquals(-10001,rs.getInt(0));
			}
			
			conn.createStatement().executeQuery("DEL " + key); //cleanup.
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void exists() {
		try {
			String key = keyPrefix + "_EXISTS_TEST_KEY";
			
			ResultSet rs = conn.createStatement().executeQuery("EXISTS " + key);
			while(rs.next()) assertFalse(rs.getBoolean(0)); // this key should not exists
			
			// make this key exists...
			conn.createStatement().execute("SET " + key + " value");
			
			rs = conn.createStatement().executeQuery("EXISTS " + key);
			while(rs.next()) assertTrue(rs.getBoolean(0)); // this key should exists
			
			//cleanup
			conn.createStatement().execute("DEL " + key);
			
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void del() {
		try {
			String key = keyPrefix + "_DEL_TEST_KEY";
			
			// trying to del a non existent key should return false.
			ResultSet rs = conn.createStatement().executeQuery("DEL " + key);
			while(rs.next()) assertFalse(rs.getBoolean(0)); // this key should not exists
			
			// make this key exists...
			conn.createStatement().execute("SET " + key + " value");
			
			// del a key should return true...
			rs = conn.createStatement().executeQuery("DEL " + key);
			while(rs.next()) assertTrue(rs.getBoolean(0)); // this key should exists
			
			// the deleted key should be a null if we try to get it after the del.
			rs = conn.createStatement().executeQuery("GET " + key);
			while(rs.next()) assertNull(rs.getString(0)); // this key should not exists
			
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void type() {
		try {
			String stringKey = keyPrefix + "_TYPE_STRING_TEST_KEY";
			String listKey   = keyPrefix + "_TYPE_LIST_TEST_KEY";
			String setKey   = keyPrefix  + "_TYPE_SET_TEST_KEY";
			String nonKey   = keyPrefix  + "_TYPE_NONE_TEST_KEY";
			
			// create a string key
			conn.createStatement().execute("SET "   + stringKey + " value");
			conn.createStatement().execute("LPUSH " + listKey   + " value");
			conn.createStatement().execute("SADD "  + setKey    + " value");
			
			ResultSet rs = conn.createStatement().executeQuery("TYPE " + stringKey);
			while(rs.next()) assertEquals("string", rs.getString(0));
			
			rs = conn.createStatement().executeQuery("TYPE " + listKey);
			while(rs.next()) assertEquals("list", rs.getString(0));
			
			rs = conn.createStatement().executeQuery("TYPE " + setKey);
			while(rs.next()) assertEquals("set", rs.getString(0));
			
			rs = conn.createStatement().executeQuery("TYPE " + nonKey);
			while(rs.next()) assertEquals("none", rs.getString(0));
			
			//cleanup...
			conn.createStatement().execute("DEL " + stringKey);
			conn.createStatement().execute("DEL " + listKey);
			conn.createStatement().execute("DEL " + setKey);
			
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void clean() {
		for(String key : map.keySet()) {
			try {
				conn.createStatement().execute("DEL " + key);
			} catch (SQLException e) {
				fail(e.getMessage());
			}
		}
	}

}
