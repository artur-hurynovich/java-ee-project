package com.hurynovich.mus_site.util.paginator;

public class PaginatorFactory {
	private final static PaginatorFactory INSTANCE = new PaginatorFactory();
	
	private final IPaginator reviewsPaginator;
	private final IPaginator instrumentsPaginator;
	
	private PaginatorFactory() {
		reviewsPaginator = new ReviewsPaginator();
		instrumentsPaginator = new InstrumentsPaginator();
	}
	
	public static PaginatorFactory getInstance() {
		return INSTANCE;
	}
	
	public IPaginator getReviewsPaginator() {
		return reviewsPaginator;
	}
	
	public IPaginator getInstrumentsPaginator() {
		return instrumentsPaginator;
	}
}
