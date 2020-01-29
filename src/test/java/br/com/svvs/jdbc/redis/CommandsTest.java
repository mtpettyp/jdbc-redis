package br.com.svvs.jdbc.redis;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private static String keyPrefix;

    @BeforeClass
    public static void connect() throws Exception {
        TestHelper t = TestHelper.getInstance();
        conn = t.getConnection();
        keyPrefix = t.get("keyPrefix");
    }

    @Test(expected = SQLException.class)
    public void invalidCommand() throws Exception {
        executeSingleStringResult("INVALID");
    }

    @Test
    public void set() throws Exception {
        String key = keyPrefix + "_SET";
        assertEquals("OK", executeSingleStringResult("SET " + key + " value"));
        assertEquals("value", retrieveValue(key));
        delete(key);
    }

    @Test
    public void get() throws Exception {
        String key = keyPrefix + "_GET";
        createValue(key, "value");
        assertEquals("value", retrieveValue(key));
        delete(key);
    }

    @Test
    public void mget() throws Exception {
        String key = keyPrefix + "_MGET";
        createValue(key + 1, "value1");
        createValue(key + 2, "value2");

        List<String> results = executeStringResults("MGET " + key + "1 " + key + "2");

        assertEquals(2, results.size());
        assertTrue(results.contains("value1"));
        assertTrue(results.contains("value2"));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void setnx() throws Exception {
        String key = keyPrefix + "_SETNX_NON_EXISTENT_KEY";
        // let's store store a non-existent key, which should be true as return.
        assertTrue(executeSingleBooleanResult("SETNX " + key + " value"));

        // now let's do it again, now SETNX should return false.
        assertFalse(executeSingleBooleanResult("SETNX " + key + " value"));

        delete(key);
    }

    @Test
    public void incr() throws Exception {
        String key = keyPrefix + "_INCR_TEST_KEY";
        assertEquals(1,executeSingleIntegerResult("INCR " + key));
        assertEquals(2,executeSingleIntegerResult("INCR " + key));
        delete(key);
    }

    @Test
    public void incrby() throws Exception {
        String key = keyPrefix + "_INCRBY_TEST_KEY";
        assertEquals(2,executeSingleIntegerResult("INCRBY " + key + " 2"));
        assertEquals(12,executeSingleIntegerResult("INCRBY " + key + " 10"));
        delete(key);
    }

    @Test
    public void decr() throws Exception {
        String key = keyPrefix + "_DECR_TEST_KEY";
        assertEquals(-1, executeSingleIntegerResult("DECR " + key));
        assertEquals(-2, executeSingleIntegerResult("DECR " + key));
        delete(key);
    }

    @Test
    public void decrby() throws Exception{
        String key = keyPrefix + "_DECRBY_TEST_KEY";
        assertEquals(-1, executeSingleIntegerResult("DECRBY " + key + " 1"));
        assertEquals(-11, executeSingleIntegerResult("DECRBY " + key + " 10"));
        delete(key);
    }

    @Test
    public void exists() throws Exception {
        String key = keyPrefix + "_EXISTS_TEST_KEY";
        assertFalse(executeSingleBooleanResult("EXISTS " + key));
        createValue(key, "value");
        assertTrue(executeSingleBooleanResult("EXISTS " + key));
        delete(key);
    }

    @Test
    public void del() throws Exception {
        String key = keyPrefix + "_DEL_TEST_KEY";

        // trying to del a non existent key should return false.
        assertFalse(executeSingleBooleanResult("DEL " + key));

        // del a key should return true...
        createValue(key, "value");
        assertTrue(executeSingleBooleanResult("DEL " + key));

        // the deleted key should be a null if we try to get it after the del.
        assertNull(retrieveValue(key));
    }

    @Test
    public void type() throws Exception {
        String stringKey = keyPrefix + "_TYPE_STRING_TEST_KEY";
        String listKey   = keyPrefix + "_TYPE_LIST_TEST_KEY";
        String setKey   = keyPrefix  + "_TYPE_SET_TEST_KEY";
        String nonKey   = keyPrefix  + "_TYPE_NONE_TEST_KEY";

        // create some possible types
        execute("SET " + stringKey + " value");
        execute("LPUSH " + listKey + " value");
        execute("SADD " + setKey + " value");

        assertEquals("string", executeSingleStringResult("TYPE " + stringKey));
        assertEquals("list", executeSingleStringResult("TYPE " + listKey));
        assertEquals("set", executeSingleStringResult("TYPE " + setKey));
        assertEquals("none", executeSingleStringResult("TYPE " + nonKey));

        delete(stringKey);
        delete(listKey);
        delete(setKey);
    }

    @Test
    public void keys() throws Exception {
        String key = keyPrefix + "_KEYS_1";
        String keyGlob = keyPrefix + "_KEYS_1*";
        createValue(key, "value");
        assertEquals(key, executeSingleStringResult("KEYS " + keyGlob));
        delete(key);
    }

    @Test
    public void randomkey() throws Exception {
        String key = keyPrefix + "_RANDOMKEY";
        createValue(key, "value");
        assertEquals(key, executeSingleStringResult("RANDOMKEY"));
        delete(key);
    }

    @Test
    public void rename() throws Exception {
        String oldNameKey = keyPrefix + "_RENAME_TEST_OLD_KEY";
        String newNameKey = keyPrefix + "_RENAME_TEST_NEW_KEY";

        createValue(oldNameKey, "value");
        // now we rename it, it should destroy the old key.
        execute("RENAME " + oldNameKey + " " + newNameKey);

        // a get on the old key should return null.
        assertNull(retrieveValue(oldNameKey));

        // the new key should be defined and with out value.
        assertEquals("value", retrieveValue(newNameKey));

        delete(newNameKey);
    }

    @Test
    public void renamenx() throws Exception {
        String oldNameKey = keyPrefix + "_RENAMENX_TEST_OLD_KEY";
        String newNameKey = keyPrefix + "_RENAMENX_TEST_NEW_KEY";

        // setting a test key...
        createValue(oldNameKey, "value");

        // now we rename it, it should destroy the old key and return true.
        assertTrue(executeSingleBooleanResult("RENAMENX " + oldNameKey + " " + newNameKey));

        // a get on the old key should return null.
        assertNull(retrieveValue(oldNameKey));

        // the new key should be defined and with out value.
        assertEquals("value", retrieveValue(newNameKey));

        // now let's set the old one again
        createValue(oldNameKey, "value");

        // the new key already exists and renamenx should return false.
        assertFalse(executeSingleBooleanResult("RENAMENX " + oldNameKey + " " + newNameKey));

        delete(oldNameKey);
        delete(newNameKey);
    }

    @Test
    public void dbsize() throws Exception {
        assertEquals(0, executeSingleIntegerResult("DBSIZE"));
    }

    @Test
    public void expire() throws Exception {
        String key = keyPrefix + "_EXPIRE_TEST_KEY";

        createValue(key, "value");

        // set it to expire in one seconds...
        execute("EXPIRE " + key + " 1");

        // sleep a little so Redis can remove the key in time.
        try {
            Thread.sleep(2000); // two seconds
        }
        catch (InterruptedException e) {
            fail(e.getMessage());
        }

        // the key should not exists anymore.
        assertNull(retrieveValue(key));
    }

    @Test
    public void pexpire() throws Exception {
        String key = keyPrefix + "_PEXPIRE_TEST_KEY";

        createValue(key, "value");

        // set it to expire in one seconds...
        execute("PEXPIRE " + key + " 1000");

        // sleep a little so Redis can remove the key in time.
        try {
            Thread.sleep(2000); // two seconds
        }
        catch (InterruptedException e) {
            fail(e.getMessage());
        }

        // the key should not exists anymore.
        assertNull(retrieveValue(key));
    }

    @Test
    public void expireat() throws Exception {
        String key = keyPrefix + "_EXPIREAT_TEST_KEY";

        createValue(key, "value");

        // set it to expire in one second...
        execute("EXPIREAT " + key + " 1293840000");

        // the key should not exists anymore.
        assertNull(retrieveValue(key));
    }

    @Test
    public void ttl() throws Exception {
        String key = keyPrefix + "_TTL_TEST_KEY";

        createValue(key, "value");

        execute("EXPIRE " + key + " 10");

        int timeout = executeSingleIntegerResult("TTL " + key);
        assertTrue("Timeout is between 10 and 0", timeout >= 0 && timeout <= 10);

        delete(key);
    }

    @Test
    public void pttl() throws Exception {
        String key = keyPrefix + "_PTTL_TEST_KEY";

        createValue(key, "value");

        execute("PEXPIRE " + key + " 10000");

        int timeout = executeSingleIntegerResult("PTTL " + key);
        assertTrue("Timeout is between 10 and 0", timeout >= 0 && timeout <= 10000);

        delete(key);
    }


    @Test
    public void rpush() throws Exception {
        String key = keyPrefix + "_RPUSH_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        assertEquals("second", executeSingleStringResult("LINDEX " + key + " 1"));
        delete(key);
    }

    @Test
    public void rpushx() throws Exception {
        String key = keyPrefix + "_RPUSHX_TEST_KEY";
        assertEquals(0, executeSingleIntegerResult("RPUSHX " + key + " 1"));
        delete(key);
    }

    @Test
    public void lpush() throws Exception {
        String key = keyPrefix + "_LPUSH_TEST_KEY";
        execute("LPUSH " + key + " first");
        execute("LPUSH " + key + " second");
        assertEquals("first", executeSingleStringResult("LINDEX " + key + " 1"));
        delete(key);
    }

    @Test
    public void lpushx() throws Exception {
        String key = keyPrefix + "_LPUSHX_TEST_KEY";

        assertEquals(0, executeSingleIntegerResult("LPUSHX " + key + " 1"));
    }

    @Test
    public void llen() throws Exception {
        String key = keyPrefix + "_LLEN_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        assertEquals(2, executeSingleIntegerResult("LLEN " + key));
        delete(key);
    }

    @Test
    public void lrange() throws Exception {
        String key = keyPrefix + "_LRANGE_TEST_KEY";

        execute("LPUSH " + key + " first");
        execute("LPUSH " + key + " second");

        List<String> results = executeStringResults("LRANGE " + key + " 1 2");
        assertEquals(1, results.size());
        assertEquals("first", results.get(0));

        delete(key);
    }

    @Test
    public void ltrim() throws Exception {
        String key = keyPrefix + "_LTRIM_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        execute("RPUSH " + key + " third");

        execute("LTRIM " + key + " 0 1");

        List<String> results = executeStringResults("LRANGE " + key + " 0 2");
        assertEquals(2, results.size());
        assertEquals("first", results.get(0));
        assertEquals("second", results.get(1));

        delete(key);
    }

    @Test
    public void lpop() throws Exception {
        String key = keyPrefix + "_LPOP_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        execute("RPUSH " + key + " third");

        String[] r = new String[]{"first", "second", "third"};

        for(int i = 0; i < r.length; i++) {
            assertEquals(r[i], executeSingleStringResult("LPOP " + key));
        }

        delete(key);
    }

    @Test
    public void rpop() throws Exception {
        String key = keyPrefix + "_RPOP_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        execute("RPUSH " + key + " third");

        String[] r = new String[]{"third", "second", "first"};

        for (int i = 0; i < r.length; i++) {
            assertEquals(r[i], executeSingleStringResult("RPOP " + key));
        }

        delete(key);
    }

    @Test
    public void lset() throws Exception {
        String key = keyPrefix + "_LSET_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        execute("RPUSH " + key + " third");

        String[] r = new String[]{"first","second","new_third"};

        execute("LSET " + key + " 2 new_third");

        for (int i = 0; i < r.length; i++) {
            assertEquals(r[i], executeSingleStringResult("LPOP " + key));
        }

        delete(key);
    }

    @Test
    public void lrem() throws Exception {
        String key = keyPrefix + "_LREM_TEST_KEY";
        execute("RPUSH " + key + " first");
        execute("RPUSH " + key + " second");
        execute("RPUSH " + key + " third");

        String[] r = new String[]{"first","third"};

        execute("LREM " + key + " 1 second");

        for (int i = 0; i < r.length; i++) {
            assertEquals(r[i], executeSingleStringResult("LPOP " + key));
        }

        delete(key);
    }

    @Test
    public void append() throws Exception {
        String key = keyPrefix + "_APPEND";
        assertEquals(5, executeSingleIntegerResult("APPEND " + key + " value"));
        assertEquals(6, executeSingleIntegerResult("APPEND " + key + " s"));
        assertEquals("values", retrieveValue(key));
        delete(key);
    }

    @Test
    public void mset() throws Exception {
        assertEquals("OK", executeSingleStringResult("MSET " + keyPrefix + "_MSET1 value1 "
                + keyPrefix + "_MSET2 value2"));

        assertEquals("value1", retrieveValue(keyPrefix + "_MSET1"));
        assertEquals("value2", retrieveValue(keyPrefix + "_MSET2"));

        delete(keyPrefix + "_MSET1");
        delete(keyPrefix + "_MSET2");
    }

    @Test
    public void getset() throws Exception {
        String key = keyPrefix + "_GETSET";
        createValue(key, "value");
        assertEquals("value", executeSingleStringResult("GETSET " + key + " value1"));
        assertEquals("value1", retrieveValue(key));
        delete(key);
    }
    
    @Test
    public void scan() throws Exception {
        String key = keyPrefix + "_SCAN";

        for (int i = 0; i < 30; i++ ) {
            createValue(key + i, "value" + i);
        }

        Set<String> results = new HashSet<>(executeStringResults("SCAN 0"));

        assertEquals(30, results.size());

        for (int i = 0; i < 30; i++ ) {
            assertTrue(results.contains(key + i));
        }

        for (int i = 0; i < 30; i++ ) {
            delete(key + i);
        }
}

    @Test
    public void bgrewriteaof() throws Exception {
        assertEquals("Background append only file rewriting started",
                executeSingleStringResult("BGREWRITEAOF"));
    }

    @Test
    public void setbit() throws Exception {
        String key = keyPrefix + "_SETBIT";

        assertEquals(0, executeSingleIntegerResult("SETBIT " + key + " 1 1"));

        delete(key);
    }

    @Test
    public void getbit() throws Exception {
        String key = keyPrefix + "_GETBIT";

        assertEquals(0, executeSingleIntegerResult("SETBIT " + key + " 7 1"));
        assertEquals(1, executeSingleIntegerResult("GETBIT " + key + " 7"));

        delete(key);
    }

    @Test
    public void bitcount() throws Exception {
        String key = keyPrefix + "_BITCOUNT";

        createValue(key, "foobar");
        assertEquals(26, executeSingleIntegerResult("BITCOUNT " + key));

        delete(key);
    }

    @Test
    public void bitpos() throws Exception {
        String key = keyPrefix + "_BITPOS";

        createValue(key, "\"\\xff\\xf0\\x00\"");
        assertEquals(12, executeSingleIntegerResult("BITPOS " + key + " 0"));

        delete(key);
    }

    @Test
    public void bitop() throws Exception {
        String key = keyPrefix + "_BITOP";

        createValue(key + 1, "foobar");
        createValue(key + 2, "abcdef");
        assertEquals(6, executeSingleIntegerResult("BITOP AND " + (key + 3) + " "  +
        (key + 1) + " " + (key + 2)));
        assertEquals("`bc`ab", retrieveValue(key + 3));
        delete(key + 1);
        delete(key + 2);
        delete(key + 3);
    }

    @Test
    public void bitfield() throws Exception {
        String key = keyPrefix + "_BITFIELD";

        List<Integer> results = executeIntegerResults("BITFIELD " + key + " INCRBY i5 100 1 GET u4 0");

        assertEquals(2, results.size());
        assertEquals(1, (int)results.get(0));
        assertEquals(0, (int)results.get(1));

        delete(key);
    }

    @Test
    public void hdel() throws Exception {
        String key = keyPrefix + "_HDEL";

        execute("HSET " + key + " field1 \"foo\"");
        assertEquals(1, executeSingleIntegerResult("HDEL " + key + " field1 \"foo\""));
        assertEquals(0, executeSingleIntegerResult("HDEL " + key + " field1 \"foo\""));

        delete(key);
    }

    @Test
    public void hexists() throws Exception {
        String key = keyPrefix + "_HEXISTS";

        execute("HSET " + key + " field1 \"foo\"");
        assertEquals(1, executeSingleIntegerResult("HEXISTS " + key + " field1"));
        assertEquals(0, executeSingleIntegerResult("HEXISTS " + key + " field2"));

        delete(key);
    }

    @Test
    public void hget() throws Exception {
        String key = keyPrefix + "_HGET";

        execute("HSET " + key + " field1 \"foo\"");
        assertEquals("foo", executeSingleStringResult("HGET " + key + " field1"));
        assertNull(executeSingleStringResult("HGET " + key + " field2"));

        delete(key);
    }

    @Test
    public void hgetall() throws Exception {
        String key = keyPrefix + "_HGETALL";

        execute("HSET " + key + " field1 \"Hello\"");
        execute("HSET " + key + " field2 \"World\"");

        //TODO: should return two rows with two columns
        List<String> results = executeStringResults("HGETALL " + key);

        assertEquals(4, results.size());
        assertEquals("field1", results.get(0));
        assertEquals("Hello", results.get(1));
        assertEquals("field2", results.get(2));
        assertEquals("World", results.get(3));

        delete(key);
    }

    @Test
    public void hincrby() throws Exception {
        String key = keyPrefix + "_HINCRBY";

        execute("HSET " + key + " field1 5");
        assertEquals(6, executeSingleIntegerResult("HINCRBY " + key + " field1 1"));

        delete(key);
    }

    @Test
    public void hincrbyfloat() throws Exception {
        String key = keyPrefix + "_HINCRBYFLOAT";

        execute("HSET " + key + " field1 10.50");
        assertEquals("10.6", executeSingleStringResult("HINCRBYFLOAT " + key + " field1 0.1"));

        delete(key);
    }

    @Test
    public void hkeys() throws Exception {
        String key = keyPrefix + "_HKEYS";

        execute("HSET " + key + " field1 \"Hello\"");
        execute("HSET " + key + " field2 \"World\"");

        List<String> results = executeStringResults("HKEYS " + key);

        assertEquals(2, results.size());
        assertEquals("field1", results.get(0));
        assertEquals("field2", results.get(1));

        delete(key);
    }

    @Test
    public void hlen() throws Exception {
        String key = keyPrefix + "_HLEN";

        execute("HSET " + key + " field1 \"Hello\"");
        execute("HSET " + key + " field2 \"World\"");
        assertEquals(2, executeSingleIntegerResult("HLEN " + key));

        delete(key);
    }

    @Test
    public void hmget() throws Exception {
        String key = keyPrefix + "_HMGET";

        execute("HSET " + key + " field1 \"Hello\"");
        execute("HSET " + key + " field2 \"World\"");
        List<String> results = executeStringResults("HMGET " + key + " field1 field2 nofield");

        assertEquals(3, results.size());
        assertEquals("Hello", results.get(0));
        assertEquals("World", results.get(1));
        assertNull(results.get(2));

        delete(key);
    }

    @Test
    public void hmset() throws Exception {
        String key = keyPrefix + "_HMSET";

        execute("HMSET " + key + " field1 \"Hello\" field2 \"World\"");
        assertEquals("Hello", executeSingleStringResult("HGET " + key + " field1"));
        assertEquals("World", executeSingleStringResult("HGET " + key + " field2"));

        delete(key);
    }

    @Test
    public void hscan() throws Exception {
        String key = keyPrefix + "_HSCAN";

        execute("HMSET " + key + " field1 \"Hello\" field2 \"World\"");

        List<String> results = executeStringResults("HSCAN " + key + " 0");

        //TODO: return values as multiple columns

        assertEquals(4, results.size());
        assertEquals("field1", results.get(0));
        assertEquals("Hello", results.get(1));
        assertEquals("field2", results.get(2));
        assertEquals("World", results.get(3));

        delete(key);
    }

    @Test
    public void hset() throws Exception {
        String key = keyPrefix + "_HSET";

        assertEquals(1, executeSingleIntegerResult("HSET " + key + " field1 \"foo\""));

        delete(key);
    }

    @Test
    public void hsetnx() throws Exception {
        String key = keyPrefix + "_HSETNX";

        assertEquals(1, executeSingleIntegerResult("HSETNX " + key + " field \"Hello\""));
        assertEquals(0, executeSingleIntegerResult("HSETNX " + key + " field \"World\""));
        assertEquals("Hello", executeSingleStringResult("HGET " + key + " field"));

        delete(key);
    }

    @Test
    public void hstrlen() throws Exception {
        String key = keyPrefix + "_HSTRLN";

        execute("HSET " + key + " field \"Hello\"");
        assertEquals(5, executeSingleIntegerResult("HSTRLEN " + key + " field"));

        delete(key);
    }

    @Test
    public void hvals() throws Exception {
        String key = keyPrefix + "_HVALS";

        execute("HSET " + key + " field1 \"Hello\"");
        execute("HSET " + key + " field2 \"World\"");

        List<String> results = executeStringResults("HVALS " + key);

        assertEquals(2, results.size());
        assertEquals("Hello", results.get(0));
        assertEquals("World", results.get(1));

        delete(key);
    }

    @Test
    public void zadd() throws Exception {
        String key = keyPrefix + "_ZADD";

        assertEquals(1, executeSingleIntegerResult("ZADD " + key + " 1 \"one\""));

        delete(key);
    }

    @Test
    public void zcard() throws Exception {
        String key = keyPrefix + "_ZCARD";

        execute("ZADD " + key + " 1 \"one\"");
        execute("ZADD " + key + " 2 \"two\"");
        assertEquals(2, executeSingleIntegerResult("ZCARD " + key));

        delete(key);
    }

    @Test
    public void zcount() throws Exception {
        String key = keyPrefix + "_ZCOUNT";

        execute("ZADD " + key + " 1 \"one\"");
        execute("ZADD " + key + " 2 \"two\"");
        execute("ZADD " + key + " 3 \"three\"");
        assertEquals(2, executeSingleIntegerResult("ZCOUNT " + key + " (1 3"));

        delete(key);
    }

    @Test
    public void zincrby() throws Exception {
        String key = keyPrefix + "_ZINCRBY";

        assertEquals(1, executeSingleIntegerResult("ZADD " + key + " 1 \"one\""));
        assertEquals(3, executeSingleIntegerResult("ZINCRBY " + key + " 2 \"one\""));

        delete(key);
    }

    @Test
    public void zinterstore() throws Exception {
        String key = keyPrefix + "_ZINTERSTORE";

        execute("ZADD " + key + "1 1 \"one\"");
        execute("ZADD " + key + "2 2 \"one\"");
        assertEquals(1, executeSingleIntegerResult("ZINTERSTORE " + key + "OUT" + " 2 " + key + "1 " + key + "2"));

        delete(key + 1);
        delete(key + 2);
        delete(key + "OUT");
    }

    @Test
    public void zlexcount() throws Exception {
        String key = keyPrefix + "_ZLEXCOUNT";

        execute("ZADD " + key + " 0 a 0 b 0 c 0 d 0 e");
        assertEquals(5, executeSingleIntegerResult("ZLEXCOUNT " + key + " - +"));

        delete(key);
    }

    @Test
    public void zrange() throws Exception {
        String key = keyPrefix + "_ZRANGE";


        //TODO: handle WITHSCORES
        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        assertEquals("three", executeSingleStringResult("ZRANGE " + key + " 2 3"));

        delete(key);
    }

    @Test
    public void zrangebylex() throws Exception {
        String key = keyPrefix + "_ZRANGEBYLEX";

        execute("ZADD " + key + " 1 \"a\" 2 \"b\" 3 \"c\"");
        List<String> results = executeStringResults("ZRANGEBYLEX " + key + " - [b");

        assertEquals(2, results.size());
        assertEquals("a", results.get(0));
        assertEquals("b", results.get(1));

        delete(key);
    }

    @Test
    public void zrangebyscore() throws Exception {
        String key = keyPrefix + "_ZRANGEBYSCORE";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        List<String> results = executeStringResults("ZRANGEBYSCORE " + key + " 1 2");
        assertEquals(2, results.size());
        assertEquals("one", results.get(0));
        assertEquals("two", results.get(1));


        delete(key);
    }

    @Test
    public void zrank() throws Exception {
        String key = keyPrefix + "_ZRANK";

        execute("ZADD " + key + " 1 \"a\" 1 \"b\" 2 \"c\"");
        assertEquals(2, executeSingleIntegerResult("ZRANK " + key + " c"));

        delete(key);
    }

    @Test
    public void zrem() throws Exception {
        String key = keyPrefix + "_ZREM";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        assertEquals(1, executeSingleIntegerResult("ZREM " + key + " two"));

        delete(key);
    }

    @Test
    public void zremrangebylex() throws Exception {
        String key = keyPrefix + "_ZREMRANGEBYLEX";

        execute("ZADD " + key + " 0 a 0 b 0 c 0 d 0 e");
        assertEquals(2, executeSingleIntegerResult("ZREMRANGEBYLEX " + key + " (a [c"));

        delete(key);
    }

    @Test
    public void zremrangebyrank() throws Exception {
        String key = keyPrefix + "_ZREMRANGEBYRANK";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        assertEquals(2, executeSingleIntegerResult("ZREMRANGEBYRANK " + key + " 1 2"));

        delete(key);
    }

    @Test
    public void zremrangebyscore() throws Exception {
        String key = keyPrefix + "_ZREMRANGEBYSCORE";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        assertEquals(1, executeSingleIntegerResult("ZREMRANGEBYSCORE " + key + " -inf (2"));

        delete(key);
    }

    @Test
    public void zrevrange() throws Exception {
        String key = keyPrefix + "_ZREVRANGE";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        List<String> results = executeStringResults("ZREVRANGE " + key + " 0 -1");
        assertEquals(3, results.size());
        assertEquals("three", results.get(0));
        assertEquals("two", results.get(1));
        assertEquals("one", results.get(2));

        delete(key);
    }

    @Test
    public void zrevrangebylex() throws Exception {
        String key = keyPrefix + "_ZREVRANGEBYLEX";

        execute("ZADD " + key + " 0 a 0 b 0 c 0 d 0 e 0 f 0 g");
        List<String> results = executeStringResults("ZREVRANGEBYLEX " + key + " [c -");
        assertEquals(3, results.size());
        assertEquals("c", results.get(0));
        assertEquals("b", results.get(1));
        assertEquals("a", results.get(2));

        delete(key);
    }

    @Test
    public void zrevrangebyscore() throws Exception {
        String key = keyPrefix + "_ZREVRANGEBYSCORE";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        List<String> results = executeStringResults("ZREVRANGEBYSCORE " + key + " +inf -inf");
        assertEquals(3, results.size());
        assertEquals("three", results.get(0));
        assertEquals("two", results.get(1));
        assertEquals("one", results.get(2));

        delete(key);
    }

    @Test
    public void zrevrank() throws Exception {
        String key = keyPrefix + "_ZREMRANGEBYSCORE";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\" 3 \"three\"");
        assertEquals(2, executeSingleIntegerResult("ZREVRANK " + key + " one"));
        assertNull(executeSingleStringResult("ZREVRANK " + key + " four"));

        delete(key);
    }

    @Test
    public void zscan() throws Exception {
        String key = keyPrefix + "_ZSCAN";

        execute("ZADD " + key + " 1 \"one\" 2 \"two\"");

        List<String> results = executeStringResults("ZSCAN " + key + " 0");

        //TODO: return values as multiple columns

        assertEquals(4, results.size());
        assertEquals("one", results.get(0));
        assertEquals("1", results.get(1));
        assertEquals("two", results.get(2));
        assertEquals("2", results.get(3));

        delete(key);
    }

    @Test
    public void zscore() throws Exception {
        String key = keyPrefix + "_ZSCORE";

        execute("ZADD " + key + " 1 \"one\"");
        assertEquals(1, executeSingleIntegerResult("ZSCORE " + key + " one"));

        delete(key);
    }

    @Test
    public void zunionstore() throws Exception {
        String key = keyPrefix + "_ZUNIONSTORE";

        execute("ZADD " + key + "1 1 \"one\"");
        execute("ZADD " + key + "2 2 \"one\"");
        assertEquals(1, executeSingleIntegerResult("ZUNIONSTORE " + key + "OUT" + " 2 " + key + "1 " + key + "2"));

        delete(key + 1);
        delete(key + 2);
        delete(key + "OUT");
    }

    @Test
    public void sadd() throws Exception {
        String key = keyPrefix + "_SADD";

        assertEquals(1, executeSingleIntegerResult("SADD " + key + " \"Hello\""));

        delete(key);
    }

    @Test
    public void scard() throws Exception {
        String key = keyPrefix + "_SCARD";

        execute("SADD " + key + " \"Hello\"");
        execute("SADD " + key + " \"World\"");
        assertEquals(2, executeSingleIntegerResult("SCARD " + key));

        delete(key);
    }

    @Test
    public void sdiff() throws Exception {
        String key = keyPrefix + "_SDIFF";

        execute("SADD " + key + "1 \"a\"");
        execute("SADD " + key + "1 \"b\"");
        execute("SADD " + key + "1 \"c\"");
        execute("SADD " + key + "2 \"c\"");
        execute("SADD " + key + "2 \"d\"");
        execute("SADD " + key + "2 \"e\"");
        List<String> results = executeStringResults("SDIFF " + key + "1 " + key + "2");

        assertEquals(2, results.size());
        assertTrue(results.contains("a"));
        assertTrue(results.contains("b"));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void sdiffstore() throws Exception {
        String key = keyPrefix + "_SDIFFSTORE";

        execute("SADD " + key + "1 \"a\"");
        execute("SADD " + key + "1 \"b\"");
        execute("SADD " + key + "1 \"c\"");
        execute("SADD " + key + "2 \"c\"");
        execute("SADD " + key + "2 \"d\"");
        execute("SADD " + key + "2 \"e\"");
        assertEquals(2, executeSingleIntegerResult("SDIFFSTORE " + key + " " + key + "1 " + key + "2"));

        delete(key);
        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void sinter() throws Exception {
        String key = keyPrefix + "_SINTER";

        execute("SADD " + key + "1 \"a\"");
        execute("SADD " + key + "1 \"b\"");
        execute("SADD " + key + "1 \"c\"");
        execute("SADD " + key + "2 \"c\"");
        execute("SADD " + key + "2 \"d\"");
        execute("SADD " + key + "2 \"e\"");
        List<String> results = executeStringResults("SINTER " + key + "1 " + key + "2");

        assertEquals(1, results.size());
        assertEquals("c", results.get(0));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void sinterstore() throws Exception {
        String key = keyPrefix + "_SINTERSTORE";

        execute("SADD " + key + "1 \"a\"");
        execute("SADD " + key + "1 \"b\"");
        execute("SADD " + key + "1 \"c\"");
        execute("SADD " + key + "2 \"c\"");
        execute("SADD " + key + "2 \"d\"");
        execute("SADD " + key + "2 \"e\"");
        assertEquals(1, executeSingleIntegerResult("SINTERSTORE " + key + " " + key + "1 " + key + "2"));

        delete(key);
        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void sismember() throws Exception {
        String key = keyPrefix + "_SISMEMBER";

        execute("SADD " + key + " one");
        assertEquals(1, executeSingleIntegerResult("SISMEMBER " + key + " one"));
        assertEquals(0, executeSingleIntegerResult("SISMEMBER " + key + " two"));

        delete(key);
    }

    @Test
    public void smembers() throws Exception {
        String key = keyPrefix + "_SMEMBERS";

        execute("SADD " + key + " Hello");
        execute("SADD " + key + " World");
        List<String> results = executeStringResults("SMEMBERS " + key);

        assertEquals(2, results.size());
        assertTrue(results.contains("Hello"));
        assertTrue(results.contains("World"));

        delete(key);
    }

    @Test
    public void smove() throws Exception {
        String key = keyPrefix + "_SMOVE";

        execute("SADD " + key + "1 \"one\"");
        execute("SADD " + key + "1 \"two\"");
        execute("SADD " + key + "2 \"three\"");
        assertEquals(1, executeSingleIntegerResult("SMOVE " + key + "1 " + key + "2 two"));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void spop() throws Exception {
        String key = keyPrefix + "_SPOP";

        execute("SADD " + key + " one");
        execute("SADD " + key + " two");
        execute("SADD " + key + " three");
        assertThat(executeSingleStringResult("SPOP " + key),  anyOf(is("one"), is("two"), is("three")));

        delete(key);
    }

    @Test
    public void unwatch() throws Exception {
        assertEquals("OK", executeSingleStringResult("UNWATCH"));
    }

    @Test
    public void watch() throws Exception {
        String key = keyPrefix + "_WATCH";

        assertEquals("OK", executeSingleStringResult("WATCH " + key));

        delete(key);
    }

    @Test
    public void time() throws Exception {

        //TODO: convert the response into a java datetime
        List<Integer> results = executeIntegerResults("TIME");
        assertEquals(2, results.size());

    }

    @Test
    public void strlen() throws Exception {
        String key = keyPrefix + "_STRLEN";

        createValue(key, "\"Hello world\"");
        assertEquals(11, executeSingleIntegerResult("STRLEN " + key));

        delete(key);
    }

    @Test
    public void blpop() throws Exception {
        String key = keyPrefix + "_BLPOP";

        execute("RPUSH " + key + " a b c");
        List<String> results = executeStringResults("BLPOP " + key + " 0");
        assertEquals(2, results.size());
        assertEquals(key, results.get(0));
        assertEquals("a", results.get(1));

        //TODO: should be returned as a single row


        delete(key);
    }

    @Test
    public void brpop() throws Exception {
        String key = keyPrefix + "_BRPOP";

        execute("RPUSH " + key + " a b c");
        List<String> results = executeStringResults("BRPOP " + key + " 0");
        assertEquals(2, results.size());
        assertEquals(key, results.get(0));
        assertEquals("c", results.get(1));

        //TODO: should be returned as a single row

        delete(key);
    }

    @Test
    public void rpoplpush() throws Exception {
        String key = keyPrefix + "_RPOPLPUSH";

        execute("RPUSH " + key + "1 a b c");
        assertEquals("c", executeSingleStringResult("RPOPLPUSH " + key + "1 " + key + "2"));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void brpoplpush() throws Exception {
        String key = keyPrefix + "_BRPOPLPUSH";

        execute("RPUSH " + key + "1 a b c");
        assertEquals("c", executeSingleStringResult("BRPOPLPUSH " + key + "1 " + key + "2 0"));

        delete(key + 1);
        delete(key + 2);
    }

    @Test
    public void lindex() throws Exception {
        String key = keyPrefix + "_LINDEX";

        execute("RPUSH " + key + " a b c");
        assertEquals("a", executeSingleStringResult("LINDEX " + key + " 0"));

        delete(key);
    }

    @Test
    public void linsert() throws Exception {
        String key = keyPrefix + "_LINSERT";

        execute("RPUSH " + key + " a c");
        assertEquals(3, executeSingleIntegerResult("LINSERT " + key + " BEFORE c a"));

        delete(key);
    }

    private void execute(final String command) throws Exception {
        conn.createStatement().execute(command);
    }

    private ResultSet executeQuery(final String command) throws Exception {
        return conn.createStatement().executeQuery(command);
    }

    private void createValue(String key, String value) throws Exception {
        execute("SET " + key + " " + value);
    }

    private String retrieveValue(String key) throws Exception {
        return executeSingleStringResult("GET " + key);
    }

    private void delete(final String key) throws Exception {
        execute("DEL " + key);
    }

    private String executeSingleStringResult(final String command) throws Exception {
        List<String> results = executeStringResults(command);
        assertEquals(1, results.size());
        return results.get(0);
    }

    private int executeSingleIntegerResult(final String command) throws Exception {
        List<Integer> results = executeIntegerResults(command);
        assertEquals(1, results.size());
        return results.get(0);
    }

    private Boolean executeSingleBooleanResult(final String command) throws Exception {
        List<Boolean> results = executeBooleanResults(command);
        assertEquals(1, results.size());
        return results.get(0);
    }

    private List<String> executeStringResults(final String command) throws Exception {
        return executeSingleResult(command, (rs, x) -> rs.getString(x));
    }

    private List<Integer> executeIntegerResults(final String command) throws Exception {
        return executeSingleResult(command, (rs, x) -> rs.getInt(x));
    }

    private List<Boolean> executeBooleanResults(final String command) throws Exception {
        return executeSingleResult(command, (rs, x) -> rs.getBoolean(x));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> List<T> executeSingleResult(final String command, Retrieve<T> operation) throws Exception {
        ResultSet result = executeQuery(command);

        List results = new ArrayList();
        while(result.next()) {
            results.add(operation.query(result, 0));
        }
        result.close();

        return results;
    }

    interface Retrieve<T> {
        T query(ResultSet rs, int index) throws Exception;
    }

}
