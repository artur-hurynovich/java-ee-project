package com.hurynovich.mus_site.service.user;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.dao.DAOFactory;
import com.hurynovich.mus_site.dao.user.IUserDAO;
import com.hurynovich.mus_site.exception.UserDAOException;

public class UserServiceImpl implements IUserService {
	private final IUserDAO userDao = DAOFactory.getInstance().getUserDao();
	
	@Override
	public boolean emailExists(String email) throws UserDAOException{
		return userDao.emailExists(email);
	}

	// Create methods
	@Override
	public boolean registration(String name, String email, String password, UserRole role, String phone)
		throws UserDAOException {
		return userDao.addUser(name, email, password, role, phone);
	}

	// Read methods
	@Override
	public User getUserByEmailAndPassword(String email, String password) throws UserDAOException {
		return userDao.getUser(email, password);
	}

	@Override
	public User getUserByEmail(String email) throws UserDAOException {
		return userDao.getUserByEmail(email);
	}

	// Update methods
	@Override
	public boolean changeRole(int userId, UserRole role) throws UserDAOException {
		return userDao.changeRole(userId, role);
	}
}
