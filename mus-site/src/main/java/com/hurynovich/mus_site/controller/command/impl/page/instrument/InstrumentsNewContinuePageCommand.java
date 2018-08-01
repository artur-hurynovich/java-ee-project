package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsNewContinuePageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("instruments.new_continue");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String subgroupIdParam = request.getParameter("subgroupId");
		int subgroupId = Integer.valueOf(subgroupIdParam);
		request.setAttribute("subgroupId", subgroupId);
		
		List<InstrumentsField> fields = instrumentService.getFieldsBySubgroupId(subgroupId);
		request.setAttribute("fields", fields);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
