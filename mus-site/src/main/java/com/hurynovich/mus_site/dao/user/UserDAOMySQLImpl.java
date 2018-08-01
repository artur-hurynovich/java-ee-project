package com.hurynovich.mus_site.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.exception.UserDAOException;
import com.hurynovich.mus_site.util.ConnectionFactory;

public class UserDAOMySQLImpl implements IUserDAO {
	
	@Override
	public boolean emailExists(String email) throws UserDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT user_email FROM users WHERE user_email = ?");
			st.setString(1, email);
			res = st.executeQuery();
			if (res.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method emailExists, called "
				+ "with parameter email = " + email;
			throw new UserDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Create methods
	@Override
	public boolean addUser(String name, String email, String password, UserRole role, String phone) 
		throws UserDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO users "
				+ "(user_name, user_email, user_password, user_role, user_phone) "
				+ "VALUES (?, ?, ?, ?, ?);");
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, password);
			st.setString(4, role.toString());
			st.setString(5, phone);
			int affRows = st.executeUpdate();
			if (affRows != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method addUser, called "
				+ "with parameters name = " + name + ", "
				+ "email = " + email + ", "
				+ "password = " + password + ", "
				+ "role = " + role
				+ "phone = " + phone;
			throw new UserDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Read methods
	@Override
	public User getUser(String email, String password) throws UserDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM users WHERE user_email = ? "
				+ "AND user_password = ?;");
			st.setString(1, email);
			st.setString(2, password);
			res = st.executeQuery();
			if (res.next()) {
				User user = new User();
				int userId = res.getInt("user_id");
				user.setId(userId);
				String name = res.getString("user_name");
				user.setName(name);
				user.setEmail(email);
				UserRole role = UserRole.valueOf(res.getString("user_role"));
				user.setRole(role);
				String phone = res.getString("user_phone");
				user.setPhone(phone);
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getUser, called "
					+ "with parameters email = " + email + ", "
					+ "password = " + password;
			throw new UserDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	@Override
	public User getUserByEmail(String email) throws UserDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM users WHERE user_email = ?;");
			st.setString(1, email);
			res = st.executeQuery();
			if (res.next()) {
				User user = new User();
				int userId = res.getInt("user_id");
				user.setId(userId);
				String name = res.getString("user_name");
				user.setName(name);
				user.setEmail(email);
				UserRole role = UserRole.valueOf(res.getString("user_role"));
				user.setRole(role);
				String phone = res.getString("user_phone");
				user.setPhone(phone);
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			String excMessage = "SQLException in method getUserByEmail, called "
				+ "with parameter email = " + email;
			throw new UserDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}

	// Update methods
	@Override
	public boolean changeRole(int userId, UserRole role) throws UserDAOException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement st = null;
		ResultSet res = null;
		
		try {
			st = conn.prepareStatement("UPDATE users SET user_role = ? WHERE user_id = ?;");
			st.setString(1, role.toString());
			st.setInt(2, userId);
			int affRows = st.executeUpdate();
			return (affRows != 0);
		} catch (SQLException e) {
			String excMessage = "SQLException in method changeRole, called "
				+ "with parameters userId = " + userId + ", "
				+ "role = " + role;
			throw new UserDAOException(excMessage, e);
		} finally {
			close(conn, st, res);
		}
	}
	
	private void close(Connection conn, PreparedStatement st, ResultSet res) throws UserDAOException {
		try {
			if (res != null) {
				res.close();
				res = null;
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
			throw new UserDAOException(excMessage, e);
		}
	}
}
