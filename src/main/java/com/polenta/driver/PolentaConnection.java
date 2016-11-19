package com.polenta.driver;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class PolentaConnection {

	private String host;
	private int port;
	private Socket socket;
	private boolean connected;
	private BufferedWriter writer;
	private ObjectInputStream reader;
	
	PolentaConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void open() throws Exception {
		try {
			socket = new Socket(host, port);
			socket.setKeepAlive(true);
			connected = true;
			
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (java.net.ConnectException ce) {
			throw new PolentaException("It was not possible connect to Polenta server on host [" + this.host + "] and port [" + this.port + "].\n");
		} catch (UnknownHostException uhe) {
			throw new PolentaException("It was not possible connect to Polenta server on host [" + this.host + "] and port [" + this.port + "].\n");
		}
	}
	
	public PolentaStatement createStatement() {
		return new PolentaStatement(this);
	}
	
	//public void close()
	//public void commit()
	//public void rollback
	//public void createTransaction
	//public void setAutoCommit
	//public void getAutoCommit
	//public PolentaMetaData getMetaData
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> writeToSocket(String statement) throws PolentaException {
		
		if (this.connected) {
			Map<String, Object> response = null;
			try {
				
				writer.write(statement);
				writer.newLine();
				writer.flush();

				response = (Map<String, Object>)reader.readObject();
			} catch (Exception e) {
				throw new PolentaException("An error ocurred on communication to PolentaServer. See root cause for details.", e);
			}
			if (response == null) {
				 this.connected = false;
				 throw new PolentaException("Connection to server is no longer active.");
			}
			return response;
		} else {
			throw new PolentaException("Connection to server has been closed.");
		}
		
	}
	
	public void close() throws Exception {
		this.connected = false;
		socket.close();	
	}

	public boolean isConnected() {
		return this.connected;
	}
}
