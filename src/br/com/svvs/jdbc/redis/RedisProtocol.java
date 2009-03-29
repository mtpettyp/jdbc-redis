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
	QUIT(new RedisDigester("QUIT")) {
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createSingleCommand();
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
