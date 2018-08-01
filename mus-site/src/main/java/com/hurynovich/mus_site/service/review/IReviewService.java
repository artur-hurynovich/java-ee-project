package com.hurynovich.mus_site.service.review;

import java.time.LocalDate;
import java.util.List;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.exception.ReviewDAOException;

public interface IReviewService {
	// Create methods
	boolean addReview(String reviewLang, String title, String text, LocalDate date, 
		int userId, String hashtags) throws ReviewDAOException;
	
	// Read methods
	Review getReviewById(String reviewLang, int id) throws ReviewDAOException;
	
	List<Review> getReviews(String reviewLang, int pageNumber) throws ReviewDAOException;
	
	List<Review> getReviewsByHashtags(String reviewLang, int pageNumber, String hashtags) 
		throws ReviewDAOException;
	
	List<String> getPopularHashtags(String reviewLang) throws ReviewDAOException;
	
	int getReviewsNumber();
	
	int getPagesNumber();
	
	// Update methods
	boolean editReview(String reviewLang, int id, String title, String text, String hashtags) 
		throws ReviewDAOException;
	
	// Delete methods
	boolean deleteReview(String reviewLang, int id) throws ReviewDAOException;
}
