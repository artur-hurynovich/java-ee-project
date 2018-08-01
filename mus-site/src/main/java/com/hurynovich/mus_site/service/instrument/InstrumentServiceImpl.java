package com.hurynovich.mus_site.service.instrument;

import java.util.LinkedList;
import java.util.List;

import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.instrument.InstrumentsSubgroup;
import com.hurynovich.mus_site.dao.DAOFactory;
import com.hurynovich.mus_site.dao.instrument.IInstrumentDAO;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.util.paginator.IPaginator;
import com.hurynovich.mus_site.util.paginator.PaginatorFactory;

public class InstrumentServiceImpl implements IInstrumentService {
	private final IInstrumentDAO instrumentDao = DAOFactory.getInstance().getInstrumentDao();
	
	private final IPaginator paginator = PaginatorFactory.getInstance().getInstrumentsPaginator();
	
	private int instrumentsNumber;
	private int pagesNumber;
	
	private final String markFieldEn = "Mark";
	private final String modelFieldEn = "Model";
	private final String markFieldRu = "Марка";
	private final String modelFieldRu = "Модель";
	
	@Override
	public boolean groupExists(String groupLang, String name) throws InstrumentDAOException {
		return instrumentDao.groupExists(groupLang, name);
	}
	
	@Override
	public boolean subgroupExists(String subgroupLang, String name) throws InstrumentDAOException {
		return instrumentDao.subgroupExists(subgroupLang, name);
	}
	
	@Override
	public boolean instrumentRatedByUser(int instrumentId, int userId) 
		throws InstrumentDAOException {
		return instrumentDao.instrumentRatedByUser(instrumentId, userId);
	}
	
	// Create methods
	@Override
	public boolean addGroup(String groupNameEn, String groupNameRu) throws InstrumentDAOException {
		return instrumentDao.addGroup(groupNameEn, groupNameRu);
	}
	
	@Override
	public boolean addSubgroup(int groupId, String subgroupNameEn, String subgroupNameRu, 
		List<InstrumentsField> fields) throws InstrumentDAOException {
		InstrumentsField markField = new InstrumentsField();
		markField.setNameEn(markFieldEn);
		markField.setNameRu(markFieldRu);
		InstrumentsField modelField = new InstrumentsField();
		modelField.setNameEn(modelFieldEn);
		modelField.setNameRu(modelFieldRu);
		List<InstrumentsField> mandatoryFields = new LinkedList<>();
		mandatoryFields.add(markField);
		mandatoryFields.add(modelField);
		mandatoryFields.addAll(fields);
		
		return instrumentDao.addSubgroup(groupId, subgroupNameEn, subgroupNameRu, mandatoryFields);
	}
	
	@Override
	public boolean addInstrument(int subgroupId, List<InstrumentsField> fields) 
		throws InstrumentDAOException {
		return instrumentDao.addInstrument(subgroupId, fields);
	}
	
	@Override
	public boolean addRating(int instrumentId, int userId, int ratingValue) 
		throws InstrumentDAOException {
		return instrumentDao.addRating(instrumentId, userId, ratingValue);
	}
	
	@Override
	public boolean addPhoto(int instrumentId, String imgSource) throws InstrumentDAOException {
		return instrumentDao.addPhoto(instrumentId, imgSource);
	}
	
	// Read methods
	@Override
	public List<InstrumentsGroup> getGroups() throws InstrumentDAOException {
		return instrumentDao.getGroups();
	}
	
	@Override
	public InstrumentsSubgroup getSubgroupById(int subgroupId) throws InstrumentDAOException {
		return instrumentDao.getSubgroupById(subgroupId);
	}

	@Override
	public Instrument getInstrumentById(int instrumentId) throws InstrumentDAOException {
		return instrumentDao.getInstrumentById(instrumentId);
	}
	
	@Override
	public List<Instrument> getInstruments(int subgroupId, int pageNumber) 
		throws InstrumentDAOException {
		int start = paginator.getStart(pageNumber);
		int count = paginator.getCount();
		List<Instrument> instruments = 
			instrumentDao.getInstrumentsByLimit(subgroupId, start, count);
		instrumentsNumber = instrumentDao.getInstrumentsNumber();
		pagesNumber = paginator.getPagesNumber(instrumentsNumber);
		
		return instruments;
	}

	@Override
	public List<Instrument> getInstrumentsByFilter(int subgroupId, 
		List<InstrumentsField> filterFields, int pageNumber) throws InstrumentDAOException {
		int start = paginator.getStart(pageNumber);
		int count = paginator.getCount();
		List<Instrument> instruments =
			instrumentDao.getInstrumentsByFilterByLimit(subgroupId, filterFields, start, count);
		instrumentsNumber = instrumentDao.getInstrumentsNumber();
		pagesNumber = paginator.getPagesNumber(instrumentsNumber);
		
		return instruments;
	}
	
	@Override
	public List<InstrumentsField> getFieldsBySubgroupId(int subgroupId) 
		throws InstrumentDAOException {
		return instrumentDao.getFieldsBySubgroupId(subgroupId);
	}

	@Override
	public int getInstrumentsNumber() {
		return instrumentsNumber;
	}
	
	@Override
	public int getPagesNumber() {
		return pagesNumber;
	}
	
	// Delete methods
	@Override
	public boolean deleteGroup(int groupId) throws InstrumentDAOException {
		return instrumentDao.deleteGroup(groupId);
	}
	
	@Override
	public boolean deleteSubgroup(int subgroupId) throws InstrumentDAOException {
		return instrumentDao.deleteSubgroup(subgroupId);
	}

	@Override
	public boolean deleteInstrument(int instrumentId) throws InstrumentDAOException {
		return instrumentDao.deleteInstrument(instrumentId);
	}
}
