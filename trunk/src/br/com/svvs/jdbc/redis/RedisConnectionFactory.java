package br.com.svvs.jdbc.redis;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

public class RedisConnectionFactory {

	public static Connection getConnection(String host, int port,
			int dbnb) throws SQLException {
		
		//TODO: Add support for others RedisIOs
		RedisIO io;
		try {
			io = new RedisSocketIO(host, port);
		} catch (UnknownHostException e) {
			throw new SQLException("Can't find host: " + host);
		} catch (IOException e) {
			throw new SQLException("Couldn't connect ("+ e.getMessage() + ")");
		}
		
		Connection conn = new RedisConnection(io);		
		return conn;
	}

}
