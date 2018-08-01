package com.hurynovich.mus_site.controller.command.impl.profile;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.user.IUserService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ProfileChangeRoleCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String profileChangeRoleSuccessPage = 
		pathsBundle.getString("redirect.profile.change.role.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private IUserService userService = ServiceFactory.getInstance().getUserService();

	@Override
	public String execute(HttpServletRequest request) throws UserDAOException {
		String newRoleParam = request.getParameter("newRole");
		UserRole newRole = UserRole.valueOf(newRoleParam);
		
		String userIdParam = request.getParameter("userId");
		int userId = Integer.valueOf(userIdParam);
		
		if (userService.changeRole(userId, newRole)) {
			return profileChangeRoleSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
