package com.hurynovich.mus_site.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {
	private final static ConnectionFactory INSTANCE = new ConnectionFactory();
	private DataSource dataSource;
	
	private ConnectionFactory() {
		try {
			InitialContext initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/musSiteDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
