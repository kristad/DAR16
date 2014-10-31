package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Contrat;
import metier.User;

public class DAOUser implements IDAOUser{

	/**
	 * Rajoute un contact dans la base de donnees.
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return renvoit le nouveau contact
	 */

	/**
	 * Suppresion d'un contact a partir de son identifiant
	 * @param id
	 * @return vrai si la suppression a bien ete effectuee
	 */

	
	//testé
	public User addUser(String firstname, String lastname, String email, String pass, int sexe, int DateNaissance, Contrat mon_c) {
		
		
		int lastId=0;
		
		User contact = new User();
		
		contact.setFirstName(firstname);
		contact.setName(lastname);
		contact.setEmail(email);
		contact.setPass(pass);
		contact.setSexe(sexe);
		contact.setDateNaissance(DateNaissance);
		contact.setMon_contrat(mon_c); // si nul exclure de la requette ! à chaque foreign key faire deux requettes dans deux methodes
		
		
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		    Class.forName("com.mysql.jdbc.Driver");
		     con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
	        
			Statement stmt_test = con.createStatement();
			String request_test;
			if (mon_c == null){
			// le idc sera à null automatiquement, attention au get
			request_test = "INSERT INTO user(name, firstname, email, pass, sexe , datenaissance) VALUES('"+firstname+"','"+lastname+"','"+email+"','"+pass+"','"+sexe+"','"+DateNaissance+"')";
			}else{
			request_test = "INSERT INTO user(name, firstname, email, pass, sexe , datenaissance, idc) VALUES('"+firstname+"','"+lastname+"','"+email+"','"+pass+"','"+sexe+"','"+DateNaissance+"','"+mon_c.getId()+"')"; 
			}
			stmt_test.executeUpdate(request_test); // ligne ajouté dans la bd, faire une requette pour le max id
			
			// la modife commence ici

			Statement stmt_id = con.createStatement();
			rec = stmt_id.executeQuery("SELECT max(id) FROM user"); // retourne le id maximum;
			while (rec.next()) {
			contact.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			stmt_id.close(); 
			rec.close();
			// la modife termine ici	
			
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		return contact;
		
	}
	
	/*Cannot delete or update a parent row: a foreign key constraint*/
	// testé
	public int deleteUser(int id){
		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			String request = "DELETE FROM user WHERE id = "+id; 
			success = stmt.executeUpdate(request);
			stmt.close();
			con.close();
			
		} catch( Exception e ){
			e.printStackTrace();
		}

		return success;
	}

	

	// testé
	public User getUser(int id){
		
		ResultSet rec = null;
		User contact = new User();
		DAOContrat mon_contrat= new DAOContrat();  // créer l'objet Contrat qui sera chercher dans la bd par getContrat()
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM user WHERE id = "+id); 

			while (rec.next()) {
				
				contact.setId(Integer.parseInt(rec.getString("id")));
				contact.setFirstName(rec.getString("firstname")); 
				contact.setName(rec.getString("name")); 
				contact.setEmail(rec.getString("email")); 
				contact.setPass(rec.getString("pass")); 
				contact.setSexe(Integer.parseInt(rec.getString("sexe"))); // parse boolean n'accepte pas 1= true à voir
				contact.setDateNaissance(Integer.parseInt(rec.getString("datenaissance")));
				
				String s=rec.getString("idc");
				
				if(s != null ){
					 contact.setMon_contrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 contact.setMon_contrat(null);
				}
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contact;
	}

	
	

	//testé
	public boolean modifyUser(int id, String firstname, String lastname,
			String email, String pass, int sexe, int DateNaissance, Contrat mon_c) {
		
		boolean success = false;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
		       
			Statement stmt = con.createStatement();
			String sqlFirstName = "UPDATE user SET firstname = "+"'"+firstname+"'"+" WHERE id = "+id ; 
			String sqlLastName = "UPDATE user SET name = "+"'"+lastname+"'"+" WHERE id = "+id ; 
			String sqlEmail = "UPDATE user SET email = "+"'"+email+"'"+" WHERE id = "+id ; 
			
			if(firstname != "")stmt.executeUpdate(sqlFirstName); 
			if(lastname != "")stmt.executeUpdate(sqlLastName); 
			if(email != "")stmt.executeUpdate(sqlEmail); 
			
			// ma modife commence ici
			String sqlPass = "UPDATE user SET pass = "+"'"+pass+"'"+" WHERE id = "+id ; 
			String sqlSexe = "UPDATE user SET sexe = "+"'"+sexe+"'"+" WHERE id = "+id ; 
			String sqlDateNaissance = "UPDATE user SET datenaissance = "+"'"+DateNaissance+"'"+" WHERE id = "+id ; 

			if(pass != "")stmt.executeUpdate(sqlPass); 
			stmt.executeUpdate(sqlSexe); // pas de verification pour boolean sexe
			if(DateNaissance != 0)stmt.executeUpdate(sqlDateNaissance);
			if(mon_c != null){
			String sqlMon_c = "UPDATE user SET idc = "+"'"+mon_c.getId()+"'"+" WHERE id = "+id ; 
			System.out.println("************"+mon_c.getId());
			stmt.executeUpdate(sqlMon_c); 
			}

			// ma modife termine ici
			
			success = true;
			stmt.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return success;
	}



	//testé
	public ArrayList<User> getUserByFirstName(String firstname) {


		ArrayList<User> contacts = new ArrayList<User>();

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM user WHERE firstname = "+"'"+firstname+"'"); 

			while (rec.next()) {
				User contact = new User();
				DAOContrat mon_contrat= new DAOContrat(); // sans parametres ? 
				
				contact.setId(Integer.parseInt(rec.getString("id")));
				contact.setFirstName(rec.getString("firstname")); 
				contact.setName(rec.getString("name")); 
				contact.setEmail(rec.getString("email")); 
				contact.setPass(rec.getString("pass")); 
				contact.setSexe(Integer.parseInt(rec.getString("sexe"))); // parse boolean n'accepte pas 1= true à voir
				contact.setDateNaissance(Integer.parseInt(rec.getString("datenaissance")));
				
				String s=rec.getString("idc");
				
				if(s != null ){
					 contact.setMon_contrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 contact.setMon_contrat(null);
				}
				
				contacts.add(contact);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contacts;
	}





	//testé
	public ArrayList<User> getUserByLastName(String lastname) {

		ArrayList<User> contacts = new ArrayList<User>();
		User contact = new User();
		DAOContrat mon_contrat= new DAOContrat(); // sans parametres ? 

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
		       
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM user WHERE name = "+"'"+lastname+"'"); 

			while (rec.next()) {
				
				contact.setId(Integer.parseInt(rec.getString("id")));
				contact.setFirstName(rec.getString("firstname")); 
				contact.setName(rec.getString("name")); 
				contact.setEmail(rec.getString("email")); 
				contact.setPass(rec.getString("pass")); 
				contact.setSexe(Integer.parseInt(rec.getString("sexe"))); // parse boolean n'accepte pas 1= true à voir
				contact.setDateNaissance(Integer.parseInt(rec.getString("datenaissance")));
				
				String s=rec.getString("idc");
				
				if(s != null ){
					 contact.setMon_contrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 contact.setMon_contrat(null);
				}
				
				contacts.add(contact);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contacts;
		
	}

	


	//testé
	// renvoie nul si email multiple ou inexistant !
	public User getUserByEmail(String email){

		User contact = new User();
		DAOContrat mon_contrat= new DAOContrat(); // sans parametres ? 
		int duplicat_mail=0;
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM user WHERE email = "+"'"+email+"'"); 

			while (rec.next()) {
				duplicat_mail++;
				contact.setId(Integer.parseInt(rec.getString("id")));
				contact.setFirstName(rec.getString("firstname")); 
				contact.setName(rec.getString("name")); 
				contact.setEmail(rec.getString("email")); 
				contact.setPass(rec.getString("pass")); 
				contact.setSexe(Integer.parseInt(rec.getString("sexe"))); // parse boolean n'accepte pas 1= true à voir
				contact.setDateNaissance(Integer.parseInt(rec.getString("datenaissance")));
				
				String s=rec.getString("idc");
				
				if(s != null ){
					 contact.setMon_contrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 contact.setMon_contrat(null);
				}
				
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		
		if(duplicat_mail == 1){
		return contact;
		}
		if(duplicat_mail == 0){
			System.out.println("Erreur: Aucun User avec ce mail !");
			return null;
		}
		if(duplicat_mail != 0 && duplicat_mail != 1){
			System.out.println("Erreur: plusieurs utilisateurs ont le meme mail !");
			return null;
		}
		return null;
	}

	
	//testé
	public ArrayList<User> getUserByFisrtAndLastName(String firstname, String lastname) {

		ArrayList<User> contacts = new ArrayList<User>();
		User contact = new User();
		DAOContrat mon_contrat= new DAOContrat(); // sans parametres ? 

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			// executeQuery ou executeUpdate ?
			rec = stmt.executeQuery("SELECT * FROM user WHERE name = "+"'"+lastname+"' AND firstname ='"+firstname+"'");  // requette correcte ? AND ?

			while (rec.next()) {
				
				contact.setId(Integer.parseInt(rec.getString("id")));
				contact.setFirstName(rec.getString("firstname")); 
				contact.setName(rec.getString("name")); 
				contact.setEmail(rec.getString("email")); 
				contact.setPass(rec.getString("pass")); 
				contact.setSexe(Integer.parseInt(rec.getString("sexe"))); // parse boolean n'accepte pas 1= true à voir
				contact.setDateNaissance(Integer.parseInt(rec.getString("datenaissance")));
				
				String s=rec.getString("idc");
				
				if(s != null ){
					 contact.setMon_contrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 contact.setMon_contrat(null);
				}
				
				contacts.add(contact);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contacts;
		
		
	}


	
	
	
}
