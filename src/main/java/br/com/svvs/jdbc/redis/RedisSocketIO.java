package br.com.svvs.jdbc.redis;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import br.com.svvs.jdbc.redis.response.RedisInputStream;

public class RedisSocketIO implements RedisIO {
	
	private Socket socket = null;
	private OutputStreamWriter outputStreamWriter = null;
	private RedisInputStream inputStream = null;
	private String host = null;
	private int port = 0;
	
	public RedisSocketIO(String host, int port) throws UnknownHostException, IOException {	
		this.host = host;
		this.port = port;
		init();
	}

	private void init() throws UnknownHostException, IOException {
		System.out.println("Connecting to socket "+host+":"+port);
		this.socket = new Socket(host, port);
		this.outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream());
		this.inputStream = new RedisInputStream(socket.getInputStream());
	}
	
	public Object sendRaw(String command) throws IOException, RedisResultException {
		Object decode = null;

		this.outputStreamWriter.write(command.toCharArray());
		this.outputStreamWriter.flush();
		decode = RESPDecoder.decode(inputStream);

		return decode;
	}
	
	public void reconnect() throws UnknownHostException, IOException {
		try {
			System.out.println("Closing buffers and redis connection");
			close();
		} 
		catch(IOException e) {
			System.out.println("Could not close RedisSocketIO");
		}
		init();
	}
	
	@Override
	public void close() throws IOException {
		if(socket != null)
		{	
			this.socket.close();
			this.socket = null;
		}
	}
}
