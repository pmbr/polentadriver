package com.polenta.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public boolean executeStatement(String command) {
		if (connected) {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(command);
				out.flush();
				System.out.println("Statement sent.");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
		
	}
	
}
