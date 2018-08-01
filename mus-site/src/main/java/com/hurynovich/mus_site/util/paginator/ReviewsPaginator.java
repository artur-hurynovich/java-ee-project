package com.hurynovich.mus_site.util.paginator;

import java.util.ResourceBundle;

import com.hurynovich.mus_site.util.BundleFactory;

public class ReviewsPaginator implements IPaginator {
	private final ResourceBundle configBundle = BundleFactory.getInstance().getConfigBundle();
	private final int reviewsPerPage;

	public ReviewsPaginator() {
		String reviewsPerPageString = configBundle.getString("config.reviews.per.page");
		reviewsPerPage = Integer.valueOf(reviewsPerPageString);
	}
	
	@Override
	public int getStart(int pageNumber) {
		return (pageNumber - 1) * reviewsPerPage;
	}

	@Override
	public int getCount() {
		return reviewsPerPage;
	}

	@Override
	public int getPagesNumber(int itemsNumber) {
		int pagesNumber;
		if (itemsNumber % reviewsPerPage == 0) {
			pagesNumber = itemsNumber / reviewsPerPage;
		} else {
			pagesNumber = itemsNumber / reviewsPerPage + 1;
		}
		
		return pagesNumber;
	}
}
