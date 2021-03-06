package com.hurynovich.mus_site.controller.command.impl.page.review;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsReadPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("reviews.read");

	private final IReviewService reviewService = ServiceFactory.getInstance().getReviewService();
	
	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException {
		String reviewIdParam = request.getParameter("reviewId");
		int reviewId = Integer.valueOf(reviewIdParam);
		String reviewLang = request.getSession().getAttribute("lang").toString();
		Review review = reviewService.getReviewById(reviewLang, reviewId);
		request.setAttribute("review", review);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
