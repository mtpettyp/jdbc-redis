package br.com.svvs.jdbc.redis;

import java.io.IOException;

public interface RedisIO {
	
	Object sendRaw(String command) throws IOException, RedisResultException;

	void close() throws IOException;
}
