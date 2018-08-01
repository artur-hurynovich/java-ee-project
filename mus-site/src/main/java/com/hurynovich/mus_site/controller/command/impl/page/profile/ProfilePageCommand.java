package com.hurynovich.mus_site.controller.command.impl.page.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class ProfilePageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String profileFailedPage = pathsBundle.getString("profile.failed");
	private final String profilePage = pathsBundle.getString("profile");

	@Override
	public String execute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return profileFailedPage;
		} else {
			return profilePage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
