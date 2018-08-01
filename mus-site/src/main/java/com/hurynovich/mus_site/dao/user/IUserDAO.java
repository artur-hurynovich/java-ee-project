package com.hurynovich.mus_site.dao.user;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.exception.UserDAOException;

public interface IUserDAO {
	boolean emailExists(String email) throws UserDAOException;
	
	// Create methods
	boolean addUser(String name, String email, String password, UserRole role, String phone) 
		throws UserDAOException;
	
	// Read methods
	User getUser(String email, String password) throws UserDAOException;
	
	User getUserByEmail(String email) throws UserDAOException;
	
	// Update methods
	boolean changeRole(int userId, UserRole role) throws UserDAOException;
}
