package com.hurynovich.mus_site.controller.command.impl.review;

import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsNewCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String reviewsNewSuccessPage = 
		pathsBundle.getString("redirect.reviews");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IReviewService reviewService = ServiceFactory.getInstance().getReviewService();

	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException {
		String reviewLang = request.getParameter("reviewLang");
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		LocalDate date = LocalDate.now();
		User reviewAuthor = (User) request.getSession().getAttribute("user");
		int reviewAuthorId = reviewAuthor.getId();
		String hashtags = request.getParameter("hashtags");
		
		if (reviewService.addReview(reviewLang, title, text, date, reviewAuthorId, hashtags)) {
			return reviewsNewSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
