package com.tym.model.connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {
	
	private static DBConnection instance = null;
	
	private static final String DB_SCHEMA;
	private static final String DB_PORT; 
	private static final String DB_HOST; 
	private static final String DB_USERNAME;
	private static final String DB_PASSWORD; 

	private Connection connection;
	
	static {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C:\\login.properties.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		DB_SCHEMA = sc.nextLine();
		DB_PORT = sc.nextLine();
		DB_HOST = sc.nextLine();	
		DB_USERNAME = sc.nextLine();
		DB_PASSWORD = sc.nextLine();
	}

	private DBConnection() throws ClassNotFoundException, SQLException {
		
		
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST 
													+ ":" + DB_PORT + "/" 
													+ DB_SCHEMA, DB_USERNAME, DB_PASSWORD);	 
	}
	
	public static DBConnection getInstance () {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}
