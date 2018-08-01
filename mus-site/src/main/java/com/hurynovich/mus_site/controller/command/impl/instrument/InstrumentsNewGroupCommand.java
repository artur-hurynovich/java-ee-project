package com.hurynovich.mus_site.controller.command.impl.instrument;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsNewGroupCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String instrumentsNewGroupPage = 
		pathsBundle.getString("redirect.instruments.new.group");
	private final String instrumentsNewGroupSuccessPage = 
		pathsBundle.getString("redirect.instruments.new.group.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IInstrumentService instrumentService = 
			ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String groupNameEn = request.getParameter("group_name_en");
		String groupNameRu = request.getParameter("group_name_ru");
		if (instrumentService.groupExists("en", groupNameEn) || 
			instrumentService.groupExists("ru", groupNameRu)) {
			request.setAttribute("groupNameValid", false);
			return instrumentsNewGroupPage;
		} else if (instrumentService.addGroup(groupNameEn, groupNameRu)){
			return instrumentsNewGroupSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
}
