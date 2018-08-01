package com.hurynovich.mus_site.controller.command.impl;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class UnknownCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("unknown.command");

	@Override
	public String execute(HttpServletRequest request) {
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
