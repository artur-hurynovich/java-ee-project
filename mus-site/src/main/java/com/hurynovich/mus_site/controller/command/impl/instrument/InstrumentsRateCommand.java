package com.hurynovich.mus_site.controller.command.impl.instrument;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsRateCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String instrumentsReadPage = 
		pathsBundle.getString("redirect.instruments.rate.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();
	
	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String instrumentIdParam = request.getParameter("instrumentId");
		int instrumentId = Integer.valueOf(instrumentIdParam);
		Object user = request.getSession().getAttribute("user");
		int userId = ((User) user).getId();
		String ratingValueParam = request.getParameter("ratingValue");
		int ratingValue = Integer.valueOf(ratingValueParam);
		if (instrumentService.addRating(instrumentId, userId, ratingValue)) {
			return instrumentsReadPage + instrumentId;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
