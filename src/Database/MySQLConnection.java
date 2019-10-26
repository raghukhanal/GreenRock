package Database;

import java.sql.Connection;
import java.sql.DriverManager;



public class MySQLConnection {
private Connection connection;
	
	public MySQLConnection() {
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
	  		 connection = DriverManager.getConnection(MySQLUtil.URL);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void close() {
		if(connection!=null) {
			try{
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
