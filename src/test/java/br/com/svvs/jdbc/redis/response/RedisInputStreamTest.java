package br.com.svvs.jdbc.redis.response;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class RedisInputStreamTest {

    @Test
    public void readByte(){
        String testData = "line1word1 line1word2\n line2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            for (int i=0;i<testData.length(); i++){
                Assert.assertEquals(testData.getBytes()[i], ris.readByte());
            }
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void readLine(){
        String testData = "line1word1 line1word2\r\nline2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            String line1 = ris.readLine();
            String line2 = ris.readLine();
            Assert.assertEquals("line1word1 line1word2", line1);
            Assert.assertEquals("line2word1", line2);
            Assert.assertTrue(ris.available()==0);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void readLineBytes(){
        String testData = "line1word1 line1word2\r\nline2word1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            byte[] lineBytes1 = ris.readLineBytes();
            byte[] lineBytes2 = ris.readLineBytes();
            Assert.assertEquals("line1word1 line1word2", new String(lineBytes1));
            Assert.assertEquals("line2word1", new String(lineBytes2));
            Assert.assertTrue(ris.available()==0);
        } catch (IOException e) {
            Assert.fail();
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
                        e.printStackTrace();
                    }

                }
            });
            sourceThread.start();

            byte[] line1 = ris.readLineBytes();
            Assert.assertEquals("line1word1 line1word2", new String(line1));

            byte[] line2 = ris.readLineBytes();
            Assert.assertEquals("line2word1", new String(line2));


        } catch (IOException io) {

        }
    }

    @Test
    public void readIntCRLF(){
        String testData = "12345\r\n-301\r\n1\r\n";
        RedisInputStream ris = getRedisInputStream(testData);
        try {
            Assert.assertEquals(12345, ris.readIntCrLf());
            Assert.assertEquals(-301, ris.readIntCrLf());
            Assert.assertEquals(1, ris.readIntCrLf());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    private RedisInputStream getRedisInputStream(String data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        return new RedisInputStream(bais);
    }

}