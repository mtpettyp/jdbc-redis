package br.com.svvs.jdbc.redis.response;

import java.sql.ResultSet;

public interface RedisResponse {
    ResultSet processResponse(String command, Object response);
}
