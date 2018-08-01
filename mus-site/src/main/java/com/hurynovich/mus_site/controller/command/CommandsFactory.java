package com.hurynovich.mus_site.controller.command;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.util.BundleFactory;

public class CommandsFactory {
	private final static ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final static String REAL_PATH = pathsBundle.getString("commands");
	
	public static ICommand defineCommand(HttpServletRequest request) {
		String action = request.getParameter("command");
		String xmlPath = request.getServletContext().getRealPath(REAL_PATH);
		CommandsMapping mapping = CommandsMapping.getInstance(xmlPath);
		ICommand command = mapping.getCommand(action);
		return command;
	}
}
