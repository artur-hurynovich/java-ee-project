package com.hurynovich.mus_site.bean.instrument;

import java.io.Serializable;
import java.util.List;

public class InstrumentsGroup implements Serializable {
	private static final long serialVersionUID = -8223085745482986310L;
	
	private int id;
	private String nameEn;
	private String nameRu;
	private List<InstrumentsSubgroup> subgroups;
	
	public InstrumentsGroup() {
		
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
	
	public void setSubgroups(List<InstrumentsSubgroup> subgroups) {
		this.subgroups = subgroups;
	}
	
	public List<InstrumentsSubgroup> getSubgroups() {
		return subgroups;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		InstrumentsGroup temp = (InstrumentsGroup) obj;
		
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
		
		boolean subgroupsEquals  = false;
		if (subgroups != null) {
			subgroupsEquals = (subgroups.equals(temp.subgroups));
		} else {
			subgroupsEquals = (temp.subgroups == null);
		}
		
		return (nameEnEquals && nameRuEquals && subgroupsEquals);
		
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
		
		if (subgroups != null) {
			res = res * PRIME + subgroups.hashCode();
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + "\n" + 
			"Name_En: " + nameEn + "\n" + 
			"Name_Ru: " + nameRu + "\n" + 
			"Subgroups: " + subgroups;
	}
}
