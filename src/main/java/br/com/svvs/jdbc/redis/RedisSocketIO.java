package br.com.svvs.jdbc.redis;

import br.com.svvs.jdbc.redis.response.RedisInputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RedisSocketIO implements RedisIO {
	
	private Socket socket = null;
	private OutputStreamWriter outputStreamWriter = null;
	private RedisInputStream inputStream = null;
	
	public RedisSocketIO(String host, int port) throws UnknownHostException, IOException {	
		
		this.socket = new Socket(host, port);
		this.outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream());
		this.inputStream = new RedisInputStream(socket.getInputStream());
	}

	public Object sendRaw(String command) throws IOException, RedisResultException {
		this.outputStreamWriter.write(command.toCharArray());
		this.outputStreamWriter.flush();
		return RESPDecoder.decode(inputStream);
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

}
