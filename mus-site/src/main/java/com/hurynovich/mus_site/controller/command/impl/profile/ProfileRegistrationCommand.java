package com.hurynovich.mus_site.controller.command.impl.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.user.IUserService;
import com.hurynovich.mus_site.util.BundleFactory;
import com.hurynovich.mus_site.util.EmailSender;

public class ProfileRegistrationCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String profileRegistrationPage = 
		pathsBundle.getString("profile.registration");
	private final String profileRegistrationSuccessPage = 
		pathsBundle.getString("redirect.profile.registration.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private IUserService userService = ServiceFactory.getInstance().getUserService();
	
	private final EmailSender emailSender = EmailSender.getInstance();
	
	private boolean validEmail;
	private boolean validPassword;
	
	@Override
	public String execute(HttpServletRequest request) throws UserDAOException {
		validEmail = true;
		validPassword = true;
		String email = request.getParameter("email");
		if (userService.emailExists(email)) {
			validEmail = false;
		}
		
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		if (!password.equals(confirmPassword)) {
			validPassword = false;
			if (validEmail) {
				request.setAttribute("email", email);
			}
		}
		
		String name = request.getParameter("name");
		UserRole role = UserRole.USER;
		String phone = request.getParameter("phone");
		
		if (!validEmail || !validPassword) {
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			request.setAttribute("validEmail", validEmail);
			request.setAttribute("validPassword", validPassword);
			return profileRegistrationPage;
		} else if (userService.registration(name, email, password, role, phone)) {
			String emailLang = request.getSession().getAttribute("lang").toString();
			emailSender.send(emailLang, name, email, password);
			return profileRegistrationSuccessPage;
		} else {
			return errorPage;
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
