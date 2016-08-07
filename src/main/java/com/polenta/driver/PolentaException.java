package com.polenta.driver;

public class PolentaException extends Exception {

	private static final long serialVersionUID = -423250123428946978L;
	
	public PolentaException(String message) {
		super(message);
	}

	public PolentaException(String message, Throwable cause) {
		super(message, cause);
	}

}
