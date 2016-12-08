package com.polenta.driver;

import java.util.List;
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

	@SuppressWarnings("unchecked")
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
				if (!serverResponse.containsKey("FIELDS") && !serverResponse.containsKey("ROWS")) {
					throw new PolentaException("Received no result set from server.");
				} else {
					return new PolentaResultSet((List<String>)serverResponse.get("FIELDS"), (List<Map<String, Object>>)serverResponse.get("ROWS"));
				}
			}
		} else {
			throw new PolentaException("Connection to server has been closed. Statement cannot be executed"); 
		}
	}

}
