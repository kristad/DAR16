package Control;


import java.sql.Connection;
import java.sql.DriverManager;


public class Connect {
	
	public  void Connect (){ 
		   //Vos données, variables, différents traitements…
		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	   // String appname = System.getenv("OPENSHIFT_APP_NAME");
	    
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ;
	}
	

}
