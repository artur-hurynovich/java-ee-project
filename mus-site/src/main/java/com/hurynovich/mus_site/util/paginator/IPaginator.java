package com.hurynovich.mus_site.util.paginator;

public interface IPaginator {
	int getStart(int pageNumber);
	
	int getCount();
	
	int getPagesNumber(int itemsNumber);
}
