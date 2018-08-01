package com.hurynovich.mus_site.service;

import com.hurynovich.mus_site.service.instrument.InstrumentServiceImpl;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.service.review.ReviewServiceImpl;
import com.hurynovich.mus_site.service.user.IUserService;
import com.hurynovich.mus_site.service.user.UserServiceImpl;

public final class ServiceFactory {
	private final static ServiceFactory INSTANCE = new ServiceFactory();
	
	private final IUserService userService;
	private final IReviewService reviewService;
	private final IInstrumentService instrumentService;
	
	private ServiceFactory() {
		userService = new UserServiceImpl();
		reviewService = new ReviewServiceImpl();
		instrumentService = new InstrumentServiceImpl();
	}
	
	public static ServiceFactory getInstance() {
		return INSTANCE;
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public IReviewService getReviewService() {
		return reviewService;
	}
	
	public IInstrumentService getInstrumentService() {
		return instrumentService;
	}
}
