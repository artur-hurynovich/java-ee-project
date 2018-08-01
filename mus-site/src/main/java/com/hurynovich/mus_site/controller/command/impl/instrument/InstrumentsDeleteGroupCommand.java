package com.hurynovich.mus_site.controller.command.impl.instrument;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsDeleteGroupCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String deleteSuccessPage = 
		pathsBundle.getString("redirect.instruments.delete.group.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String groupIdParam = request.getParameter("groupId");
		int groupId = Integer.valueOf(groupIdParam);
		
		if (instrumentService.deleteGroup(groupId)) {
			return deleteSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
