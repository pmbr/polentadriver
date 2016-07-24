package com.polenta.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public BufferedReader writeToSocket(String statement) {
		if (connected) {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(statement);
				out.flush();
				System.out.println("Statement sent.");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
	}

	public boolean isConnected() {
		return this.connected;
	}
}
