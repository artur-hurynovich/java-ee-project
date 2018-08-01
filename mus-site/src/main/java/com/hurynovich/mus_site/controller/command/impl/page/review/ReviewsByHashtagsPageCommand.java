package com.hurynovich.mus_site.controller.command.impl.page.review;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;
import com.hurynovich.mus_site.util.HashtagsHandler;

public class ReviewsByHashtagsPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("reviews.by_hashtags");
	
	private final IReviewService reviewService = ServiceFactory.getInstance().getReviewService();

	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException {
		String pageNumberParam = request.getParameter("page_number");
		int pageNumber;
		if (pageNumberParam == null) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.valueOf(pageNumberParam);
		}
		request.setAttribute("pageNumber", pageNumber);
		
		String hashtagsParam = request.getParameter("hashtags");
		if (!hashtagsParam.isEmpty()) {
			String hashtags = HashtagsHandler.execute(hashtagsParam);
			String hashtagsQuery = Arrays.toString(hashtags.split(" ")).replaceAll("\\[|\\]", "");
			request.setAttribute("hashtagsQuery", hashtagsQuery);
			
			String reviewLang = request.getSession().getAttribute("lang").toString();
			List<Review> reviews = reviewService.getReviewsByHashtags(reviewLang, pageNumber, hashtags);
			request.setAttribute("reviews", reviews);
			
			List<String> popularHashtags = reviewService.getPopularHashtags(reviewLang);
			request.setAttribute("popularHashtags", popularHashtags);
			
			int reviewsNumber = reviewService.getReviewsNumber();
			request.setAttribute("reviewsNumber", reviewsNumber);
			
			int pagesNumber = reviewService.getPagesNumber();
			request.setAttribute("pagesNumber", pagesNumber);
		}

		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
