package br.com.svvs.jdbc.redis;

import java.sql.SQLException;

public abstract class RedisAbstractStatement {
	
	
	protected boolean isClosed = false;
	protected RedisConnection conn;
	protected RedisResultSet resultSet;
	protected String sql;
	
	protected RedisAbstractStatement(String sql, RedisConnection conn) {
		this.sql  = sql;
		this.conn = conn;
	}
	protected RedisAbstractStatement(RedisConnection conn) {
		this.conn = conn;
	}
	
	protected RedisCommandWrapper extractCommand(final String sql) throws RedisParseException {

		String[] sql_splt = sql.trim().split(" ",2);

		try {
			RedisProtocol cmd = RedisProtocol.valueOf(RedisProtocol.class, sql_splt[0].toUpperCase());
			return new RedisCommandWrapper(cmd, sql_splt.length == 2 ? sql_splt[1] : "");
		} catch(IllegalArgumentException e) {
			throw new RedisParseException("Command not recognized.");
		}
	}

	public boolean execute(String sql) throws SQLException {
		
		if(this.isClosed)
			throw new SQLException("This statement is closed.");
		
		try {
			RedisCommandWrapper wrapper = this.extractCommand(sql);
			
			String redisMsg = wrapper.cmd.createMsg(wrapper.value);
			
			String[] result = wrapper.cmd.parseMsg(this.conn.msgToServer(redisMsg));
			
			if(result != null)
				this.resultSet = new RedisResultSet(result);
			
			return true;
						
		} catch (RedisParseException e) {
			throw new SQLException(e);
		} catch (RedisResultException e) {
			throw new SQLException(e);
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
