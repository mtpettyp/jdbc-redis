package br.com.svvs.jdbc.redis;

public class RedisResultException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RedisResultException() {}
	public RedisResultException(String msg) {
		super(msg);
	}
}
