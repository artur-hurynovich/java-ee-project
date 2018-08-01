package com.hurynovich.mus_site.exception;

public class ReviewDAOException extends Exception {
	private static final long serialVersionUID = -8504622125391461928L;
	
	public ReviewDAOException() {
		super();
	}
	public ReviewDAOException(String message) {
		super(message);
	}
	
	public ReviewDAOException(Exception exc) {
		super(exc);
	}

	public ReviewDAOException(String message, Exception exc) {
		super(message, exc);
	}
}
