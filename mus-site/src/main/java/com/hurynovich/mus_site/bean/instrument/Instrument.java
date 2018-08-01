package com.hurynovich.mus_site.bean.instrument;

import java.io.Serializable;
import java.util.List;

public class Instrument implements Serializable {
	private static final long serialVersionUID = -4400346929691916575L;
	
	private int id;
	private List<InstrumentsField> fields;
	private double rating;
	private String imgSource;
	
	public Instrument() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<InstrumentsField> getFields() {
		return fields;
	}

	public void setFields(List<InstrumentsField> fields) {
		this.fields = fields;
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = Math.ceil(rating * 100) / 100;
	}
	
	public String getImgSource() {
		return imgSource;
	}

	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		Instrument temp = (Instrument) obj;
		
		boolean fieldsEquals = false;
		if (fields != null) {
			fieldsEquals = fields.equals(temp.fields);
		} else {
			fieldsEquals = (temp.fields == null);
		}
		
		return (fieldsEquals);
	}
	
	@Override
	public int hashCode() {
		int res = 13;
		final int PRIME = 25;
		
		res = res * PRIME + id;
		
		if (fields != null) {
			res = res * PRIME + fields.hashCode();
		}
		
		long l = Double.doubleToLongBits(rating); 
		res = res * PRIME + (int)(l ^ (l>>>32));
		
		return res;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + "\n" + 
			"Fields: " + fields + "\n" + 
			"Rating: " + rating;
	}
}
