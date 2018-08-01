package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsNewSubgroupPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("instruments.new_subgroup");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		List<InstrumentsGroup> groups = instrumentService.getGroups();
		request.setAttribute("groups", groups);
		
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
