package com.hurynovich.mus_site.dao.review;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.util.BundleFactory;
import com.hurynovich.mus_site.util.ConnectionFactory;

public class ReviewDAOMySQLImpl implements IReviewDAO {
	private int reviewsNumber;
	
	private final ResourceBundle bundle = BundleFactory.getInstance().getConfigBundle();

	// Create methods
	@Override
	public boolean addReview(String reviewLang, String title, String text, LocalDate date,
		int userId, String hashtags) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews";
		try {
			st = conn.prepareStatement("INSERT INTO " + tableName + 
				" (review_title, review_text, review_date, user_id) VALUES (?, ?, ?, ?);", 
				Statement.RETURN_GENERATED_KEYS);
			st.setString(1, title);
			st.setString(2, text);
			Date reviewDate = Date.valueOf(date);
			st.setDate(3, reviewDate);
			st.setInt(4, userId);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				res = st.getGeneratedKeys();
				res.next();
				int reviewId = res.getInt(1);
				return addHashtags(reviewLang, reviewId, hashtags);
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method addReview, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "title = " + title + ", "
				+ "text = " + text + ", "
				+ "date = " + date + ", "
				+ "userId = " + userId + ", "
				+ "hashtags = " + hashtags;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Read methods
	@Override
	public Review getReviewById(String reviewLang, int id) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews";
		try {
			st = conn.prepareStatement("SELECT * FROM " + tableName + 
				" JOIN users USING (user_id) WHERE review_id = ?;");
			st.setInt(1, id);
			res = st.executeQuery();
			if (res.next()) {
				Review review = new Review();
				review.setId(id);
				String title = res.getString("review_title");
				review.setTitle(title);
				String text = res.getString("review_text");
				review.setText(text);
				LocalDate date = res.getDate("review_date", 
					Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())).toLocalDate();
				review.setDate(date);
				String userName = res.getString("user_name");
				review.setUserName(userName);
				String hashtags = getHashtags(reviewLang, id);
				review.setHashtags(hashtags);
				return review;
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getReviewById, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "id = " + id ;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	@Override
	public List<Review> getReviewsByLimit(String reviewLang, int start, int count) 
		throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews";
		try {
			st = conn.prepareStatement("SELECT COUNT(*) FROM " + tableName + ";");
			res = st.executeQuery();
			if (res.next()) {
				reviewsNumber = res.getInt(1);
			} else {
				reviewsNumber = 0;
			}
			res.close();
			st.close();
			st = conn.prepareStatement("SELECT * FROM " + tableName + " JOIN users USING (user_id) "
					+ "ORDER BY review_date DESC LIMIT ?,?;");
			st.setInt(1, start);
			st.setInt(2, count);
			res = st.executeQuery();
			return resultSetToList(res);
		} catch (SQLException e) {
			String excMessage = "SQLException in method getReviewsByLimit, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "start = " + start + ", "
				+ "count = " + count;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public List<Review> getReviewsByHashtagsByLimit(String reviewLang, String hashtags, 
		int start, int count) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableRevName = reviewLang + "_reviews";
		String tableRevTagsName = reviewLang + "_reviews_hashtags";
		String tableTagsName = reviewLang + "_hashtags";
		String[] searchHashtags = hashtags.split(" ");
		List<Review> reviews = new LinkedList<>();
		try {
			for (String searchHashtag : searchHashtags) {
				st = conn.prepareStatement("SELECT * FROM " + tableRevName 
					+ " JOIN users USING (user_id) "
					+ "JOIN " + tableRevTagsName + " USING (review_id) "
					+ "JOIN " + tableTagsName + " USING (hashtag_id) WHERE hashtag_name = ?;");
				st.setString(1, searchHashtag);
				res = st.executeQuery();
				List<Review> addReviews = resultSetToList(res);
				if (addReviews != null) {
					addReviews.forEach(aa -> {
						if (reviews.contains(aa)) {
							reviews.forEach(a -> {
								if (a.equals(aa)) {
									a.incrementRelevance();
								}
							});
						}
						else {
							reviews.add(aa);
						}
					});
				}
			}
			reviewsNumber = reviews.size();
			if (reviews.isEmpty()) {
				return null;
			} else {
				Comparator<Review> comp = Comparator.comparingInt(Review::getRelevance)
					.thenComparing(Comparator.comparing(Review::getDate)).reversed();
				reviews.sort(comp);
				List<Review> limitReviews = new LinkedList<>();
				for (int i = start; (i < (start + count)) && (i < reviews.size()); i++) {
					limitReviews.add(reviews.get(i));
				}
				return limitReviews;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getReviewsByHashtagsByLimit, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "hashtags = " + hashtags + ", "
				+ "start = " + start + ", "
				+ "count = " + count;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public List<String> getPopularHashtags(String reviewLang) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableRevTagsName = reviewLang + "_reviews_hashtags";
		String tableTagsName = reviewLang + "_hashtags";
		String hashtagsLimitString = bundle.getString("config.popular.hashtags.number");
		int hashtagsLimit = Integer.valueOf(hashtagsLimitString);
		try {
			st = conn.prepareStatement("SELECT hashtag_name, COUNT(*) c "
				+ "FROM " + tableRevTagsName + " JOIN " + tableTagsName + " USING (hashtag_id) "
				+ "GROUP BY hashtag_id HAVING c > 0 ORDER BY c DESC LIMIT 0, " + hashtagsLimit + ";");
			res = st.executeQuery();
			List<String> popularHashtags = resultSetToStringList(res);
			return popularHashtags;
		} catch (SQLException e) {
			String excMessage = "SQLException in method getPopularHashtags, called "
				+ "with parameter reviewLang = " + reviewLang;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public int getReviewsNumber() {
		return reviewsNumber;
	}
	
	// Update methods
	@Override
	public boolean editReview(String reviewLang, int id, String title, String text, 
		String hashtags) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews";
		try {
			st = conn.prepareStatement("UPDATE " + tableName + " SET review_title = ?, review_text = ? "
				+ "WHERE review_id = ?;");
			st.setString(1, title);
			st.setString(2, text);
			st.setInt(3, id);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				return (deleteHashtags(reviewLang, id) && addHashtags(reviewLang, id, hashtags));
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method editReview, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "id = " + id + ", "
				+ "title = " + title + ", "
				+ "text = " + text + ", "
				+ "hashtags = " + hashtags;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Delete methods
	@Override
	public boolean deleteReview(String reviewLang, int id) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews";
		try {
			st = conn.prepareStatement("DELETE FROM " + tableName + " WHERE review_id = ?;");
			st.setInt(1, id);
			int affRows = st.executeUpdate();
			return (affRows != 0);
		} catch (SQLException e) {
			String excMessage = "SQLException in method editReview, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "id = " + id;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	// Secondary methods
	private List<Review> resultSetToList(ResultSet res) throws ReviewDAOException {
		List<Review> reviews = new LinkedList<>();
		try {
			while (res.next()) {
				Review review = new Review();
				int id = res.getInt("review_id");
				review.setId(id);
				String title = res.getString("review_title");
				review.setTitle(title);
				String text = res.getString("review_text");
				review.setText(text);
				LocalDate date = res.getDate("review_date", 
					Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())).toLocalDate();
				review.setDate(date);
				String userName = res.getString("user_name");
				review.setUserName(userName);
				reviews.add(review);
			}
			if (reviews.size() == 0) {
				return null;
			} else {
				return reviews;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method resultSetToList";
			throw new ReviewDAOException(excMessage, e);
		}
	}
	
	private List<String> resultSetToStringList(ResultSet res) throws ReviewDAOException {
		List<String> popularHashtags = new LinkedList<>();
		try {
			while (res.next()) {
				String popularHashtag = res.getString("hashtag_name");
				popularHashtags.add(popularHashtag);
			}
			if (popularHashtags.size() == 0) {
				return null;
			} else {
				return popularHashtags;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method resultSetToStringList";
			throw new ReviewDAOException(excMessage, e);
		}
	}
	
	private boolean addHashtags(String reviewLang, int reviewId, String hashtags) 
		throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String[] hashtagsArray = hashtags.split(" ");
		try {
			for (String hashtag : hashtagsArray) {
				String tableName = reviewLang + "_hashtags";
				st = conn.prepareStatement("SELECT hashtag_id FROM " + tableName 
					+ " WHERE hashtag_name = ?;");
				st.setString(1, hashtag);
				res = st.executeQuery();
				int affRows;
				if (res.next()) {
					int hashtagId = res.getInt(1);
					tableName = reviewLang + "_reviews_hashtags";
					res.close();
					st.close();
					st = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?);", 
						Statement.RETURN_GENERATED_KEYS);
					st.setInt(1, reviewId);
					st.setInt(2, hashtagId);
					affRows = st.executeUpdate();
					if (affRows == 0) {
						return false;
					}
				} else {
					tableName = reviewLang + "_hashtags";
					res.close();
					st.close();
					st = conn.prepareStatement("INSERT INTO " + tableName 
						+ " (hashtag_name) VALUES (?);", 
						Statement.RETURN_GENERATED_KEYS);
					st.setString(1, hashtag);
					affRows = st.executeUpdate();
					if (affRows == 0) {
						return false;
					} else {
						st = conn.prepareStatement("SELECT hashtag_id FROM " + tableName
							+ " WHERE hashtag_name = ?;");
						st.setString(1, hashtag);
						res = st.executeQuery();
						if (res.next()) {
							int hashtagId = res.getInt(1);
							tableName = reviewLang + "_reviews_hashtags";
							st = conn.prepareStatement("INSERT INTO " + tableName 
								+ " VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
							st.setInt(1, reviewId);
							st.setInt(2, hashtagId);
							affRows = st.executeUpdate();
							st.close();
							if (affRows == 0) {
								return false;
							}
						}
					}
				}
			}
			return true;
		} catch (SQLException e) {
			String excMessage = "SQLException in method addHashtags, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "reviewId = " + reviewId + ", "
				+ "hashtags = " + hashtags;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	private String getHashtags(String reviewLang, int reviewId) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableRevTagsName = reviewLang + "_reviews_hashtags";
		String tableTagsName = reviewLang + "_hashtags";
		try {
			st = conn.prepareStatement("SELECT hashtag_name FROM " + tableRevTagsName + 
				" JOIN " + tableTagsName + " USING (hashtag_id) WHERE review_id = ?;");
			st.setInt(1, reviewId);
			res = st.executeQuery();
			StringBuilder h = new StringBuilder();
			while (res.next()) {
				String hashtag = res.getString("hashtag_name");
				h.append(hashtag + " ");
			}
			return h.toString();
		} catch (SQLException e) {
			String excMessage = "SQLException in method getHashtags, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "reviewId = " + reviewId;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private boolean deleteHashtags(String reviewLang, int id) throws ReviewDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		String tableName = reviewLang + "_reviews_hashtags";
		try {
			st = conn.prepareStatement("DELETE FROM " + tableName + " WHERE review_id = ?;");
			st.setInt(1, id);
			int affRows = st.executeUpdate();
			return (affRows != 0);
		} catch (SQLException e) {
			String excMessage = "SQLException in method deleteHashtags, called "
				+ "with parameters reviewLang = " + reviewLang + ", "
				+ "id = " + id;
			throw new ReviewDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private void close(Connection conn, PreparedStatement st, ResultSet res) 
		throws ReviewDAOException {
		try {
			if (res != null) {
				res.close();
				res = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method close";
			throw new ReviewDAOException(excMessage, e);
		}
	}
}
