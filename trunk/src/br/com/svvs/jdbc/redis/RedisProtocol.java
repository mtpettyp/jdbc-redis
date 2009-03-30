package br.com.svvs.jdbc.redis;

public enum RedisProtocol implements RedisMessageHandler {
	
	SET(new RedisDigester("SET")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	GET(new RedisDigester("GET")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	MGET(new RedisDigester("MGET")) {
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
		
	},
	SETNX(new RedisDigester("SETNX")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
		
	},
	INCR(new RedisDigester("INCR")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	INCRBY(new RedisDigester("INCRBY")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	DECR(new RedisDigester("DECR")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	DECRBY(new RedisDigester("DECRBY")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	EXISTS(new RedisDigester("EXISTS")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	DEL(new RedisDigester("DEL")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	TYPE(new RedisDigester("TYPE")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);		
		}
	},
	KEYS(new RedisDigester("KEYS")) {
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
	RANDOMKEY(new RedisDigester("RANDOMKEY")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	RENAME(new RedisDigester("RENAME")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	RENAMENX(new RedisDigester("RENAMENX")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	DBSIZE(new RedisDigester("DBSIZE")) {

		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	RPUSH(new RedisDigester("RPUSH")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LPUSH(new RedisDigester("LPUSH")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LLEN(new RedisDigester("LLEN")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LRANGE(new RedisDigester("LRANGE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LTRIM(new RedisDigester("LTRIM")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LINDEX(new RedisDigester("LINDEX")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LSET(new RedisDigester("LSET")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkWithParamCommand(msg,1);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LREM(new RedisDigester("LREM")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkWithParamCommand(msg,1);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	LPOP(new RedisDigester("LPOP")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	RPOP(new RedisDigester("RPOP")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}

	},
	SADD(new RedisDigester("SADD")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SREM(new RedisDigester("SREM")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SCARD(new RedisDigester("SCARD")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SISMEMBER(new RedisDigester("SISMEMBER")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SINTER(new RedisDigester("SINTER")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SMEMBERS(new RedisDigester("SMEMBERS")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SINTERSTORE(new RedisDigester("SINTERSTORE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SELECT(new RedisDigester("SELECT")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	MOVE(new RedisDigester("MOVE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	FLUSHDB(new RedisDigester("FLUSHDB")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	FLUSHALL(new RedisDigester("FLUSHDB")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SORT(new RedisDigester("SORT")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SAVE(new RedisDigester("SAVE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	BGSAVE(new RedisDigester("BGSAVE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	LASTSAVE(new RedisDigester("LASTSAVE")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	SHUTDOWN(new RedisDigester("SHUTDOWN")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	INFO(new RedisDigester("INFO")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			return this.digester.parseResultMessage(msg);
		}
	},
	QUIT(new RedisDigester("QUIT")) {
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSimpleCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) throws RedisResultException {
			// TODO Auto-generated method stub
			return null;
		}
		
	};

	// message digester
	protected final RedisDigester digester;	
	RedisProtocol(RedisDigester d) {
		this.digester = d;
	}
}
