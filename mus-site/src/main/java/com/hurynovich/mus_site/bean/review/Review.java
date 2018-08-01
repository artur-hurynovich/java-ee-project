package com.hurynovich.mus_site.bean.review;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Review implements Serializable {
	private static final long serialVersionUID = 993491152105160292L;

	private int id;
	private String title;
	private String text;
	private LocalDate date;
	private int userId;
	private String userName;
	private String hashtags;
	private int relevance = 0;

	public Review() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitlePreview() {
		if (title != null) {
			if (title.length() >= 50) {
				return title.substring(0, 50) + "...";
			} else {
				return title;
			}
		} else {
			return null;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getTextPreview() {
		if (text != null) {
			if (text.length() >= 200) {
				return text.substring(0, 200) + " ...";
			} else {
				return text;
			}
		} else {
			return null;
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getDateView() {
		if (date != null) {
			return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		} else {
			return null;
		}
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}
	
	public String getHashtagsView() {
		if (hashtags != null) {
			return Arrays.toString(hashtags.split(" ")).replaceAll("\\[|\\]", "");
		} else {
			return null;
		}
	}

	public int getRelevance() {
		return relevance;
	}
	
	public void incrementRelevance() {
		relevance++;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		Review temp = (Review) obj;
		
		boolean titleEquals = false;
		if (title != null) {
			titleEquals = title.equals(temp.title);
		} else {
			titleEquals = (temp.title == null);
		}
		
		boolean textEquals = false;
		if (text != null) {
			textEquals = text.equals(temp.text);
		} else {
			textEquals = (temp.text == null);
		}
		
		boolean dateEquals = false;
		if (date != null) {
			dateEquals = date.equals(temp.date);
		} else {
			dateEquals = (temp.date == null);
		}
		
		boolean hashtagsEquals = false;
		if (hashtags != null) {
			hashtagsEquals = hashtags.equals(temp.hashtags);
		} else {
			hashtagsEquals = (temp.hashtags == null);
		}
		
		return (titleEquals && textEquals && dateEquals && hashtagsEquals);
	}
	
	@Override
	public int hashCode() {
		int res = 13;
		final int PRIME = 25;
		
		res = res * PRIME + id;
		
		if (title != null) {
			res = res * PRIME + title.hashCode();
		}
		
		if (text != null) {
			res = res * PRIME + text.hashCode();
		}
		
		if (date != null) {
			res = res * PRIME + date.hashCode();
		}
		
		res = res * PRIME + userId;
		
		if (hashtags != null) {
			res = res * PRIME + hashtags.hashCode();
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		return "ID: " + getId() + "\n" +
			"Title: " + getTitlePreview() + "\n" +
			"Text: " + getTextPreview() + "\n" +
			"Date: " + getDateView() + "\n" +
			"Author: " + getUserName() + "\n" +
			"Hashtags: " + getHashtags();
	}
}
