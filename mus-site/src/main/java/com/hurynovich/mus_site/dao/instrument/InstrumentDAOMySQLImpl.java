package com.hurynovich.mus_site.dao.instrument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hurynovich.mus_site.bean.instrument.FieldValue;
import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.instrument.InstrumentsSubgroup;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.util.ConnectionFactory;

public class InstrumentDAOMySQLImpl implements IInstrumentDAO {
	private int instrumentsNumber;
	
	private final String TABLE_GROUPS_NAME = "groups";
	private final String TABLE_SUBGROUPS_NAME = "subgroups";
	private final String TABLE_FIELDS_NAME = "fields";
	private final String TABLE_SUBGROUPS_FIELDS_NAME = "subgroups_fields";
	private final String TABLE_FIELD_VALUES_NAME = "field_values";
	private final String TABLE_SUBGROUPS_INSTRUMENTS_NAME = "subgroups_instruments";
	private final String TABLE_INSTRUMENTS_NAME = "instruments";
	private final String TABLE_RATINGS_NAME = "ratings";
	private final String TABLE_PHOTOS_NAME = "photos";

	@Override
	public boolean groupExists(String groupLang, String name) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT group_id FROM " + TABLE_GROUPS_NAME 
				+ " WHERE group_name_" 
				+ groupLang + " = ?;");
			st.setString(1, name);
			res = st.executeQuery();
			if (res.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method groupExists, called "
				+ "with parameters groupLang = " + groupLang + ", "
				+ "name = " + name;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean subgroupExists(String subgroupLang, String name) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT subgroup_id FROM " + TABLE_SUBGROUPS_NAME + 
				" WHERE subgroup_name_" + subgroupLang + " = ?;");
			st.setString(1, name);
			res = st.executeQuery();
			if (res.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method subgroupExists, called "
				+ "with parameters groupLang = " + subgroupLang + ", "
				+ "name = " + name;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean instrumentRatedByUser(int instrumentId, int userId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT user_id FROM " + TABLE_RATINGS_NAME 
				+ " WHERE instrument_id = ? AND user_id = ?;");
			st.setInt(1, instrumentId);
			st.setInt(2, userId);
			res = st.executeQuery();
			return res.next();
		} catch (SQLException e) {
			String excMessage = "SQLException in method instrumentRatedByUser, called "
				+ "with parameters instrumentId = " + instrumentId + ", "
				+ "userId = " + userId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	// Create methods
	@Override
	public boolean addGroup(String groupNameEn, String groupNameRu) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + TABLE_GROUPS_NAME 
				+ " (group_name_en, group_name_ru) VALUES (?, ?);");
			st.setString(1, groupNameEn);
			st.setString(2, groupNameRu);
			int affRows = st.executeUpdate();
			return affRows != 0;
		} catch (SQLException e) {
			String excMessage = "SQLException in method addGroup, called "
				+ "with parameters groupNameEn = " + groupNameEn + ", "
				+ "groupNameRu = " + groupNameRu;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean addSubgroup(int groupId, String subgroupNameEn, String subgroupNameRu, 
		List<InstrumentsField> fields) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + TABLE_SUBGROUPS_NAME 
				+ " (group_id, subgroup_name_en, subgroup_name_ru) VALUES (?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, groupId);
			st.setString(2, subgroupNameEn);
			st.setString(3, subgroupNameRu);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				res = st.getGeneratedKeys();
				res.next();
				int subgroupId = res.getInt(1);
				return addFields(subgroupId, fields);
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method addSubgroup, called "
				+ "with parameters groupId = " + groupId + ", "
				+ "with parameters subgroupNameEn = " + subgroupNameEn + ", "
				+ "with parameters subgroupNameRu = " + subgroupNameRu + ", "
				+ "fields = " + fields;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean addInstrument(int subgroupId, List<InstrumentsField> fields) 
		throws InstrumentDAOException {
		for (InstrumentsField field : fields) {
			if (field.getCurrentValue().getId() == 0) {
				int valueId = addValue(subgroupId, field);
				if (valueId != -1) {
					field.getCurrentValue().setId(valueId);
				} else {
					return false;
				}
			}
		}
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + TABLE_SUBGROUPS_INSTRUMENTS_NAME 
				+ " (subgroup_id) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, subgroupId);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				res = st.getGeneratedKeys();
				res.next();
				int instrumentId = res.getInt(1);
				res.close();
				st.close();
				st = conn.prepareStatement("INSERT INTO " + TABLE_INSTRUMENTS_NAME 
					+ "(instrument_id, field_id, value_id) VALUES (?, ?, ?);");
				for (InstrumentsField field : fields) {
					st.setInt(1, instrumentId);
					int fieldId = field.getId();
					st.setInt(2, fieldId);
					int valueId = field.getCurrentValue().getId();
					st.setInt(3, valueId);
					affRows = st.executeUpdate();
					if (affRows == 0) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method addInstrument, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "fields = " + fields;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	@Override
	public boolean addRating(int instrumentId, int userId, int ratingValue) 
		throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + TABLE_RATINGS_NAME 
				+ " (instrument_id, user_id, rating_value) VALUES (?, ?, ?);");
			st.setInt(1, instrumentId);
			st.setInt(2, userId);
			st.setInt(3, ratingValue);
			int affRows = st.executeUpdate();
			return (affRows != 0);
		} catch (SQLException e) {
			String excMessage = "SQLException in method addRating, called "
				+ "with parameters instrumentId = " + instrumentId + ", "
				+ "userId = " + userId
				+ "ratingValue = " + ratingValue;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean addPhoto(int instrumentId, String imgSource) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + TABLE_PHOTOS_NAME 
				+ " (instrument_id, photo_source) VALUES (?, ?);");
			st.setInt(1, instrumentId);
			st.setString(2, imgSource);
			int affRows = st.executeUpdate();
			return (affRows != 0);
		} catch (SQLException e) {
			String excMessage = "SQLException in method addPhoto, called "
				+ "with parameters instrumentId = " + instrumentId + ", "
				+ "imgSource = " + imgSource;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Read methods
	@Override
	public List<InstrumentsGroup> getGroups() throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_GROUPS_NAME + ";");
			res = st.executeQuery();
			List<InstrumentsGroup> groups = new LinkedList<>();
			while (res.next()) {
				InstrumentsGroup group = new InstrumentsGroup();
				int groupId = res.getInt("group_id");
				group.setId(groupId);
				String groupNameEn = res.getString("group_name_en");
				group.setNameEn(groupNameEn);
				String groupNameRu = res.getString("group_name_ru");
				group.setNameRu(groupNameRu);
				List<InstrumentsSubgroup> subgroups = getSubgroups(groupId);
				group.setSubgroups(subgroups);
				groups.add(group);
			}
			if (!groups.isEmpty()) {
				return groups;
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getGroups";
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public InstrumentsSubgroup getSubgroupById(int subgroupId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_SUBGROUPS_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			res = st.executeQuery();
			if (res.next()) {
				InstrumentsSubgroup subgroup = new InstrumentsSubgroup();
				subgroup.setId(subgroupId);
				String subgroupNameEn = res.getString("subgroup_name_en");
				subgroup.setNameEn(subgroupNameEn);
				String subgroupNameRu = res.getString("subgroup_name_ru");
				subgroup.setNameRu(subgroupNameRu);
				return subgroup;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			String excMessage = "SQLException in method getSubgroupById, called "
				+ "with parameters subgroupId = " + subgroupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public Instrument getInstrumentById(int instrumentId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		ResultSet tempRes = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_INSTRUMENTS_NAME 
				+ " WHERE instrument_id = ? ORDER BY field_id;");
			st.setInt(1, instrumentId);
			res = st.executeQuery();
			Instrument instrument = new Instrument();
			instrument.setId(instrumentId);
			List<InstrumentsField> fields = new LinkedList<>();
			while(res.next()) {
				InstrumentsField field = new InstrumentsField();
				int fieldId = res.getInt("field_id");
				field.setId(fieldId);
				st = conn.prepareStatement("SELECT * FROM " + TABLE_FIELDS_NAME 
					+ " WHERE field_id = ?");
				st.setInt(1, fieldId);
				tempRes = st.executeQuery();
				tempRes.next();
				String fieldNameEn = tempRes.getString("field_name_en");
				field.setNameEn(fieldNameEn);
				String fieldNameRu = tempRes.getString("field_name_ru");
				field.setNameRu(fieldNameRu);
				int valueId = res.getInt("value_id");
				FieldValue currentValue = new FieldValue();
				currentValue.setId(valueId);
				tempRes.close();
				st.close();
				st = conn.prepareStatement("SELECT * FROM " + TABLE_FIELD_VALUES_NAME 
					+ " WHERE value_id = ?");
				st.setInt(1, valueId);
				tempRes = st.executeQuery();
				tempRes.next();
				String valueNameEn = tempRes.getString("value_name_en");
				currentValue.setNameEn(valueNameEn);
				String valueNameRu = tempRes.getString("value_name_ru");
				currentValue.setNameRu(valueNameRu);
				field.setCurrentValue(currentValue);
				fields.add(field);
			}
			double rating = getRating(instrumentId);
			instrument.setRating(rating);
			res.close();
			st.close();
			st = conn.prepareStatement("SELECT photo_source FROM " + TABLE_PHOTOS_NAME 
				+ " WHERE instrument_id = ?");
			st.setInt(1, instrumentId);
			res = st.executeQuery();
			if (res.next()) {
				String imgSource = res.getString("photo_source");
				instrument.setImgSource(imgSource);
			}
			if (fields.isEmpty()) {
				return null;
			} else {
				instrument.setFields(fields);
				return instrument;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getInstrumentById, called "
				+ "with parameter instrumentId = " + instrumentId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res, tempRes);
		}
	}
	
	@Override
	public List<Instrument> getInstrumentsByLimit(int subgroupId, int start, int count) 
		throws InstrumentDAOException {
		List<Integer> instrumentsIds = getInstrumentsIdsByLimit(subgroupId, start, count);
		
		List<Instrument> instruments = new LinkedList<>();
		if (instrumentsIds != null) {
			for (Integer instrumentId : instrumentsIds) {
				Instrument instrument = getInstrumentById(instrumentId);
				instruments.add(instrument);
			}
			
			if (instruments.isEmpty()) {
				return null;
			} else {
				return instruments;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public List<Instrument> getInstrumentsByFilterByLimit(int subgroupId, 
		List<InstrumentsField> filterFields, int start, int count) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			List<Integer> filteredInstrumentsIds = new LinkedList<>();
			List<Integer> instrumentsIds = getInstrumentsIds(subgroupId);
			for (int instrumentId : instrumentsIds) {
				int fieldMatch = 0;
				for (InstrumentsField field : filterFields) {
					int fieldId = field.getId();
					int valueId = field.getCurrentValue().getId();
					st = conn.prepareStatement("SELECT instrument_id FROM " + TABLE_INSTRUMENTS_NAME 
						+ " WHERE instrument_id = ? AND field_id = ? AND value_id = ?;");
					st.setInt(1, instrumentId);
					st.setInt(2, fieldId);
					st.setInt(3, valueId);
					res = st.executeQuery();
					if (res.next()) {
						fieldMatch++;
					}
				}
				if (fieldMatch == filterFields.size()) {
					filteredInstrumentsIds.add(instrumentId);
				}
			}
			if (!filteredInstrumentsIds.isEmpty()) {
				List<Instrument> filteredInstruments = new ArrayList<>();
				for (int filteredInstrumentId : filteredInstrumentsIds) {
					Instrument filteredInstrument = getInstrumentById(filteredInstrumentId);
					if (filteredInstrument != null) {
						filteredInstruments.add(filteredInstrument);
					}
				}
				if (!filteredInstruments.isEmpty()) {
					instrumentsNumber = filteredInstruments.size();
					List<Instrument> limitedFilteredInstruments = new LinkedList<>();
					for (int i = start; (i < start + count) && i < filteredInstruments.size(); i++) {
						limitedFilteredInstruments.add(filteredInstruments.get(i));
					}
					return limitedFilteredInstruments;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getInstrumentsByFilterByLimit, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "filterFields = " + filterFields + ", "
				+ "start = " + start + ", "
				+ "count = " + count;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public List<InstrumentsField> getFieldsBySubgroupId(int subgroupId) 
		throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_SUBGROUPS_FIELDS_NAME + " JOIN " 
				+ TABLE_FIELDS_NAME + " USING (field_id) WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			res = st.executeQuery();
			List<InstrumentsField> fields = new LinkedList<>();
			while (res.next()) {
				InstrumentsField field = new InstrumentsField();
				int fieldId = res.getInt("field_id");
				field.setId(fieldId);
				String fieldNameEn = res.getString("field_name_en");
				field.setNameEn(fieldNameEn);
				String fieldNameRu = res.getString("field_name_ru");
				field.setNameRu(fieldNameRu);
				List<FieldValue> existingValues = getExistingValues(subgroupId, fieldId);
				field.setExistingValues(existingValues);
				fields.add(field);
			}
			return fields;
		} catch (SQLException e) {
			String excMessage = "SQLException in method getFieldsBySubgroupId, called "
				+ "with parameter subgroupId = " + subgroupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public int getInstrumentsNumber() {
		return instrumentsNumber;
	}
	
	// Delete methods
	@Override
	public boolean deleteGroup(int groupId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM " + TABLE_GROUPS_NAME 
				+ " WHERE group_id = ?;");
			st.setInt(1, groupId);
			int affRows = st.executeUpdate();
			if (affRows == 0) {
				return false;
			}
			st.close();
			st = conn.prepareStatement("SELECT * FROM " + TABLE_SUBGROUPS_NAME 
				+ " WHERE group_id = ?;");
			st.setInt(1, groupId);
			res = st.executeQuery();
			List<Integer> subgroupsIds = new LinkedList<>();
			while (res.next()) {
				int subgroupId = res.getInt("subgroup_id");
				subgroupsIds.add(subgroupId);
			}
			if (!subgroupsIds.isEmpty()) {
				for (int subgroupId : subgroupsIds) {
					if (!deleteSubgroup(subgroupId)) {
						return false;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			String excMessage = "SQLException in method deleteGroup, called "
				+ "with parameter groupId = " + groupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean deleteSubgroup(int subgroupId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM " + TABLE_SUBGROUPS_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			int affRows = st.executeUpdate();
			if (affRows == 0) {
				return false;
			}
			st.close();
			st = conn.prepareStatement("DELETE FROM " + TABLE_SUBGROUPS_FIELDS_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			affRows = st.executeUpdate();
			if (affRows == 0) {
				return false;
			}
			st.close();
			st = conn.prepareStatement("DELETE FROM " + TABLE_FIELD_VALUES_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			affRows = st.executeUpdate();
			List<Integer> instrumentsIds = getInstrumentsIds(subgroupId);
			if (instrumentsIds != null) {
				for (int instrumentId : instrumentsIds) {
					if (!deleteInstrument(instrumentId)) {
						return false;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			String excMessage = "SQLException in method deleteSubgroup, called "
				+ "with parameters subgroupId = " + subgroupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public boolean deleteInstrument(int instrumentId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM " + TABLE_SUBGROUPS_INSTRUMENTS_NAME 
				+ " WHERE instrument_id = ?;");
			st.setInt(1, instrumentId);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				st.close();
				st = conn.prepareStatement("DELETE FROM " + TABLE_INSTRUMENTS_NAME 
						+ " WHERE instrument_id = ?;");
				st.setInt(1, instrumentId);
				affRows = st.executeUpdate();
				deleteRating(instrumentId);
				deletePhoto(instrumentId);
				return (affRows != 0);
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method deleteInstrument, called "
				+ "with parameters instrumentId = " + instrumentId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	// Secondary methods
	private boolean addFields(int subgroupId, List<InstrumentsField> fields) 
		throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			for (int i = 0; i < fields.size(); i++) {
				String fieldNameEn = fields.get(i).getNameEn();
				String fieldNameRu = fields.get(i).getNameRu();
				st = conn.prepareStatement("SELECT field_id FROM " + TABLE_FIELDS_NAME 
					+ " WHERE field_name_en = ?");
				st.setString(1, fieldNameEn);
				res = st.executeQuery();
				if (res.next()) {
					int fieldId = res.getInt("field_id");
					res.close();
					st.close();
					st = conn.prepareStatement("INSERT INTO " + TABLE_SUBGROUPS_FIELDS_NAME 
						+ " (subgroup_id, field_id) VALUES (?, ?);");
					st.setInt(1, subgroupId);
					st.setInt(2, fieldId);
					int affRows = st.executeUpdate();
					if (affRows == 0) {
						return false;
					}
				} else {
					res.close();
					st.close();
					st = conn.prepareStatement("INSERT INTO " + TABLE_FIELDS_NAME 
						+ " (field_name_en, field_name_ru) VALUES (?, ?);", 
						Statement.RETURN_GENERATED_KEYS);
					st.setString(1, fieldNameEn);
					st.setString(2, fieldNameRu);
					int affRows = st.executeUpdate();
					if (affRows != 0) {
						res = st.getGeneratedKeys();
						res.next();
						int fieldId = res.getInt(1);
						st = conn.prepareStatement("INSERT INTO " + TABLE_SUBGROUPS_FIELDS_NAME 
							+ " (subgroup_id, field_id) VALUES (?, ?);");
						st.setInt(1, subgroupId);
						st.setInt(2, fieldId);
						affRows = st.executeUpdate();
						if (affRows == 0) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			String excMessage = "SQLException in method addFields, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "fields = " + fields;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private int addValue(int subgroupId, InstrumentsField field) throws InstrumentDAOException {
		int fieldId = field.getId();
		String valueNameEn = field.getCurrentValue().getNameEn();
		String valueNameRu = field.getCurrentValue().getNameRu();

		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		int valueId = -1;
		try {
			st = conn.prepareStatement("SELECT value_id FROM " + TABLE_FIELD_VALUES_NAME 
				+ " WHERE value_name_en = ? AND subgroup_id = ?;");
			st.setString(1, valueNameEn);
			st.setInt(2, subgroupId);
			res = st.executeQuery();
			if (res.next()) {
				valueId = res.getInt(1);
			} else {
				res.close();
				st.close();
				st = conn.prepareStatement("INSERT INTO " + TABLE_FIELD_VALUES_NAME 
					+ " (value_name_en, value_name_ru, field_id, subgroup_id) VALUES (?, ?, ?, ?);", 
					Statement.RETURN_GENERATED_KEYS);
				st.setString(1, valueNameEn);
				st.setString(2, valueNameRu);
				st.setInt(3, fieldId);
				st.setInt(4, subgroupId);
				int affRows = st.executeUpdate();
				if (affRows != 0) {
					res = st.getGeneratedKeys();
					res.next();
					valueId = res.getInt(1);
					return valueId;
				} 
			}
			return valueId;
		} catch (SQLException e) {
			String excMessage = "SQLException in method addValue, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "field = " + field;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	private List<InstrumentsSubgroup> getSubgroups(int groupId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_SUBGROUPS_NAME + " WHERE group_id = ?;");
			st.setInt(1, groupId);
			res = st.executeQuery();
			List<InstrumentsSubgroup> subgroups = new LinkedList<>();
			while (res.next()) {
				InstrumentsSubgroup subgroup = new InstrumentsSubgroup();
				int subgroupId = res.getInt("subgroup_id");
				subgroup.setId(subgroupId);
				String subgroupNameEn = res.getString("subgroup_name_en");
				subgroup.setNameEn(subgroupNameEn);
				String subgroupNameRu = res.getString("subgroup_name_ru");
				subgroup.setNameRu(subgroupNameRu);
				subgroups.add(subgroup);
			}
			if (!subgroups.isEmpty()) {
				return subgroups;
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getSubgroups, called "
				+ "with parameters groupId = " + groupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private double getRating(int instrumentId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT AVG(rating_value) FROM " + TABLE_RATINGS_NAME 
				+ " WHERE instrument_id = ?;");
			st.setInt(1, instrumentId);
			res = st.executeQuery();
			res.next();
			double rating = res.getDouble(1);
			return rating;
		} catch (SQLException e) {
			String excMessage = "SQLException in method getRating, called "
				+ "with parameters instrumentId = " + instrumentId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private List<Integer> getInstrumentsIdsByLimit(int subgroupId, int start, int count) 
		throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT COUNT(*) FROM " + TABLE_SUBGROUPS_INSTRUMENTS_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			res = st.executeQuery();
			if (res.next()) {
				instrumentsNumber = res.getInt(1);
			} else {
				instrumentsNumber = 0;
			}
			res.close();
			st.close();
			st = conn.prepareStatement("SELECT instrument_id FROM " 
				+ TABLE_SUBGROUPS_INSTRUMENTS_NAME + " WHERE subgroup_id = ? LIMIT ?, ?;");
			st.setInt(1, subgroupId);
			st.setInt(2, start);
			st.setInt(3, count);
			res = st.executeQuery();
			List<Integer> instrumentsIds = new LinkedList<>();
			while (res.next()) {
				int instrumentId = res.getInt("instrument_id");
				instrumentsIds.add(instrumentId);
			}
			if (instrumentsIds.isEmpty()) {
				return null;
			} else {
				return instrumentsIds;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getInstrumentsIdsByLimit, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "with parameters start = " + start + ", "
				+ "count = " + count;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private List<Integer> getInstrumentsIds(int subgroupId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT instrument_id FROM " + TABLE_SUBGROUPS_INSTRUMENTS_NAME 
				+ " WHERE subgroup_id = ?;");
			st.setInt(1, subgroupId);
			res = st.executeQuery();
			List<Integer> instrumentsIds = new LinkedList<>();
			while (res.next()) {
				int instrumentId = res.getInt("instrument_id");
				instrumentsIds.add(instrumentId);
			}
			if (instrumentsIds.isEmpty()) {
				return null;
			} else {
				return instrumentsIds;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getInstrumentsIds, called "
				+ "with parameters subgroupId = " + subgroupId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private List<FieldValue> getExistingValues(int subgroupId, int fieldId) 
		throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM " + TABLE_FIELD_VALUES_NAME 
				+ " WHERE field_id = ? AND subgroup_id = ?;");
			st.setInt(1, fieldId);
			st.setInt(2, subgroupId);
			res = st.executeQuery();
			List<FieldValue> values = new LinkedList<>();
			while (res.next()) {
				FieldValue value = new FieldValue();
				int valueId = res.getInt("value_id");
				value.setId(valueId);
				String valueNameEn = res.getString("value_name_en");
				value.setNameEn(valueNameEn);
				String valueNameRu = res.getString("value_name_ru");
				value.setNameRu(valueNameRu);
				values.add(value);
			}
			return values;
		} catch (SQLException e) {
			String excMessage = "SQLException in method getExistingValues, called "
				+ "with parameters subgroupId = " + subgroupId + ", "
				+ "with parameters fieldId = " + fieldId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private void deleteRating(int instrumentId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM " + TABLE_RATINGS_NAME 
				+ " WHERE instrument_id = ?;");
			st.setInt(1, instrumentId);
			st.executeUpdate();
		} catch (SQLException e) {
			String excMessage = "SQLException in method deleteRating, called "
				+ "with parameters instrumentId = " + instrumentId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	public void deletePhoto(int instrumentId) throws InstrumentDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM " + TABLE_PHOTOS_NAME 
				+ " WHERE instrument_id = ?;");
			st.setInt(1, instrumentId);
			st.executeUpdate();
		} catch (SQLException e) {
			String excMessage = "SQLException in method deletePhoto, called "
				+ "with parameters instrumentId = " + instrumentId;
			throw new InstrumentDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private void close(Connection conn, PreparedStatement st, ResultSet ... res) 
		throws InstrumentDAOException {
		try {
			for (ResultSet rs : res) {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			}
			if (st != null) {
				st.close();
				st = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method close";
			throw new InstrumentDAOException(excMessage, e);
		} 
	}
}
