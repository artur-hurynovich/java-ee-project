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

public class InstrumentsNewSubgroupContinuePageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String instrumentsNewSubgroupPage = 
		pathsBundle.getString("instruments.new_subgroup");
	private final String instrumentsNewSubgroupContinuePage = 
		pathsBundle.getString("instruments.new_subgroup_continue");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String subgroupNameEn = request.getParameter("subgroup_name_en");
		String subgroupNameRu = request.getParameter("subgroup_name_ru");
		
		if (instrumentService.subgroupExists("en", subgroupNameEn) || 
			instrumentService.subgroupExists("ru", subgroupNameRu)) {
			request.setAttribute("subgroupNameValid", false);
			List<InstrumentsGroup> groups = instrumentService.getGroups();
			request.setAttribute("groups", groups);
			return instrumentsNewSubgroupPage;
		} else {
			String groupIdParam = request.getParameter("groupId");
			int groupId = Integer.valueOf(groupIdParam);
			request.setAttribute("groupId", groupId);
			request.setAttribute("subgroupNameEn", subgroupNameEn);
			request.setAttribute("subgroupNameRu", subgroupNameRu);
			String fieldsNumberParam = request.getParameter("fieldsNumber");
			int fieldsNumber = Integer.valueOf(fieldsNumberParam);
			request.setAttribute("fieldsNumber", fieldsNumber);
			return instrumentsNewSubgroupContinuePage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
