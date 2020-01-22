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
	private String host = null;
	private int port = 0;
	private int maxRetries = 3;
	private int maxTimeout = 3000;
	
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
		int iCounter = 0;
		
		while(iCounter < maxRetries) {
			try {
				this.outputStreamWriter.write(command.toCharArray());
				this.outputStreamWriter.flush();
				decode = RESPDecoder.decode(inputStream);
				
				if(decode != null)
					break;
			}
			catch(Exception e) {
				System.out.println("Connection to redis is closed");
				try {
					reconnectSocket();
				}
				catch(Exception io) {
					System.out.println("Problem connecting to redis: "+io.getMessage());
				}
    			try {
    				Thread.sleep(maxTimeout);
    			}
    			catch(InterruptedException ie) {
    				System.out.println("Could not interrupt thread");
    			}
			}
			iCounter++;
		}
		
		if (iCounter == maxRetries) {
			throw new IOException("Could not connect to redis");
		}
		
		return decode;
	}
	
	public void reconnectSocket() throws UnknownHostException, IOException {
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
		if(inputStream != null)
		{
			this.inputStream.close();
			inputStream = null;
		}
		
		if(outputStreamWriter != null)
		{
			this.outputStreamWriter.close();
			outputStreamWriter = null;
		}
		
		if(socket != null)
		{
			this.socket.close();
			socket = null;
		}
	}
}
