package com.hurynovich.mus_site.bean.instrument;

import java.io.Serializable;

public class FieldValue implements Serializable {
	private static final long serialVersionUID = 6873008039349585078L;
	
	private int id;
	private String nameEn;
	private String nameRu;
	
	public FieldValue() {
		
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		FieldValue temp = (FieldValue) obj;
		
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
		
		return (nameEnEquals && nameRuEquals);
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
		
		return res;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\n" + 
			"Name_En: " + nameEn + "\n" + 
			"Name_Ru: " + nameRu;
	}
}
