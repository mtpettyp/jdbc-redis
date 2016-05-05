package br.com.svvs.jdbc.redis;

public enum RedisProtocol implements RedisMessageHandler {

    SET(new RedisSimpleDigester("SET")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    GET(new RedisSimpleDigester("GET")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    MGET(new RedisSimpleDigester("MGET")) {
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    SETNX(new RedisSimpleDigester("SETNX")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    INCR(new RedisSimpleDigester("INCR")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    INCRBY(new RedisSimpleDigester("INCRBY")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    DECR(new RedisSimpleDigester("DECR")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    DECRBY(new RedisSimpleDigester("DECRBY")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    EXISTS(new RedisSimpleDigester("EXISTS")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    DEL(new RedisSimpleDigester("DEL")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    TYPE(new RedisSimpleDigester("TYPE")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    KEYS(new RedisSimpleDigester("KEYS")) {
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            String[] r =  this.digester.parseResultMessage(msg);
            // keys is a bulk reply but should be a multi bulk reply.
            // we create a array of keys instead of a string of keys.
            if(r.length == 1) {
                return r[0].split(" ");
            } else {
                throw new RedisResultException("Error trying to convert KEYS command reply.");
            }
        }
    },
    RANDOMKEY(new RedisSimpleDigester("RANDOMKEY")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    RENAME(new RedisSimpleDigester("RENAME")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    RENAMENX(new RedisSimpleDigester("RENAMENX")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    DBSIZE(new RedisSimpleDigester("DBSIZE")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    RPUSH(new RedisSimpleDigester("RPUSH")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LPUSH(new RedisSimpleDigester("LPUSH")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LLEN(new RedisSimpleDigester("LLEN")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LRANGE(new RedisSimpleDigester("LRANGE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LTRIM(new RedisSimpleDigester("LTRIM")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LINDEX(new RedisSimpleDigester("LINDEX")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LSET(new RedisSimpleDigester("LSET")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg,1);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LREM(new RedisSimpleDigester("LREM")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg,1);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    LPOP(new RedisSimpleDigester("LPOP")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    RPOP(new RedisSimpleDigester("RPOP")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    SADD(new RedisSimpleDigester("SADD")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SREM(new RedisSimpleDigester("SREM")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SCARD(new RedisSimpleDigester("SCARD")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SISMEMBER(new RedisSimpleDigester("SISMEMBER")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createBulkCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SINTER(new RedisSimpleDigester("SINTER")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SMEMBERS(new RedisSimpleDigester("SMEMBERS")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SINTERSTORE(new RedisSimpleDigester("SINTERSTORE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SELECT(new RedisSimpleDigester("SELECT")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    MOVE(new RedisSimpleDigester("MOVE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    FLUSHDB(new RedisSimpleDigester("FLUSHDB")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    FLUSHALL(new RedisSimpleDigester("FLUSHDB")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SORT(new RedisSimpleDigester("SORT")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SAVE(new RedisSimpleDigester("SAVE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    BGSAVE(new RedisSimpleDigester("BGSAVE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    LASTSAVE(new RedisSimpleDigester("LASTSAVE")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    SHUTDOWN(new RedisSimpleDigester("SHUTDOWN")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return null;
        }
    },
    INFO(new RedisSimpleDigester("INFO")){
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    },
    QUIT(new RedisSimpleDigester("QUIT")) {
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return null;
        }
    },
    EXPIRE(new RedisSimpleDigester("EXPIRE")) {

        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }

    },
    AUTH(new RedisSimpleDigester("AUTH")) {
        @Override
        public String createMsg(String msg) throws RedisParseException {
            return this.digester.createSimpleCommand(msg);
        }

        @Override
        public String[] parseMsg(String msg) throws RedisResultException {
            return this.digester.parseResultMessage(msg);
        }
    };

    // message digester
    protected final RedisDigester digester;
    RedisProtocol(RedisDigester d) {
        this.digester = d;
    }

}
