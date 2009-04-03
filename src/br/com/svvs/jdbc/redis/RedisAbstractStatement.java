package br.com.svvs.jdbc.redis;

public abstract class RedisAbstractStatement {
	
	protected RedisCommandWrapper extractCommand(final String sql) throws RedisParseException {

		String[] sql_splt = sql.trim().split(" ",2);

		try {
			RedisProtocol cmd = RedisProtocol.valueOf(RedisProtocol.class, sql_splt[0].toUpperCase());
			return new RedisCommandWrapper(cmd, sql_splt.length == 2 ? sql_splt[1] : "");
		} catch(IllegalArgumentException e) {
			throw new RedisParseException("Command not recognized.");
		}
	}

	class RedisCommandWrapper {
		RedisProtocol cmd;
		String value;

		RedisCommandWrapper(RedisProtocol cmd, String value) {
			this.cmd   = cmd;
			this.value = value;
		}
	}

}
