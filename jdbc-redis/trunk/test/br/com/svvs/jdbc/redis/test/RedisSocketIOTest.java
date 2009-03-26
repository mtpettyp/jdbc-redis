package br.com.svvs.jdbc.redis.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import br.com.svvs.jdbc.redis.RedisProtocol;
import br.com.svvs.jdbc.redis.RedisSocketIO;

public class RedisSocketIOTest {

	@Test
	public void testRedisSocketIO() {
		try {
			RedisSocketIO io = new RedisSocketIO("localhost", 6379);
			
			String r = io.sendRaw("SET foo 3\r\nbar\r\n");
			System.out.println("respo: " + r);
			
		} catch (UnknownHostException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
