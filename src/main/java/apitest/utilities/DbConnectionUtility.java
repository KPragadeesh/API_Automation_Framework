package apitest.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnectionUtility {
	
	Helper helper = Helper.getInstance();
	private String dbUrl = helper.loadProperties("dbUrl");
	private String dbUsername = helper.loadProperties("dbUsername");
	private String dbPassword = helper.loadProperties("dbPassword");
	private String driver = helper.loadProperties("driver");
	
	public void deleteOauthUser(String email) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		Statement s = conn.createStatement(); 
		
		s.execute("DELETE from authorities where oauth_users_id in (SELECT id from oauth_users ou  where email ='"+email+"')");
		s.execute("DELETE from oauth_code where oauth_user_id in (SELECT id from oauth_users ou  where email ='"+email+"')");
		s.execute("DELETE from oauth_users where email ='"+email+"'");
	}
}
