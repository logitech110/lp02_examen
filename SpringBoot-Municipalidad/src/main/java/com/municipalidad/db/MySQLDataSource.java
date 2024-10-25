package com.municipalidad.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLDataSource {
	// Detalles de conexión para MySQL
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/bd_s1";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "1234";

	
	public static Connection getMySQLConnection() {
		Connection cn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn =DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cn;
	}
}

