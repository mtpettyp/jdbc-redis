package br.com.svvs.jdbc.redis;

public interface RedisMessageHandler {
	public abstract String  createMsg(String msg) throws RedisParseException;
	public abstract String[] parseMsg(String msg);
}
