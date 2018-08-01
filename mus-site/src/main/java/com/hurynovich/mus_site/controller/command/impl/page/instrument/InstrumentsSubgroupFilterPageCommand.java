package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.FieldValue;
import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.instrument.InstrumentsSubgroup;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsSubgroupFilterPageCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsBundle.getString("instruments.subgroup_filter");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		List<InstrumentsField> filterFields;
		Map<String, String[]> params = request.getParameterMap();
		
		String pageNumberParam = request.getParameter("page_number");
		int pageNumber;
		if (pageNumberParam == null) {
			pageNumber = 1;
			filterFields = extractFieldsFromParameters(params);
			request.getSession().setAttribute("filterFields", filterFields);
		} else {
			pageNumber = Integer.valueOf(pageNumberParam);
			filterFields = (List<InstrumentsField>) request.getSession().getAttribute("filterFields");
		}
		request.setAttribute("pageNumber", pageNumber);
	
		String subgroupIdParam = request.getParameter("subgroupId");
		int subgroupId = Integer.valueOf(subgroupIdParam);
		
		InstrumentsSubgroup subgroup = instrumentService.getSubgroupById(subgroupId);
		request.setAttribute("subgroup", subgroup);
		
		List<Instrument> instruments = null;
		if (filterFields != null && filterFields.size() != 0) {
			instruments = 
				instrumentService.getInstrumentsByFilter(subgroupId, filterFields, pageNumber);
			request.getSession().setAttribute("filterFields", filterFields);
		} else {
			instruments = instrumentService.getInstruments(subgroupId, pageNumber);
		}
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
	
	private List<InstrumentsField> extractFieldsFromParameters(Map<String, String[]> params) {
		List<InstrumentsField> fields = new LinkedList<>();;
		for (String paramName : params.keySet()) {
			if (paramName.contains("field_") && !params.get(paramName)[0].equals("empty")) {
				InstrumentsField field = new InstrumentsField();
				String fieldIdParam = paramName.replaceAll("\\D", "");
				int fieldId = Integer.valueOf(fieldIdParam);
				field.setId(fieldId);
				String valueIdParam = params.get(paramName)[0].replaceAll("\\D", "");
				int valueId = Integer.valueOf(valueIdParam);
				FieldValue currentValue = new FieldValue();
				currentValue.setId(valueId);
				field.setCurrentValue(currentValue);
				fields.add(field);
			}
		}
		return fields;
	}
}
