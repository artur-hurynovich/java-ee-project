package com.hurynovich.mus_site.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hurynovich.mus_site.controller.command.CommandsFactory;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.util.BundleFactory;

public class Controller extends HttpServlet{
	private static final long serialVersionUID = -8913362050742009565L;
	
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String errorPageForward = pathsBundle.getString("error");
	private final String errorPageRedirect = pathsBundle.getString("redirect.error");
	private String page;
	
	private Logger log;
	
	@Override
	public void init() {
        String projectDir = getServletContext().getRealPath("/");
        System.setProperty("projectDir", projectDir);
        log = LogManager.getRootLogger();
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		ICommand command = CommandsFactory.defineCommand(request);
		
		try {
			page = command.execute(request);
		} catch (UserDAOException|ReviewDAOException|InstrumentDAOException e) {
			log.error(e.getMessage(), e);
			if (command.isRedirect()) {
				page = errorPageRedirect;
			} else {
				page = errorPageForward;
			}
		}
		
		if (command.isRedirect()) {
			response.sendRedirect(request.getContextPath() + page);
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
	}
}
