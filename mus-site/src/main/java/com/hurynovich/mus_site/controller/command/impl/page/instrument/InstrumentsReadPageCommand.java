package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsReadPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("instruments.read");
	
	private final IReviewService reviewsService = ServiceFactory.getInstance().getReviewService();
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();
	
	@Override
	public String execute(HttpServletRequest request) throws ReviewDAOException, 
		InstrumentDAOException {
		List<InstrumentsGroup> groups = instrumentService.getGroups();
		request.setAttribute("groups", groups);
		
		String instrumentIdParam = request.getParameter("instrumentId");
		int instrumentId = Integer.valueOf(instrumentIdParam);
		
		Instrument instrument = instrumentService.getInstrumentById(instrumentId);
		request.setAttribute("instrument", instrument);
		
		Object user = request.getSession().getAttribute("user");
		if (user != null) {
			int userId = ((User) user).getId();
			boolean rated = instrumentService.instrumentRatedByUser(instrumentId, userId);
			request.setAttribute("rated", rated);
		}
		
		String mark = null;
		String model = null;
		List<InstrumentsField> fields = instrument.getFields();
		for (InstrumentsField field : fields) {
			if (field.getNameEn().equals("Mark")) {
				mark = field.getCurrentValue().getNameEn();
			}
			if (field.getNameEn().equals("Model")) {
				model = field.getCurrentValue().getNameEn();
			}
		}
		
		String reviewLang = request.getSession().getAttribute("lang").toString();
		List<Review> reviews = reviewsService.getReviewsByHashtags(reviewLang, 1, mark + " " 
			+ model);
		request.setAttribute("reviews", reviews);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
