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
		if(s.length < 1)
			throw new RedisParseException("Missing parameter.");
		
		// expected bytes
		long bytes;
		
		// when there is no value attached,
		// this is still valid.
		if(s.length == 0) {
			bytes = 0L;
			return command + " " + s[0] + " " + bytes + terminator + terminator;		
		}
		else {
			bytes = s[1].length();
			return command + " " + s[0] + " " + bytes + terminator + s[1] + terminator;		
		}
		
	}

}
