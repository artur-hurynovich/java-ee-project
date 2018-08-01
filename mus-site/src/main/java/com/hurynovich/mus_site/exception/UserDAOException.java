package com.hurynovich.mus_site.exception;

public class UserDAOException extends Exception {
	private static final long serialVersionUID = -8504622125391461928L;
	
	public UserDAOException() {
		super();
	}
	public UserDAOException(String message) {
		super(message);
	}
	
	public UserDAOException(Exception exc) {
		super(exc);
	}

	public UserDAOException(String message, Exception exc) {
		super(message, exc);
	}
}
