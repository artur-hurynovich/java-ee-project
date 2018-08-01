package com.hurynovich.mus_site.dao;

import com.hurynovich.mus_site.dao.instrument.InstrumentDAOMySQLImpl;
import com.hurynovich.mus_site.dao.instrument.IInstrumentDAO;
import com.hurynovich.mus_site.dao.review.IReviewDAO;
import com.hurynovich.mus_site.dao.review.ReviewDAOMySQLImpl;
import com.hurynovich.mus_site.dao.user.IUserDAO;
import com.hurynovich.mus_site.dao.user.UserDAOMySQLImpl;

public final class DAOFactory {
	private final static DAOFactory INSTANCE = new DAOFactory();
	
	private final IUserDAO userDao;
	private final IReviewDAO serviceDao;
	private final IInstrumentDAO instrumentDao;
	
	private DAOFactory() {
		userDao = new UserDAOMySQLImpl();
		serviceDao = new ReviewDAOMySQLImpl();
		instrumentDao = new InstrumentDAOMySQLImpl();
	}
	
	public static DAOFactory getInstance() {
		return INSTANCE;
	}
	
	public IUserDAO getUserDao() {
		return userDao;
	}
	
	public IReviewDAO getReviewDao() {
		return serviceDao;
	}
	
	public IInstrumentDAO getInstrumentDao() {
		return instrumentDao;
	}
}
