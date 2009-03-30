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
	
	/**
	 * Use this for commands that have one or more parameters but we don't have to declare a size afterwards, 
	 * like <i>GET key</i>,<i>MGET key1 key2 key3</i>.
	 * @param msg
	 * @return
	 * @throws RedisParseException
	 */
	public String createSimpleCommand(final String msg) throws RedisParseException {
		return command + " " + msg + terminator;
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
		return new String[]{this.removeTerminator(msg)};
	}

	private String[] parseInteger(final String message) {
		return new String[]{this.removeTerminator(message)};
	}

	private String[] passeMultiBulkData(final String message) throws RedisResultException {
		
		String[] r = message.split(terminator);
		
		String[] result;
		if(r.length > 1) {
			result = new String[Integer.parseInt(r[0])];
		} else if (r.length == 1){
			return new String[]{null};
		} else {
			throw new RedisResultException("Server replied with invalid multi bulk data size.");
		}
		
		long bytes = 0;
		int resultIndex = 0;
		for(int i = 1; i < r.length; i++) {
			bytes = Long.parseLong(r[i].substring(1));
			if(bytes == -1L) {
				result[resultIndex] = null; // empty value
			} else {
				result[resultIndex] = r[i + 1];
				i++;
			}
			resultIndex++;
		}
		
		return result;
	}

	private String[] parseBulkData(final String message) throws RedisResultException {
		
		String[] r = message.split(terminator,2);
		
		if(r.length == 1 || Integer.parseInt(r[0]) == -1) {
			return new String[]{null}; // key not found, null result.
		} else if (r.length == 2) {
			return new String[]{this.removeTerminator(r[1])}; // key found
		} else {
			throw new RedisResultException("Problem trying to parse bulk reply.");
		}
	}
	
	private String removeTerminator(final String s) {
		return s.substring(0,s.length() - 2);
	}

	public String createBulkWithParamCommand(String msg, int i) throws RedisParseException {
		
		String[] s = msg.trim().split(" ",i - 1);
		
		if(s.length < i)
			throw new RedisParseException("Missing parameter.");
		
		// last parameter is the value, we need to know the length
		long bytes = s[s.length - 1].length();
		
		StringBuilder sb = new StringBuilder();
		sb.append(command + " ");
		
		// loop for all parameters
		for(int j = 0; j < s.length - 1; j++ ) {
			sb.append(s[j] + " ");
		}
		
		sb.append(bytes + terminator + s[s.length - 1] + terminator);
		
		return sb.toString();
	}

}
