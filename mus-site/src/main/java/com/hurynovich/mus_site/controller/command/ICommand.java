package com.hurynovich.mus_site.controller.command;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.exception.UserDAOException;

public interface ICommand {
	String execute(HttpServletRequest request) throws UserDAOException, ReviewDAOException, 
		InstrumentDAOException;
	
	boolean isRedirect();
}
