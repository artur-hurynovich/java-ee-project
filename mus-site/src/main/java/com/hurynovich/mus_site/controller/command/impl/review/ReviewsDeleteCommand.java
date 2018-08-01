package com.hurynovich.mus_site.controller.command.impl.review;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsDeleteCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String reviewsDeleteSuccessPage = 
		pathsBundle.getString("redirect.reviews.delete.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IReviewService reviewService = ServiceFactory.getInstance().getReviewService();

	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException {
		String reviewIdParam = request.getParameter("reviewId");
		int reviewId = Integer.valueOf(reviewIdParam);
		String reviewLang = request.getSession().getAttribute("lang").toString();
		if (reviewService.deleteReview(reviewLang, reviewId)) {
			return reviewsDeleteSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
