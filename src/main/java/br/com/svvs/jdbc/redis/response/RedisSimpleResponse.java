package br.com.svvs.jdbc.redis.response;

import java.sql.ResultSet;

import br.com.svvs.jdbc.redis.RedisConnection;
import br.com.svvs.jdbc.redis.RedisResultSet;

public class RedisSimpleResponse implements RedisResponse {

    public static final RedisResponse INSTANCE = new RedisSimpleResponse();

    private RedisSimpleResponse() {
    }

    @Override
    public ResultSet processResponse(final RedisConnection connection, final String command, final Object response) {
        return new RedisResultSet(new String[]{(String)response});
    }
}
