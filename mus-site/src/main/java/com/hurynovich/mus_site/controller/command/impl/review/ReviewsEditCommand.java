package com.hurynovich.mus_site.controller.command.impl.review;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsEditCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String reviewsReadPage = pathsBundle.getString("redirect.reviews.edit.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IReviewService reviewService = ServiceFactory.getInstance().getReviewService();

	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException {
		String reviewLang = request.getSession().getAttribute("lang").toString();
		String reviewIdParam = request.getParameter("reviewId");
		int reviewId = Integer.valueOf(reviewIdParam);
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String hashtags = request.getParameter("hashtags");
		if (reviewService.editReview(reviewLang, reviewId, title, text, hashtags)) {
			Review review = reviewService.getReviewById(reviewLang, reviewId);
			request.setAttribute("review", review);
			return reviewsReadPage + reviewId;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
