package br.com.svvs.jdbc.redis;

import java.io.IOException;
import java.net.UnknownHostException;

public interface RedisIO {
	
	Object sendRaw(String command) throws IOException, RedisResultException;

	void close() throws IOException;
	
	void reconnect() throws UnknownHostException, IOException;
}
