package to_delete_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import metier.*;


public class DAOUser {
	
	
	public User addContact( String firstname, String lastname, String email, String pass){

		User contact = new User();
		contact.setFirstName(firstname);
		contact.setName(lastname);
		contact.setEmail(email);
		contact.setPass(pass);

		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection("db", "root", ""); 
			Statement stmt = con.createStatement();
			String request = "INSERT INTO contacts(firstname,lastname,email) VALUES( '"+firstname+"','"+lastname+"','"+email+"')";
			stmt.executeUpdate(request);
			stmt.close();   	
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}

		return contact;
	}
	

}
