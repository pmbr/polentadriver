package com.polenta.driver;

public class PolentaConnectionBuilder {
	
	private PolentaConnectionBuilder() {
		
	}
	
	public static PolentaConnection openConnection(int port) {
		return new PolentaConnection(port);
	}

}
