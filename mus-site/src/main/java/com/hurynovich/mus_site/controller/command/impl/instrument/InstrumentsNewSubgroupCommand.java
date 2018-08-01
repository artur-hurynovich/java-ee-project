package com.hurynovich.mus_site.controller.command.impl.instrument;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsNewSubgroupCommand implements ICommand {
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String instrumentsNewSubgroupSuccessPage = 
		pathsBundle.getString("redirect.instruments.new.subgroup.success");
	private final String errorPage = pathsBundle.getString("redirect.error");
	
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();

	@Override
	public String execute(HttpServletRequest request) throws InstrumentDAOException {
		String fieldsNumberParam = request.getParameter("fieldsNumber");
		int fieldsNumber = Integer.valueOf(fieldsNumberParam);
		
		Map<String, String[]> params = request.getParameterMap();
		List<InstrumentsField> fields = extractFieldsFromParameters(params, fieldsNumber);
		
		String subgroupNameEn = request.getParameter("subgroupNameEn");
		String subgroupNameRu = request.getParameter("subgroupNameRu");
		String groupIdParam  = request.getParameter("groupId");
		int groupId = Integer.valueOf(groupIdParam);
		if (instrumentService.addSubgroup(groupId, subgroupNameEn, subgroupNameRu, fields)) {
			return instrumentsNewSubgroupSuccessPage;
		} else {
			return errorPage;
		}
	}
	
	@Override
	public boolean isRedirect() {
		return true;
	}
	
	private List<InstrumentsField> extractFieldsFromParameters(Map<String, String[]> params, 
		int fieldsNumber) {
		List<InstrumentsField> fields = new LinkedList<>();
		for (int i = 1; i <= fieldsNumber; i++) {
			InstrumentsField field = new InstrumentsField();
			String fieldNameEn = params.get("field_name_en_" + i)[0];
			field.setNameEn(fieldNameEn);
			String fieldNameRu = params.get("field_name_ru_" + i)[0];
			field.setNameRu(fieldNameRu);
			fields.add(field);
		}
		return fields;
	}
}
