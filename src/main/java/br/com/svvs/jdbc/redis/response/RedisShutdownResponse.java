package br.com.svvs.jdbc.redis.response;

import java.sql.ResultSet;

public class RedisShutdownResponse implements RedisResponse {

    public static final RedisResponse INSTANCE = new RedisShutdownResponse();

    private RedisShutdownResponse() {

    }

    @Override
    public ResultSet processResponse(final String command, final Object response) {
        return null;
    }
}
