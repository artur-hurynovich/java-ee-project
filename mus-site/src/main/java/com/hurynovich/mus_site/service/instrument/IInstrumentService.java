package com.hurynovich.mus_site.service.instrument;

import java.util.List;

import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.instrument.InstrumentsSubgroup;
import com.hurynovich.mus_site.exception.InstrumentDAOException;

public interface IInstrumentService {
	boolean groupExists(String groupLang, String name) throws InstrumentDAOException;
	
	boolean subgroupExists(String subgroupLang, String name) throws InstrumentDAOException;
	
	boolean instrumentRatedByUser(int instrumentId, int userId) throws InstrumentDAOException;
	
	// Create methods
	boolean addGroup (String groupNameEn, String groupNameRu) throws InstrumentDAOException;
	
	boolean addSubgroup(int groupId, String subgroupNameEn, String subgroupNameRu, 
		List<InstrumentsField> fields) throws InstrumentDAOException;
	
	boolean addInstrument(int subgroupId, List<InstrumentsField> fields) 
		throws InstrumentDAOException;
	
	boolean addRating(int instrumentId, int userId, int ratingValue) throws InstrumentDAOException;
	
	boolean addPhoto(int instrumentId, String imgSource) throws InstrumentDAOException;
	
	// Read methods
	List<InstrumentsGroup> getGroups() throws InstrumentDAOException;
	
	InstrumentsSubgroup getSubgroupById(int subgroupId) throws InstrumentDAOException;
	
	Instrument getInstrumentById(int instrumentId) throws InstrumentDAOException;
	
	List<Instrument> getInstruments(int subgroupId, int pageNumber) throws InstrumentDAOException;
	
	List<Instrument> getInstrumentsByFilter(int subgroupId, List<InstrumentsField> filterFields, 
		int pageNumber) throws InstrumentDAOException;
	
	List<InstrumentsField> getFieldsBySubgroupId(int subgroupId) throws InstrumentDAOException;
	
	int getInstrumentsNumber();
	
	int getPagesNumber();
	
	// Delete methods
	boolean deleteGroup(int groupId) throws InstrumentDAOException;
	
	boolean deleteSubgroup(int subgroupId) throws InstrumentDAOException;
	
	boolean deleteInstrument(int instrumentId) throws InstrumentDAOException;
}
