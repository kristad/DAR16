package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import metier.Contrat;




/* 

Contracts

For each client agglomeration of JCDecaux, there's an associated contract:

{
  "name" : "Paris",
  "commercial_name" : "Vélib'",
  "country_code" : "FR",
  "cities" : [
    "Paris",
    "Neuilly",
    ...
  ]
}

*/


public class DAOContrat implements IDAOContrat {

	public Contrat addContrat(String country_code, String commercial_name,
			String contract_name, String cities) {

		
		Contrat contrat = new Contrat();
		
		contrat.setCities(cities);
		contrat.setContract_name(contract_name);
		contrat.setCommercial_name(commercial_name);
		contrat.setCountry_code(country_code);
		
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
			request_test = "INSERT INTO contrat(contract_name, cities, commercial_name,country_code) VALUES('"+contract_name+"','"+cities+"','"+commercial_name+"','"+country_code+"')";
			stmt_test.executeUpdate(request_test); // ligne ajouté dans la bd, faire une requette pour le max id
			
			// la modife commence ici

			Statement stmt_id = con.createStatement();
			rec = stmt_id.executeQuery("SELECT max(id) FROM contrat"); // retourne le id maximum;
			while (rec.next()) {
			contrat.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			stmt_id.close(); 
			rec.close();
			// la modife termine ici	
			
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		return contrat;
		
	}

	public int deleteContrat(int id) {
	
		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			String request = "DELETE FROM contrat WHERE id = "+id; 
			success = stmt.executeUpdate(request);
			stmt.close();
			con.close();
			
		} catch( Exception e ){
			e.printStackTrace();
		}

		return success;
	}
	
	public int deleteContratByContratName(String c) {
		
		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			String request = "DELETE FROM contrat WHERE contract_name='"+c+"' "; 
			success = stmt.executeUpdate(request);
			stmt.close();
			con.close();
			
		} catch( Exception e ){
			e.printStackTrace();
		}

		return success;
	}
	
	

	public Contrat getContrat(int id) {
		ResultSet rec = null;
		Contrat contrat = new Contrat();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM contrat WHERE id = "+id); 

			while (rec.next()) {
			
				contrat.setId(Integer.parseInt(rec.getString("id")));
				contrat.setCities(rec.getString("cities"));
				contrat.setContract_name(rec.getString("contract_name"));
				contrat.setCommercial_name(rec.getString("commercial_name"));
				contrat.setCountry_code(rec.getString("country_code"));
				}
			

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contrat;
	}

	public boolean modifyContrat(int id, String country_code,String commercial_name, String contract_name, String citie) {
		
		boolean success = false;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			String sqlCountry_code = "UPDATE contrat SET country_code = "+"'"+country_code+"'"+" WHERE id = "+id ; 
			String sqlcommercial = "UPDATE contrat SET commercial_name = "+"'"+commercial_name+"'"+" WHERE id = "+id ; 
			String sqlcname = "UPDATE contrat SET contract_name = "+"'"+contract_name+"'"+" WHERE id = "+id ; 
			
			if(country_code != "")stmt.executeUpdate(sqlCountry_code); 
			if(commercial_name != "")stmt.executeUpdate(sqlcommercial); 
			if(contract_name != "")stmt.executeUpdate(sqlcname); 
			
			success = true;
			stmt.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return success;
		
	}

	public Contrat getContratByCommercialName(String commercial_name) {
		
		
		Contrat contrat = new Contrat();
		int duplicat_contrat=0;
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM contrat WHERE commercial_name='"+commercial_name+"'"); 

			while (rec.next()) {
				duplicat_contrat++;
				contrat.setId(Integer.parseInt(rec.getString("id")));
				contrat.setContract_name(rec.getString("contract_name")); 
				contrat.setCities(rec.getString("cities")); 
				contrat.setCountry_code(rec.getString("country_code"));
				
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		
		if(duplicat_contrat == 1){
		return contrat;
		}
		if(duplicat_contrat == 0){
			System.out.println("Erreur: Aucun User avec ce mail !");
			return null;
		}
		if(duplicat_contrat != 0 && duplicat_contrat != 1){
			System.out.println("Erreur: plusieurs utilisateurs ont le meme mail !");
			return null;
		}
		return null;
	}

	public Contrat getContratByContractName(String contract_name) {
		Contrat contrat = new Contrat();
		int duplicat_contrat=0;
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM contrat WHERE contract_name= "+"'"+contract_name+"'"); 

			while (rec.next()) {
				duplicat_contrat++;
				contrat.setId(Integer.parseInt(rec.getString("id")));
				contrat.setContract_name(rec.getString("contract_name")); 
				contrat.setCities(rec.getString("cities")); 
				contrat.setCountry_code(rec.getString("country_code"));
				
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		
		if(duplicat_contrat == 1){
		return contrat;
		}
		if(duplicat_contrat == 0){
			System.out.println("Erreur: Aucun User avec ce mail !");
			return null;
		}
		if(duplicat_contrat != 0 && duplicat_contrat != 1){
			System.out.println("Erreur: plusieurs utilisateurs ont le meme mail !");
			return null;
		}
		return null;
		
		
	}

	public ArrayList<Contrat> getContratByCountryName(String country_code) {
		
		
		
		ArrayList<Contrat> contrats = new ArrayList<Contrat>();
	

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM contrat WHERE country_code = "+"'"+country_code+"'"); 

			while (rec.next()) {
				Contrat contrat = new Contrat();	
				contrat.setId(Integer.parseInt(rec.getString("id")));
				contrat.setContract_name(rec.getString("contract_name")); 
				contrat.setCities(rec.getString("cities")); 
				contrat.setCommercial_name(rec.getString("commercial_name"));
				
				contrats.add(contrat);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return contrats;
	}



}
