package com.hurynovich.mus_site.dao.review;

import java.time.LocalDate;
import java.util.List;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.exception.ReviewDAOException;

public interface IReviewDAO {
	// Create methods
	boolean addReview(String reviewLang, String title, String text, LocalDate date, int userId, 
		String hashtags) throws ReviewDAOException;
	
	// Read methods
	Review getReviewById(String reviewLang, int id) throws ReviewDAOException;
	
	List<Review> getReviewsByLimit(String reviewLang, int start, int count) 
		throws ReviewDAOException;
	
	List<Review> getReviewsByHashtagsByLimit(String reviewLang, String hashtags, int start, 
		int count) throws ReviewDAOException;
	
	List<String> getPopularHashtags(String reviewLang) throws ReviewDAOException;
	
	int getReviewsNumber();
	
	// Update methods
	boolean editReview(String reviewLang, int id, String title, String text, 
		String hashtags) throws ReviewDAOException;
	
	// Delete methods
	boolean deleteReview(String reviewLang, int id) throws ReviewDAOException;
}
