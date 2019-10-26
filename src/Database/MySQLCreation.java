package Database;

import java.sql.Connection;
import java.sql.DriverManager;

//creates tables
public class MySQLCreation {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection connection = DriverManager.getConnection(MySQLUtil.URL);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
