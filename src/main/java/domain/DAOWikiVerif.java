package domain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import metier.*;

/*
 * 
 *  un WikiVerif est valable 24h max !
 * 
 */

public class DAOWikiVerif implements IDAOWikiVerif {

	public WikiVerif addWikiVerif(Station s, User u, int velos, int places, Date dandh, String commentaire)
					
	{
			WikiVerif wiki = new WikiVerif();
			
			wiki.setCommentaire(commentaire);
			wiki.setDandh(dandh);
			wiki.setPlaces(places);
			wiki.setS(s);
			wiki.setU(u);
			wiki.setVelos(velos);
			ResultSet rec = null;
			Connection con = null;
			try{
				String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			       Class.forName("com.mysql.jdbc.Driver");
			       con = DriverManager.getConnection(
			                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

				Statement stmt_test = con.createStatement();
				String req;
				if ((u != null) & (s !=null)){
				req = "INSERT INTO wikiverif (dandh, velos , places, commentaire, ids, idu) VALUES('"+dandh+"','"+velos+"','"+places+"','"+commentaire+"','"+s.getId()+"','"+u.getId()+"' )";
				}else{
				req = "INSERT INTO wikiverif (dandh, velos , places, commentaire) VALUES('"+dandh+"','"+velos+"','"+places+"','"+commentaire+"')"; 
				}
				stmt_test.executeUpdate(req);
				
				Statement stmt_id = con.createStatement();
				rec = stmt_id.executeQuery("SELECT max(id) FROM wikiverif"); // retourne le id maximum;
				while (rec.next()) {
				wiki.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
				}
				stmt_id.close(); 
			
				
				
				stmt_test.close(); 
				con.close();
			} catch( Exception e ){
				e.printStackTrace();
			}

			return wiki;
	}

	public int deleteWikiVerif(int id) {
		

		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
 
			Statement stmt = con.createStatement();
			String request = "DELETE FROM wikiverif WHERE id = "+id ; 
			success = stmt.executeUpdate(request);
			stmt.close();
			con.close();
			
		} catch( Exception e ){
			e.printStackTrace();
		}

		return success;
		
	}

	public WikiVerif getWikiVerif(int id) {
		
		ResultSet rec = null;
		WikiVerif wiki = new WikiVerif();
		DAOStation st= new DAOStation();
		DAOUser us= new DAOUser();
		// créer l'objet Contrat qui sera chercher dans la bd par getContrat()
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE id = "+id); 

			while (rec.next()) {
				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire"));
				wiki.setPlaces(Integer.parseInt(rec.getString("places")));
				wiki.setVelos(Integer.parseInt(rec.getString("velos")));
				
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date result =  df.parse(rec.getString("dandh")); 
				wiki.setDandh(result);
				
				String stac=rec.getString("ids");
				
				if(stac != null ){
					 wiki.setS(st.getStation(Integer.parseInt(stac)));
				}else{
					 wiki.setS(null);
				}
				
              String user=rec.getString("idu");
				
				if(user != null ){
					 wiki.setU(us.getUser(Integer.parseInt(user)));
				}else{
					 wiki.setU(null);
				}
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return wiki;
		
		
		
	}

	public boolean modifyWikiVefif(int id, Station s, User u, int velos, int places, Date dandh, String commentaire) {
		
		
		Connection con = null;
		try{	
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE wikiverif SET commentaire='"+commentaire+"'  where id='"+id+"' ";
			String reqK = "UPDATE wikiverif SET ids='"+s.getId()+"' where id="+id;
			String sqlB = "UPDATE  wikiverif SET idu='"+u.getId()+"' WHERE id = "+id ; 
			String sqlL = "UPDATE wikiverif SET velos='"+velos+"' WHERE id = "+id ; 
			String req1 = "UPDATE wikiverif SET dandh='"+dandh+"' where id="+id;
			String sql2= "UPDATE  wikiverif SET places='"+places+"' WHERE id = "+id ; 
			stmt_test.executeUpdate(reqK);
			stmt_test.executeUpdate(sqlB);
			stmt_test.executeUpdate(sqlL);
			stmt_test.executeUpdate(sql2);
			stmt_test.executeUpdate(req1);
			stmt_test.executeUpdate(req);
			
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;
		
	}

	public ArrayList<WikiVerif> getWikiVerifByUser(User user) {
		
		ArrayList<WikiVerif> wikis = new ArrayList<WikiVerif>();

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE idu="+user.getId() +" ORDER BY id DESC "); 

			while (rec.next()) {
				WikiVerif wiki = new WikiVerif();
				DAOStation station= new DAOStation(); // sans parametres ? 
				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire")); 
			
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date result =  df.parse(rec.getString("dandh")); 
				wiki.setDandh(result);
				
				wiki.setPlaces(Integer.parseInt(rec.getString("places")));
				wiki.setVelos(Integer.parseInt(rec.getString("velos")));
				wiki.setU(user);
				wiki.setS(station.getStation(Integer.parseInt(rec.getString("ids"))));
		
				
				wikis.add(wiki);
			}


		} catch( Exception e ){
			e.printStackTrace();
		}
		return wikis;
		
	}

	public ArrayList<WikiVerif> getWikiVerifByStation(Station station) {
		
		ArrayList<WikiVerif> wikis = new ArrayList<WikiVerif>();

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE ids="+station.getId()); 

			while (rec.next()) {
				WikiVerif wiki = new WikiVerif();
				DAOUser user= new DAOUser();
				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire")); 
			//	wiki.setDanh(rec.getString("lastname")); 
				wiki.setPlaces(Integer.parseInt(rec.getString("places"))); 
				wiki.setVelos(Integer.parseInt(rec.getString("velos"))); 
				wiki.setS(station);
				wiki.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				
				wikis.add(wiki);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return wikis;
		
		
	}

	public ArrayList<WikiVerif> getWikiVerifByUserStation(User user, Station station) {
	
		
		ArrayList<WikiVerif> wikis= new ArrayList<WikiVerif>();
		ResultSet rec = null;
		Connection con = null;
		
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE (idu, ids) IN ( '"+user.getId()+"',  '"+station.getId()+"') "); 
				while(rec.next()){
					WikiVerif wiki= new WikiVerif();
					
					wiki.setId(Integer.parseInt(rec.getString("id")));
					wiki.setCommentaire(rec.getString("commentaire")); 
					
					DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
				    Date result =  df.parse(rec.getString("dandh")); 
					wiki.setDandh(result);
					
					wiki.setPlaces(Integer.parseInt(rec.getString("places"))); 
					wiki.setVelos(Integer.parseInt(rec.getString("velos"))); 
					wiki.setS(station);
					wiki.setU(user);
				
				wikis.add(wiki);
				}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return wikis;
		
		
	}

	public ArrayList<WikiVerif> getWikiVerifByDate(Date dandh) { // juste le jour normalement
		
		ResultSet rec = null;
		Connection con = null;
		WikiVerif wiki = new WikiVerif ();
		DAOStation station= new DAOStation(); // sans parametres ? 
		ArrayList<WikiVerif> wikis= new ArrayList<WikiVerif>();
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE dandh='"+dandh+"' "); 
    
			while(rec.next()){

				DAOUser user= new DAOUser();
				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire")); 
				wiki.setDandh(dandh);
				wiki.setPlaces(Integer.parseInt(rec.getString("places")));
				wiki.setVelos(Integer.parseInt(rec.getString("velos")));
				wiki.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				wiki.setS(station.getStation(Integer.parseInt(rec.getString("ids"))));
				
				wikis.add(wiki);
				
			}
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return wikis;
		
	}

	public WikiVerif getWikiVerifByUserStationDate(User user, Station station,	Date dandh) {
		
		WikiVerif wiki = new WikiVerif();
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE (idu, ids, dandh) IN ( '"+user.getId()+"',  '"+station.getId()+"', '"+dandh+"' "); 

				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire")); 
				wiki.setDandh(dandh);
				wiki.setPlaces(Integer.parseInt(rec.getString("places")));
				wiki.setVelos(Integer.parseInt(rec.getString("velos"))); 
				wiki.setU(user);
				wiki.setS(station);
				
				
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return wiki;
		
		
		
	}

	
}
