package com.hurynovich.mus_site.controller.command.impl.instrument;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.FieldValue;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsNewCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String instrumentsNewSuccessPage = 
		pathsBundle.getString("redirect.instruments.new.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();
	
	private final String FIELD_EXISTING = "field_existing_";
	private final String FIELD_NEW = "field_new_";

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String subgroupIdParam = request.getParameter("subgroupId");
		int subgroupId = Integer.valueOf(subgroupIdParam);
		
		Map<String, String[]> params = request.getParameterMap();
		List<InstrumentsField> fields = extractFieldsFromParameters(params);
		
		if (instrumentService.addInstrument(subgroupId, fields)) {
			return instrumentsNewSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
	
	private List<InstrumentsField> extractFieldsFromParameters(Map<String, String[]> params) {
		Set<InstrumentsField> fields = new HashSet<>();
		params.keySet().forEach(paramName -> {
			if (paramName.contains(FIELD_EXISTING)) {
				InstrumentsField field = new InstrumentsField();
				String fieldIdParam = paramName.replaceAll("\\D", "");
				int fieldId = Integer.valueOf(fieldIdParam);
				field.setId(fieldId);
				String valueIdParam = params.get(paramName)[0];
				int valueId = Integer.valueOf(valueIdParam);
				FieldValue value = new FieldValue();
				value.setId(valueId);
				field.setCurrentValue(value);
				fields.add(field);
			}
			if (paramName.contains(FIELD_NEW)) {
				InstrumentsField field = new InstrumentsField();
				String fieldIdParam = paramName.replaceAll("\\D", "");
				int fieldId = Integer.valueOf(fieldIdParam);
				field.setId(fieldId);
				FieldValue value = new FieldValue();
				String valueNameEnKey = FIELD_NEW + fieldId + "_en";
				String valueNameEn = params.get(valueNameEnKey)[0];
				value.setNameEn(valueNameEn);
				String valueNameRuKey = FIELD_NEW + fieldId + "_ru";
				String valueNameRu = params.get(valueNameRuKey)[0];
				value.setNameRu(valueNameRu);
				field.setCurrentValue(value);;
				fields.add(field);
			}
		});
		
		return new LinkedList<>(fields);
	}
}
