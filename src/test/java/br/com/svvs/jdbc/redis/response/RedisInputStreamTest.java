package br.com.svvs.jdbc.redis.response;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RedisInputStreamTest {

    @Test
    public void readByte(){
        String testData = "line1word1 line1word2\n line2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            for (int i=0;i<testData.length(); i++){
                assertEquals(testData.getBytes()[i], ris.readByte());
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readLine(){
        String testData = "line1word1 line1word2\r\nline2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            String line1 = ris.readLine();
            String line2 = ris.readLine();
            assertEquals("line1word1 line1word2", line1);
            assertEquals("line2word1", line2);
            assertTrue(ris.available()==0);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readLineBytes(){
        String testData = "line1word1 line1word2\r\nline2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            byte[] lineBytes1 = ris.readLineBytes();
            byte[] lineBytes2 = ris.readLineBytes();
            assertEquals("line1word1 line1word2", new String(lineBytes1));
            assertEquals("line2word1", new String(lineBytes2));
            assertTrue(ris.available()==0);
        } catch (IOException e) {
            fail("Unexpected IO Exception");
        }
    }

    @Test
    public void readLineBytesWithSlowData(){
        try {
            final PipedOutputStream source = new PipedOutputStream();
            PipedInputStream sink = new PipedInputStream(source);
            RedisInputStream ris = new RedisInputStream(sink);

            Thread sourceThread = new Thread(new Runnable() {
                static final String INPUT = "line1word1 line1word2\r\nline2word1\r\n";
                public static final int DELAY_MILLIS = 50;

                private int position = 0;
                @Override
                public void run() {
                    try {
                        while (position < INPUT.length()) {
                            Thread.sleep(DELAY_MILLIS);
                            source.write(INPUT.charAt(position));
                            position++;
                        }
                    } catch (Exception e) {
                        fail("Unexpected exception while writing to piped input stream");
                    }

                }
            });
            sourceThread.start();

            byte[] line1 = ris.readLineBytes();
            assertEquals("line1word1 line1word2", new String(line1));

            byte[] line2 = ris.readLineBytes();
            assertEquals("line2word1", new String(line2));
        } catch (IOException io) {
            fail("Unexpected IO Exception");
        }
    }

    @Test
    public void readIntCRLF(){
        String testData = "12345\r\n-301\r\n1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            assertEquals(12345, ris.readIntCrLf());
            assertEquals(-301, ris.readIntCrLf());
            assertEquals(1, ris.readIntCrLf());
        } catch (IOException e) {
            fail("Unexpected IO Exception");
        }
    }

    private RedisInputStream getRedisInputStream(String data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        return new RedisInputStream(bais);
    }

}