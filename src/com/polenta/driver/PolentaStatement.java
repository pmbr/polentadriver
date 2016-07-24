package com.polenta.driver;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class PolentaStatement {

	private PolentaConnection connection;
	
	PolentaStatement(PolentaConnection connection) {
		this.connection = connection;
	}
	
	public boolean execute(String statement) throws Exception {
		if (connection.isConnected()) {
			@SuppressWarnings("unused")
			BufferedReader reader = connection.writeToSocket(statement);
			return true;
		} else {
			throw new Exception("Not connected");
		}
	}
	
	public List<Map<String, Object>> executeQuery(String statement) throws Exception {
		throw new Exception("Not implemented");
	}

	
	
}
