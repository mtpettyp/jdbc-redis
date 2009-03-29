package br.com.svvs.jdbc.redis;

public enum RedisReply {

	ERROR,
	SINGLE_LINE,
	BULK_DATA,
	MULTI_BULK_DATA,
	INTEGER;
	
	public static RedisReply value(char t) {
		switch(t) {
		case '-':
			return RedisReply.ERROR;
		case '+':
			return RedisReply.SINGLE_LINE;
		case '$':
			return RedisReply.BULK_DATA;
		case '*':
			return RedisReply.MULTI_BULK_DATA;
		case ':':
			return RedisReply.INTEGER;
		default:
			throw new RuntimeException("Unkown type reply (" + t + ")");
		}
	}
}
