package com.hurynovich.mus_site.controller.filter;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.util.BundleFactory;

public class AuthFilter implements Filter {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("access.denied");
	private final String REAL_PATH = pathsBundle.getString("auths");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String command = request.getParameter("command");
		String xmlPath = request.getServletContext().getRealPath(REAL_PATH);
		AuthMapping mapping;
		try {
			mapping = AuthMapping.getInstance(xmlPath);
			List<String> filteredUrls = mapping.getAllUrls();
			if (command != null && filteredUrls.contains(command)) {
				User user = (User) httpRequest.getSession().getAttribute("user");
				if (checkUserAuth(user, mapping, command)) {
					chain.doFilter(httpRequest, response);
				} else {
					httpRequest.getRequestDispatcher(page).forward(httpRequest, response);
				}
			} else {
				chain.doFilter(httpRequest, response);
			}
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkUserAuth(User user, AuthMapping mapping, String command) {
		if (user == null) {
			return false;
		} else {
			UserRole role = user.getRole();
			List<UserRole> authRoles = mapping.getRolesForUrl(command);
			if (authRoles.contains(role)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
