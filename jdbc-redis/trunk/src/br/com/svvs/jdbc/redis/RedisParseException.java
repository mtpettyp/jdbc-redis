package br.com.svvs.jdbc.redis;

public class RedisParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RedisParseException() {}
	public RedisParseException(String msg) {
		super(msg);
	}

}
