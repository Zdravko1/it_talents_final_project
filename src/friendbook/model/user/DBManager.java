package friendbook.model.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	private static final String DB_PASS = "zdravko1";
	private static final String DB_USER = "root";
	private static final String DB_PORT = "3306";
	private static final String DB_IP = "127.0.0.1";
	private static final String DB_NAME = "mydb";
	
	private static Connection connection;
	private static DBManager instance;
	
	public static synchronized DBManager getInstance() {
		if(instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, Driver not loaded or does not exist! Aborting.");
			return;
		}
		System.out.println("Driver loaded");
		//create connection
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_IP+":"+DB_PORT+"/" + DB_NAME, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Sorry, connection failed. Maybe wrong credentials?");
			System.out.println(e.getMessage());
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
