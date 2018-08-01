package com.hurynovich.mus_site.exception;

public class InstrumentDAOException extends Exception {
	private static final long serialVersionUID = -8504622125391461928L;
	
	public InstrumentDAOException() {
		super();
	}
	public InstrumentDAOException(String message) {
		super(message);
	}
	
	public InstrumentDAOException(Exception exc) {
		super(exc);
	}

	public InstrumentDAOException(String message, Exception exc) {
		super(message, exc);
	}
}
