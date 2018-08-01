package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsRateSuccessPageCommand implements ICommand {
	private final ResourceBundle pathsundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsundle.getString("redirect.instruments.rate.success");

	@Override
	public String execute(HttpServletRequest request) {
		String instrumentIdParam = request.getParameter("instrumentId");
		int instrumentId = Integer.valueOf(instrumentIdParam);
		return page + instrumentId;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
