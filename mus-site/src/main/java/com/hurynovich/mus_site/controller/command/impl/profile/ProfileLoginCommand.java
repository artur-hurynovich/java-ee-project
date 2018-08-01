package com.hurynovich.mus_site.controller.command.impl.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.user.IUserService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ProfileLoginCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private String loginPage = pathsBundle.getString("profile.login");
	private final String loginSuccessPage = pathsBundle.getString("redirect.reviews");
	
	private IUserService userService = ServiceFactory.getInstance().getUserService();

	private boolean validEmail;
	private boolean validPassword;
	
	@Override
	public String execute(HttpServletRequest request) throws UserDAOException {
		validEmail = true;
		validPassword = true;
		
		String email = request.getParameter("email");
		if (!userService.emailExists(email)) {
			validEmail = false;
			request.setAttribute("validEmail", validEmail);
			return loginPage;
		} else {
			String password = request.getParameter("password");
			User user = userService.getUserByEmailAndPassword(email, password);
			if (user == null) {
				request.setAttribute("email", email);
				validPassword = false;
				request.setAttribute("validPassword", validPassword);
				return loginPage;
			} else {
				request.getSession().setAttribute("user", user);
				return loginSuccessPage;
			}
		}
	}
	
	@Override
	public boolean isRedirect() {
		if (validEmail && validPassword) {
			return true;
		} else {
			return false;
		}
	}
}
