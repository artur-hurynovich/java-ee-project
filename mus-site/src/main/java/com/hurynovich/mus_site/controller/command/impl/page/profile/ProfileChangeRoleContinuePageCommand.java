package com.hurynovich.mus_site.controller.command.impl.page.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.user.IUserService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ProfileChangeRoleContinuePageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String profileChangeRoleContinuePage = 
		pathsBundle.getString("profile.change_role_continue");
	private final String profileChangeRolePage = pathsBundle.getString("profile.change_role");
	
	private final IUserService userService = 
		ServiceFactory.getInstance().getUserService();

	@Override
	public String execute(HttpServletRequest request) throws UserDAOException {
		String userEmail = request.getParameter("email").toString();
		User user = userService.getUserByEmail(userEmail);
		if (user != null) {
			request.setAttribute("user", user);
			return profileChangeRoleContinuePage;
		} else {
			request.setAttribute("validEmail", false);
			return profileChangeRolePage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
