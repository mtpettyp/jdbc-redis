package br.com.svvs.jdbc.redis;

public class RedisDigester {

	private final String command;
	private final String terminator = "\r\n";
	
	public RedisDigester(String cmd) {
		if(cmd == null)
			throw new RuntimeException("Can't create a digester for a null command name.");
		this.command = cmd;
	}
	
	public String createBulkCommand(final String msg) throws RedisParseException {
		String[] s = msg.trim().split(" ",2);
		
		if(s.length == 0)
			throw new RedisParseException("Missing parameter.");
		
		// expected bytes
		long bytes;
		
		// when there is no value attached,
		// this is still valid.
		if(s.length == 1) {
			bytes = 0L;
			return command + " " + s[0] + " " + bytes + terminator + terminator;		
		}
		else {
			bytes = s[1].length();
			return command + " " + s[0] + " " + bytes + terminator + s[1] + terminator;		
		}
		
	}
	
	public String createSimpleCommand(final String msg) throws RedisParseException {
		return command + " " + msg + terminator;
	}
	
	public String createSingleCommand() throws RedisParseException {
		return command + " " + terminator;
	}
	
	public String[] parseResultMessage(final String msg) throws RedisResultException {
		
		// message without the type character.
		String message = msg.substring(1);
		
		RedisReply replyType = RedisReply.value(msg.charAt(0));
		
		switch(replyType) {
			case ERROR:
				throw new RedisResultException(message.substring(0,message.length() - 2));
			case SINGLE_LINE:
				return this.parseSingleLineReply(message);
			case BULK_DATA:
				return this.parseBulkData(message);
			case MULTI_BULK_DATA:
				return this.passeMultiBulkData(message);
			case INTEGER:
				return this.parseInteger(message);
			default:
				throw new RedisResultException("Don't know how to parse reply: " + replyType);
		}
	}

	private String[] parseSingleLineReply(final String msg) throws RedisResultException {
		return new String[]{msg.substring(0, msg.length() - 2)};
	}

	private String[] parseInteger(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	private String[] passeMultiBulkData(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	private String[] parseBulkData(String message) {
		// TODO Auto-generated method stub
		return null;
	}

}
