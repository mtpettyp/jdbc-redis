package br.com.svvs.jdbc.redis;

public interface RedisDigester {

	public abstract String createBulkCommand(final String msg)
			throws RedisParseException;

	/**
	 * Use this for commands that have one or more parameters but we don't have to declare a size afterwards, 
	 * like <i>GET key</i>,<i>MGET key1 key2 key3</i>.
	 * @param msg
	 * @return
	 * @throws RedisParseException
	 */
	public abstract String createSimpleCommand(final String msg)
			throws RedisParseException;

	public abstract String[] parseResultMessage(final String msg)
			throws RedisResultException;

	public abstract String createBulkCommand(String msg, int i)
			throws RedisParseException;

}