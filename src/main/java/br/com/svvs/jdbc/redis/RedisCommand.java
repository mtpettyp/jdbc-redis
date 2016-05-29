package br.com.svvs.jdbc.redis;

import br.com.svvs.jdbc.redis.response.RedisArrayResponse;
import br.com.svvs.jdbc.redis.response.RedisResponse;
import br.com.svvs.jdbc.redis.response.RedisShutdownResponse;
import br.com.svvs.jdbc.redis.response.RedisSimpleResponse;

public enum RedisCommand {

    APPEND(RedisSimpleResponse.INSTANCE),
    AUTH(RedisSimpleResponse.INSTANCE),
    BGSAVE(RedisSimpleResponse.INSTANCE),
    DBSIZE(RedisSimpleResponse.INSTANCE),
    DECR(RedisSimpleResponse.INSTANCE),
    DECRBY(RedisSimpleResponse.INSTANCE),
    DEL(RedisSimpleResponse.INSTANCE),
    EXISTS(RedisSimpleResponse.INSTANCE),
    EXPIRE(RedisSimpleResponse.INSTANCE),
    EXPIREAT(RedisSimpleResponse.INSTANCE),
    FLUSHALL(RedisSimpleResponse.INSTANCE),
    FLUSHDB(RedisSimpleResponse.INSTANCE),
    GET(RedisSimpleResponse.INSTANCE),
    GETSET(RedisSimpleResponse.INSTANCE),
    INCR(RedisSimpleResponse.INSTANCE),
    INCRBY(RedisSimpleResponse.INSTANCE),
    INFO(RedisSimpleResponse.INSTANCE),
    KEYS(RedisArrayResponse.INSTANCE),
    LASTSAVE(RedisSimpleResponse.INSTANCE),
    LINDEX(RedisSimpleResponse.INSTANCE),
    LLEN(RedisSimpleResponse.INSTANCE),
    LPOP(RedisSimpleResponse.INSTANCE),
    LPUSH(RedisSimpleResponse.INSTANCE),
    LRANGE(RedisArrayResponse.INSTANCE),
    LREM(RedisSimpleResponse.INSTANCE),
    LSET(RedisSimpleResponse.INSTANCE),
    LTRIM(RedisSimpleResponse.INSTANCE),
    MGET(RedisArrayResponse.INSTANCE),
    MOVE(RedisSimpleResponse.INSTANCE),
    MSET(RedisSimpleResponse.INSTANCE),
    PEXPIRE(RedisSimpleResponse.INSTANCE),
    PTTL(RedisSimpleResponse.INSTANCE),
    QUIT(RedisSimpleResponse.INSTANCE),
    RANDOMKEY(RedisSimpleResponse.INSTANCE),
    RENAME(RedisSimpleResponse.INSTANCE),
    RENAMENX(RedisSimpleResponse.INSTANCE),
    RPOP(RedisSimpleResponse.INSTANCE),
    RPUSH(RedisSimpleResponse.INSTANCE),
    SADD(RedisSimpleResponse.INSTANCE),
    SAVE(RedisSimpleResponse.INSTANCE),
    SCARD(RedisSimpleResponse.INSTANCE),
    SELECT(RedisSimpleResponse.INSTANCE),
    SET(RedisSimpleResponse.INSTANCE),
    SETNX(RedisSimpleResponse.INSTANCE),
    SHUTDOWN(RedisShutdownResponse.INSTANCE),
    SINTER(RedisSimpleResponse.INSTANCE),
    SINTERSTORE(RedisSimpleResponse.INSTANCE),
    SISMEMBER(RedisSimpleResponse.INSTANCE),
    SMEMBERS(RedisSimpleResponse.INSTANCE),
    SORT(RedisSimpleResponse.INSTANCE),
    SREM(RedisSimpleResponse.INSTANCE),
    TTL(RedisSimpleResponse.INSTANCE),
    TYPE(RedisSimpleResponse.INSTANCE);

    private final RedisResponse response;

    private RedisCommand(RedisResponse response) {
        this.response = response;
    }

    public RedisResponse getResponse() {
        return response;
    }
}
