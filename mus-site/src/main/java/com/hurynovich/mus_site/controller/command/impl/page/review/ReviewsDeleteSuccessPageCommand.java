package com.hurynovich.mus_site.controller.command.impl.page.review;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsDeleteSuccessPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private String page = pathsBundle.getString("reviews.delete.success");
	
	@Override
	public String execute(HttpServletRequest request) {
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
