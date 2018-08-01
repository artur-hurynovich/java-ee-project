package com.hurynovich.mus_site.bean.user;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -9125357857887092157L;

	private int id;
	private String name;
	private String email;
	private String password;
	private UserRole role;
	private String phone;

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		User temp = (User) obj;
		
		boolean nameEquals = false;
		if (name != null) {
			nameEquals = name.equals(temp.name);
		} else {
			nameEquals = (temp.name == null);
		}
		
		boolean emailEquals = false;
		if (email != null) {
			emailEquals = email.equals(temp.email);
		} else {
			emailEquals = (temp.email == null);
		}
		
		boolean passwordEquals = false;
		if (password != null) {
			passwordEquals = password.equals(temp.password);
		} else {
			passwordEquals = (temp.password == null);
		}
		
		boolean roleEquals = false;
		if (role != null) {
			roleEquals = role.equals(temp.role);
		} else {
			roleEquals = (temp.role == null);
		}
		
		boolean phoneEquals = false;
		if (phone != null) {
			phoneEquals = phone.equals(temp.phone);
		} else {
			phoneEquals = (temp.phone == null);
		}
		
		return (nameEquals && emailEquals && passwordEquals && roleEquals && phoneEquals);
	}

	@Override
	public int hashCode() {
		int res = 13;
		final int PRIME = 25;
		
		res = res * PRIME + id;
		
		if (email != null) {
			res = res * PRIME + email.hashCode();
		}
		
		if (password != null) {
			res = res * PRIME + password.hashCode();
		}
		
		if (role != null) {
			res = res * PRIME + role.hashCode();
		}
		
		if (phone != null) {
			res = res * PRIME + phone.hashCode();
		}
		
		return res;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\n" + 
			"E-mail: " + email + "\n" + 
			"Password: " + password + "\n" + 
			"Role: " + role + "\n" + 
			"Phone number: " + phone;
	}
}
