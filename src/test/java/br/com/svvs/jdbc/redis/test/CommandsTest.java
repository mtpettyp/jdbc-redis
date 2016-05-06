package br.com.svvs.jdbc.redis.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

/**
 * These are simple tests to check if the commands are working
 * properly.
 * @author mavcunha
 *
 */
public class CommandsTest {

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
            for(String r : map.values().toArray(new String[0])) {
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

            // create some possible types
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

    @Test
    public void keys() {
        String keyGlob = keyPrefix + "_99*";
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery("KEYS " + keyGlob);
            while(rs.next()) {
                assertTrue(map.containsKey(rs.getString(0)));
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
    /**
     * Note that RANDOMKEY returns a random key from the database
     * and not necessarily a key we know. If the database was empty
     * this test will perform correctly otherwise it can fail.
     */
    @Test
    public void randomkey() {
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery("RANDOMKEY");
            while(rs.next()) {
                assertTrue(map.containsKey(rs.getString(0)));
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void rename() {
        String oldNameKey = keyPrefix + "_RENAME_TEST_OLD_KEY";
        String newNameKey = keyPrefix + "_RENAME_TEST_NEW_KEY";

        ResultSet rs;
        try {
            // setting a test key...
            conn.createStatement().execute("SET "    + oldNameKey + " value");
            // now we rename it, it should destroy the old key.
            conn.createStatement().execute("RENAME " + oldNameKey + " " + newNameKey);

            // a get on the old key should return null.
            rs = conn.createStatement().executeQuery("GET " + oldNameKey);
            while(rs.next()) assertNull(rs.getString(0));

            // the new key should be defined and with out value.
            rs = conn.createStatement().executeQuery("GET " + newNameKey);
            while(rs.next()) assertEquals("value", rs.getString(0));

            // cleanup
            conn.createStatement().execute("DEL " + newNameKey);

        } catch (SQLException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void renamenx() {
        String oldNameKey = keyPrefix + "_RENAMENX_TEST_OLD_KEY";
        String newNameKey = keyPrefix + "_RENAMENX_TEST_NEW_KEY";

        ResultSet rs;
        try {
            // setting a test key...
            conn.createStatement().execute("SET " + oldNameKey + " value");

            // now we rename it, it should destroy the old key and return true.
            rs = conn.createStatement().executeQuery("RENAMENX " + oldNameKey + " " + newNameKey);
            while(rs.next()) assertTrue(rs.getBoolean(0));

            // a get on the old key should return null.
            rs = conn.createStatement().executeQuery("GET " + oldNameKey);
            while(rs.next()) assertNull(rs.getString(0));

            // the new key should be defined and with out value.
            rs = conn.createStatement().executeQuery("GET " + newNameKey);
            while(rs.next()) assertEquals("value", rs.getString(0));

            // now let's set the old one again
            conn.createStatement().execute("SET " + oldNameKey + " value");

            // the new key already exists and renamenx should return false.
            rs = conn.createStatement().executeQuery("RENAMENX " + oldNameKey + " " + newNameKey);
            while(rs.next()) assertFalse(rs.getBoolean(0));

            // cleanup
            conn.createStatement().execute("DEL " + oldNameKey);
            conn.createStatement().execute("DEL " + newNameKey);

        } catch (SQLException e) {

            fail(e.getMessage());
        }

    }

    /**
     * This test will work on a empty Redis instance.
     */
    @Test
    public void dbsize() {
        // dbsize depends of how many keys we have in
        // our test map, so we use map.size()
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery("DBSIZE");
            while(rs.next()) {
                assertEquals(map.size(), rs.getInt(0));
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void expire() {
        String key = keyPrefix + "_EXPIRE_TEST_KEY";
        ResultSet rs;
        try {
            // create a key...
            conn.createStatement().execute("INCR " + key);

            // the key should exists.
            rs = conn.createStatement().executeQuery("GET " + key);
            while(rs.next()) assertNotNull(rs.getString(0));

            // set it to expire in one seconds...
            conn.createStatement().execute("EXPIRE " + key + " 1");

            // sleep a little so Redis can remove the key in time.
            try {
                Thread.sleep(2000); // two seconds
            }
            catch (InterruptedException e) {
                fail(e.getMessage());
            }

            // the key should not exists anymore.
            rs = conn.createStatement().executeQuery("GET " + key);
            while(rs.next()) assertNull(rs.getString(0));

        } catch (SQLException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void rpush() {
        String key = keyPrefix + "_RPUSH_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");

            // now the second element should be in last position
            ResultSet rs = conn.createStatement().executeQuery("LINDEX " + key + " 1");
            while(rs.next()) assertEquals("second", rs.getString(0));

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lpush() {
        String key = keyPrefix + "_LPUSH_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("LPUSH " + key + " first");
            conn.createStatement().execute("LPUSH " + key + " second");

            // now the first element should be in last position
            ResultSet rs = conn.createStatement().executeQuery("LINDEX " + key + " 1");
            while(rs.next()) assertEquals("first", rs.getString(0));

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void llen() {
        String key = keyPrefix + "_LLEN_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");

            // this list should have two elements.
            ResultSet rs = conn.createStatement().executeQuery("LLEN " + key);
            while(rs.next()) assertEquals(2, rs.getInt(0));

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lrange() {
        String key = keyPrefix + "_LRANGE_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("LPUSH " + key + " first");
            conn.createStatement().execute("LPUSH " + key + " second");

            String[] result = new String[]{"first","second"};

            // this list should have two elements.
            ResultSet rs = conn.createStatement().executeQuery("LRANGE " + key + " 1 2");
            int i = 0;
            while(rs.next()) {
                assertEquals(result[i], rs.getString(0));
                i++;
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void ltrim() {
        String key = keyPrefix + "_LTRIM_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");
            conn.createStatement().execute("RPUSH " + key + " third");

            // trim out
            conn.createStatement().execute("LTRIM " + key + " 0 1");

            String[] r = new String[]{"first","second"};

            // this list should have two elements.
            ResultSet rs = conn.createStatement().executeQuery("LRANGE " + key + " 0 2");
            int i = 0;
            while(rs.next()) {
                assertEquals(r[i], rs.getString(0));
                i++;
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lpop() {
        String key = keyPrefix + "_LPOP_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");
            conn.createStatement().execute("RPUSH " + key + " third");

            String[] r = new String[]{"first","second","third"};

            for(int i = 0; i < r.length; i++) {
                ResultSet rs = conn.createStatement().executeQuery("LPOP " + key);
                while(rs.next()) {
                    assertEquals(r[i], rs.getString(0));
                }
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void rpop() {
        String key = keyPrefix + "_RPOP_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");
            conn.createStatement().execute("RPUSH " + key + " third");

            String[] r = new String[]{"third","second","first"};

            for(int i = 0; i < r.length; i++) {
                ResultSet rs = conn.createStatement().executeQuery("RPOP " + key);
                while(rs.next()) {
                    assertEquals(r[i], rs.getString(0));
                }
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lset() {
        String key = keyPrefix + "_LSET_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");
            conn.createStatement().execute("RPUSH " + key + " third");

            String[] r = new String[]{"first","second","new_third"};

            conn.createStatement().execute("LSET " + key + " 2 new_third");

            for(int i = 0; i < r.length; i++) {
                ResultSet rs = conn.createStatement().executeQuery("LPOP " + key);
                while(rs.next()) {
                    assertEquals(r[i], rs.getString(0));
                }
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lrem() {
        String key = keyPrefix + "_LREM_TEST_KEY";
        try {
            // let's push two elements
            conn.createStatement().execute("RPUSH " + key + " first");
            conn.createStatement().execute("RPUSH " + key + " second");
            conn.createStatement().execute("RPUSH " + key + " third");

            String[] r = new String[]{"first","third"};

            conn.createStatement().execute("LREM " + key + " 1 second");

            for(int i = 0; i < r.length; i++) {
                ResultSet rs = conn.createStatement().executeQuery("LPOP " + key);
                while(rs.next()) {
                    assertEquals(r[i], rs.getString(0));
                }
            }

            //cleanup
            conn.createStatement().execute("DEL " + key);

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void append() {
        try {
            Statement st = conn.createStatement();
            String prefix = keyPrefix;
            // let's append a non-existent key
            ResultSet resultSet = st.executeQuery("APPEND " + prefix + "_APPEND value");
            while(resultSet.next()) {
                assertEquals(5, resultSet.getInt(0));
            }
            resultSet.close();

            // now let's append to that key
            resultSet = st.executeQuery("APPEND " + prefix + "_APPEND s");
            while(resultSet.next()) {
                assertEquals(6, resultSet.getInt(0));
            }
            resultSet.close();

            resultSet = st.executeQuery("GET " + prefix + "_APPEND");
            while(resultSet.next()) {
                assertEquals("values", resultSet.getString(0));
            }

            // remove the test key..
            conn.createStatement().execute("DEL " + prefix + "_APPEND");

        } catch(SQLException e) {
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
