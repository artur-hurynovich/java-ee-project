package com.hurynovich.mus_site.util.paginator;

import java.util.ResourceBundle;

import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsPaginator implements IPaginator {
	private final ResourceBundle configBundle = BundleFactory.getInstance().getConfigBundle();
	private final int instrumentsPerPage;
	
	public InstrumentsPaginator() {
		String instrumentsPerPageString = configBundle.getString("config.instruments.per.page");
		instrumentsPerPage = Integer.valueOf(instrumentsPerPageString);
	}

	@Override
	public int getStart(int pageNumber) {
		return (pageNumber - 1) * instrumentsPerPage;
	}

	@Override
	public int getCount() {
		return instrumentsPerPage;
	}

	@Override
	public int getPagesNumber(int itemsNumber) {
		int pagesNumbe;
		if (itemsNumber % instrumentsPerPage == 0) {
			pagesNumbe = itemsNumber / instrumentsPerPage;
		} else {
			pagesNumbe = itemsNumber / instrumentsPerPage + 1;
		}
		
		return pagesNumbe;
	}
}
