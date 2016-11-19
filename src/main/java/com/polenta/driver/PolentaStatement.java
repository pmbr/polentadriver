package com.polenta.driver;

import java.util.Map;

public class PolentaStatement {

	private PolentaConnection connection;
	
	PolentaStatement(PolentaConnection connection) {
		this.connection = connection;
	}
	
	//TODO merge logic of execute and executeQuery to avoid duplicate code
	
	public void execute(String statement) throws PolentaException {
		if (connection.isConnected()) {
			Map<String, Object> serverResponse = connection.writeToSocket(statement);
			if (!serverResponse.containsKey("STATUS")) {
				throw new PolentaException("Received no status from server.");
			} else if (serverResponse.get("STATUS").equals("ERROR")) {
				if (!serverResponse.containsKey("ERROR_MESSAGE")) {
					throw new PolentaException("Received empty error message from server.");
				} else {
					throw new PolentaException((String)serverResponse.get("ERROR_MESSAGE"));
				}
			} else if (!serverResponse.get("STATUS").equals("SUCCESS")) {
				throw new PolentaException("Received unexpected status from server. Status: " + serverResponse.get("STATUS"));
			}
		} else {
			throw new PolentaException("Connection to server has been closed. Statement cannot be executed"); 
		}
	}

	public PolentaResultSet executeQuery(String statement) throws PolentaException {
		if (connection.isConnected()) {
			Map<String, Object> serverResponse = connection.writeToSocket(statement);
			if (!serverResponse.containsKey("STATUS")) {
				throw new PolentaException("Received no status from server.");
			} else if (serverResponse.get("STATUS").equals("ERROR")) {
				if (!serverResponse.containsKey("ERROR_MESSAGE")) {
					throw new PolentaException("Received empty error message from server.");
				} else {
					throw new PolentaException((String)serverResponse.get("ERROR_MESSAGE"));
				}
			} else if (!serverResponse.get("STATUS").equals("SUCCESS")) {
				throw new PolentaException("Received unexpected status from server.");
			} else {
				if (!serverResponse.containsKey("RESULT_SET")) {
					throw new PolentaException("Received no result set from server.");
				} else {
					return new PolentaResultSet((String)serverResponse.get("RESULT_SET"));
				}
			}
		} else {
			throw new PolentaException("Connection to server has been closed. Statement cannot be executed"); 
		}
	}

}
