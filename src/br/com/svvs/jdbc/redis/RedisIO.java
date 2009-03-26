package br.com.svvs.jdbc.redis;

import java.io.IOException;

public interface RedisIO {
	
	String sendRaw(String command) throws IOException;
}
