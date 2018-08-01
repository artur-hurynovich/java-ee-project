package com.hurynovich.mus_site.bean.instrument;

import java.io.Serializable;
import java.util.List;

public class InstrumentsField implements Serializable {
	private static final long serialVersionUID = -7906080694564889988L;
	
	private int id;
	private String nameEn;
	private String nameRu;
	private FieldValue currentValue;
	private List<FieldValue> existingValues;
	
	public InstrumentsField() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNameEn() {
		return nameEn;
	}
	
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	public String getNameRu() {
		return nameRu;
	}
	
	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}
	
	public FieldValue getCurrentValue() {
		return currentValue;
	}
	
	public void setCurrentValue(FieldValue currentValue) {
		this.currentValue = currentValue;
	}
	
	public List<FieldValue> getExistingValues() {
		return existingValues;
	}
	
	public void setExistingValues(List<FieldValue> existingValues) {
		this.existingValues = existingValues;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		InstrumentsField temp = (InstrumentsField) obj;
		
		boolean nameEnEquals = false;
		if (nameEn != null) {
			nameEnEquals = nameEn.equals(temp.nameEn);
		} else {
			nameEnEquals = (temp.nameEn == null);
		}
		
		boolean nameRuEquals = false;
		if (nameRu != null) {
			nameRuEquals = nameRu.equals(temp.nameRu);
		} else {
			nameRuEquals = (temp.nameRu == null);
		}
		
		boolean currentValueEquals = false;
		if (currentValue != null) {
			currentValueEquals = currentValue.equals(temp.currentValue);
		} else {
			currentValueEquals = (temp.currentValue == null);
		}
		
		boolean existingValuesEquals = false;
		if (existingValues != null) {
			existingValuesEquals = existingValues.equals(temp.existingValues);
		} else {
			existingValuesEquals = (temp.existingValues == null);
		}
		
		return (nameEnEquals && nameRuEquals && currentValueEquals
			&& existingValuesEquals);
	}
	
	@Override
	public int hashCode() {
		int res = 13;
		final int PRIME = 25;
		
		res = res * PRIME + id;
		
		if (nameEn != null) {
			res = res * PRIME + nameEn.hashCode();
		}
		
		if (nameRu != null) {
			res = res * PRIME + nameRu.hashCode();
		}
		
		if (currentValue != null) {
			res = res * PRIME + currentValue.hashCode();
		}
		
		if (existingValues != null) {
			res = res * PRIME + existingValues.hashCode();
		}
		
		return res;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\n" + 
			"Name_En: " + nameEn + "\n" + 
			"Name_Ru: " + nameRu + "\n" + 
			"Current value: " + currentValue + "\n" + 
			"Existing values: " + existingValues;
	}
}
