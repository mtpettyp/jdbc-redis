package br.com.svvs.jdbc.redis.response;

import java.sql.ResultSet;

import br.com.svvs.jdbc.redis.RedisConnection;

public interface RedisResponse {
    ResultSet processResponse(RedisConnection connection, String command, Object response);
}
