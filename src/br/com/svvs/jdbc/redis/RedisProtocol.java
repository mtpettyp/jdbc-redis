package br.com.svvs.jdbc.redis;

public enum RedisProtocol implements RedisMessageHandler {
	
	SET(new RedisDigester("SET")){
		@Override
		public String createMsg(String msg) throws RedisParseException {
			return this.digester.createBulkCommand(msg);
		}

		@Override
		public String[] parseMsg(String msg) {
			// TODO Auto-generated method stub
			return null;
		}

	},
	GET(new RedisDigester("GET")){
		@Override
		public String createMsg(String msg) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] parseMsg(String msg) {
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
