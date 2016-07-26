package com.polenta.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PolentaConnection {

	int port;
	private Socket socket;
	private boolean connected;
	
	PolentaConnection(int port) {
		try {
			socket = new Socket("localhost", port);
			socket.setKeepAlive(true);
			connected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	
	public String writeToSocket(String statement) {
		BufferedWriter writer;
		BufferedReader reader;
		
		if (connected) {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				writer.write(statement);
				writer.newLine();
				writer.flush();
				System.out.println("Statement sent.");
				
				String response = reader.readLine();
				System.out.println("Statement result:\n" + response);
				
				return response;
			} catch (IOException e) {
				e.printStackTrace();
				return "IO_EXCEPTION";
			}
		} else {
			System.out.println("Not connected.");
			return "NOT_CONNECTED";
		}
		
	}

	public boolean isConnected() {
		return this.connected;
	}
}
