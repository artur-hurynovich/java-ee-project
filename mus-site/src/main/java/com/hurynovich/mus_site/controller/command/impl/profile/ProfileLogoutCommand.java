package com.hurynovich.mus_site.controller.command.impl.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class ProfileLogoutCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("redirect.reviews");

	@Override
	public String execute(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
