package com.hurynovich.mus_site.service.review;

import java.time.LocalDate;
import java.util.List;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.dao.DAOFactory;
import com.hurynovich.mus_site.dao.review.IReviewDAO;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.util.HashtagsHandler;
import com.hurynovich.mus_site.util.paginator.IPaginator;
import com.hurynovich.mus_site.util.paginator.PaginatorFactory;

public class ReviewServiceImpl implements IReviewService {
	private final IReviewDAO reviewDao = DAOFactory.getInstance().getReviewDao();
	
	private final IPaginator paginator = PaginatorFactory.getInstance().getReviewsPaginator();
	
	private int reviewsNumber;
	private int pagesNumber;

	// Create methods
	@Override
	public boolean addReview(String reviewLang, String title, String text, LocalDate date, int userId, 
		String hashtags) throws ReviewDAOException {
		String cleanHashtags = HashtagsHandler.execute(hashtags);
		return reviewDao.addReview(reviewLang, title, text, date, userId, cleanHashtags);
	}

	// Read methods
	@Override
	public Review getReviewById(String reviewLang, int id) throws ReviewDAOException {
		return reviewDao.getReviewById(reviewLang, id);
	}

	@Override
	public List<Review> getReviews(String reviewLang, int pageNumber) throws ReviewDAOException {
		int start = paginator.getStart(pageNumber);
		int count = paginator.getCount();
		List<Review> reviews = reviewDao.getReviewsByLimit(reviewLang, start, count);
		reviewsNumber = reviewDao.getReviewsNumber();
		pagesNumber = paginator.getPagesNumber(reviewsNumber);
		
		return reviews;
	}
	
	@Override
	public List<Review> getReviewsByHashtags(String reviewLang, int pageNumber, String hashtags) 
		throws ReviewDAOException {
		int start = paginator.getStart(pageNumber);
		int count = paginator.getCount();
		List<Review> reviews = 
			reviewDao.getReviewsByHashtagsByLimit(reviewLang, hashtags, start, count);
		reviewsNumber = reviewDao.getReviewsNumber();
		pagesNumber = paginator.getPagesNumber(reviewsNumber);
		
		return reviews;
	}
	
	@Override
	public List<String> getPopularHashtags(String reviewLang) throws ReviewDAOException {
		return reviewDao.getPopularHashtags(reviewLang);
	}

	@Override
	public int getReviewsNumber() {
		return reviewsNumber;
	}

	@Override
	public int getPagesNumber() {
		return pagesNumber;
	}
	
	// Update methods
	@Override
	public boolean editReview(String reviewLang, int id, String title, String text, String hashtags) 
		throws ReviewDAOException {
		String cleanHashtags = HashtagsHandler.execute(hashtags);
		
		return reviewDao.editReview(reviewLang, id, title, text, cleanHashtags);
	}

	// Delete methods
	@Override
	public boolean deleteReview(String reviewLang, int id) throws ReviewDAOException {
		return reviewDao.deleteReview(reviewLang, id);
	}
}
