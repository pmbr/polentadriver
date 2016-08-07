package com.polenta.driver;

public class PolentaStatement {

	private PolentaConnection connection;
	
	PolentaStatement(PolentaConnection connection) {
		this.connection = connection;
	}
	
	public void execute(String statement) throws PolentaException {
		if (connection.isConnected()) {
			String serverResponse = connection.writeToSocket(statement);
			if (serverResponse.toUpperCase().startsWith("ERROR|")) {
				throw new PolentaException(serverResponse.substring(6));
			} else if (!serverResponse.toUpperCase().startsWith("OK|")) {
				throw new PolentaException("Received unexpected response from server.");
			}
		} else {
			throw new PolentaException("Connection to server has been closed. Statement cannot be executed"); 
		}
	}

	public PolentaResultSet executeQuery(String statement) throws PolentaException {
		if (connection.isConnected()) {
			String serverResponse = connection.writeToSocket(statement);
			if (serverResponse.toUpperCase().startsWith("ERROR|")) {
				throw new PolentaException(serverResponse.substring(6));
			} else if (serverResponse.toUpperCase().startsWith("OK|")) {
				return new PolentaResultSet(serverResponse.substring(3));
			} else {
				throw new PolentaException("Received unexpected response from server.");
			}
		} else {
			throw new PolentaException("Connection to server has been closed. Statement cannot be executed"); 
		}
	}

}
