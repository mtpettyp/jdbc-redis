package br.com.svvs.jdbc.redis;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RESPDecoderTest {

    @Test
    public void simpleString() throws Exception {
        assertEquals("OK", RESPDecoder.decode("+OK\r\n"));
    }

    @Test(expected = RedisResultException.class)
    public void error() throws Exception {
        RESPDecoder.decode("-WRONGTYPE Operation against a key holding the wrong kind of value\r\n");
    }

    @Test
    public void bulkString() throws Exception {
        assertEquals("foobar", RESPDecoder.decode("$6\r\nfoobar\r\n"));
    }

    @Test
    public void emptyString() throws Exception {
        assertEquals("", RESPDecoder.decode("$0\r\n\r\n"));
    }

    @Test
    public void nullString() throws Exception {
        assertNull(RESPDecoder.decode("$-1\r\n"));
    }

    @Test
    public void integer() throws Exception {
        assertEquals("1000", RESPDecoder.decode(":1000\r\n"));
    }

    @Test
    public void emptyArray() throws Exception {
        assertArrayEquals(new Object[]{}, (Object[])RESPDecoder.decode("*0\r\n"));
    }

    @Test
    public void simpleArray() throws Exception {
        assertArrayEquals(new Object[] {"foo", "bar"},
                (Object[])RESPDecoder.decode("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
    }

    @Test
    public void mixedArray() throws Exception {
        assertArrayEquals(new Object[] {"1", "2", "3", "4", "foobar"},
                (Object[])RESPDecoder.decode("*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n"));
    }

    @Test
    public void nullArray() throws Exception {
        assertNull(RESPDecoder.decode("*-1\r\n"));
    }

    @Test
    public void nullInArray() throws Exception {
        assertArrayEquals(new Object[] {"foo", null, "bar"},
                (Object[])RESPDecoder.decode("*3\r\n$3\r\nfoo\r\n$-1\r\n$3\r\nbar\r\n"));
    }

    @Test
    public void arrayOfArray() throws Exception {
        assertArrayEquals(new Object[] {new Object[] {"1", "2", "3"}, new Object[]{"Foo", "Bar"}},
                (Object[])RESPDecoder.decode("*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n+Bar\r\n"));
    }
}
