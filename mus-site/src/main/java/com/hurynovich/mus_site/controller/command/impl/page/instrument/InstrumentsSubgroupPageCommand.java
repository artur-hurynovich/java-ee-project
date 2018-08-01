package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.instrument.InstrumentsSubgroup;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsSubgroupPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("instruments.subgroup");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String pageNumberParam = request.getParameter("page_number");
		int pageNumber;
		if (pageNumberParam == null) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.valueOf(pageNumberParam);
		}
		request.setAttribute("pageNumber", pageNumber);
		
		String subgroupIdParam = request.getParameter("subgroupId");
		int subgroupId = Integer.valueOf(subgroupIdParam);
		
		InstrumentsSubgroup subgroup = instrumentService.getSubgroupById(subgroupId);
		request.setAttribute("subgroup", subgroup);
		
		List<Instrument> instruments = instrumentService.getInstruments(subgroupId, pageNumber);
		request.setAttribute("instruments", instruments);
		
		int instrumentsNumber = instrumentService.getInstrumentsNumber();
		request.setAttribute("instrumentsNumber", instrumentsNumber);
		
		int pagesNumber = instrumentService.getPagesNumber();
		request.setAttribute("pagesNumber", pagesNumber);
		
		List<InstrumentsGroup> groups = instrumentService.getGroups();
		request.setAttribute("groups", groups);
		
		List<InstrumentsField> fields = instrumentService.getFieldsBySubgroupId(subgroupId);
		request.setAttribute("fields", fields);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
