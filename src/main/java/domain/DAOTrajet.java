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

import metier.Station;
import metier.Trajet;
import metier.User;

public class DAOTrajet implements IDAOTrajet{

	public Trajet addTrajet(Station sd, Station sa, User u, Date danh, Date heured, Date heureA, int etatTrajet, String commentaire) {
		

		Trajet trajet = new Trajet();
		
		trajet.setDanh(danh);
		trajet.setEtatTrajet(etatTrajet);
		trajet.setHeureA(heureA);
		trajet.setHeured(heured);
		trajet.setSa(sa);
		trajet.setSd(sd);
		trajet.setU(u); 
		trajet.setCommentaire(commentaire);
	

		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String request_test;
			if ((u != null) && (sa !=null) && (sd != null)){
			request_test = "INSERT INTO trajet (dandh, heured, heurea, commentaire, etatTrajet , idsa, idsd, idu) VALUES('"+danh+"','"+heured+"','"+heureA+"','"+commentaire+"','"+etatTrajet+"','"+sa.getId()+"','"+sd.getId()+"', '"+u.getId()+"' )";
			}else{
			request_test = "INSERT INTO trajet(dandh, heured, heurea, commentaire, etatTrajet ) VALUES('"+danh+"','"+heured+"','"+heureA+"','"+commentaire+"','"+etatTrajet+"')"; 
			}
			stmt_test.executeUpdate(request_test);
			ResultSet rec;
			Statement stmt_id = con.createStatement();
			rec = stmt_id.executeQuery("SELECT max(id) FROM trajet"); // retourne le id maximum;
			while (rec.next()) {
			trajet.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}

		return trajet;
		
		
		
	}
	

	public Trajet addTrajet(Station sd, Station sa, User u) {

		Trajet trajet = new Trajet();
		
		trajet.setSa(sa);
		trajet.setSd(sd);
		trajet.setU(u); 

	

		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String request_test="";
			
			if ((u != null) && (sd != null)){
			request_test = "INSERT INTO trajet (idsd, idu) VALUES('"+sd.getId()+"' , '"+u.getId()+"' )";
			}
			if ((u != null) && (sa !=null)){
			request_test = "INSERT INTO trajet (idsa, idu) VALUES('"+sa.getId()+"' , '"+u.getId()+"' )";	
			}
			stmt_test.executeUpdate(request_test);
			ResultSet rec;
			Statement stmt_id = con.createStatement();
			rec = stmt_id.executeQuery("SELECT max(id) FROM trajet"); // retourne le id maximum;
			while (rec.next()) {
			trajet.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}

		return trajet;
		
		
		
	}

	public int deleteTrajet(int id) {
		
		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			String request = "DELETE FROM trajet WHERE id = "+id ; 
			success = stmt.executeUpdate(request);
			stmt.close();
			con.close();
			
		} catch( Exception e ){
			e.printStackTrace();
		}

		return success;
	}

	public Trajet getTrajet(int id) {
		
		Trajet st= new Trajet();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String res = "SELECT * from trajet where id="+id;
			
			ResultSet req=stmt_test.executeQuery(res);
			
			while (req.next()) {
				try{
					st.setCommentaire(req.getString("commentaire"));
				}catch(Exception e){}
				
			try{
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
				Date result =  df.parse(req.getString("dandh")); 
				st.setDanh(result);
			}catch(Exception e){}
			
			try{
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
				Date resulta =  dfa.parse(req.getString("heurea")); 
				st.setHeureA(resulta);
			}catch(Exception e){}
			try{
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
				Date resultd =  dfd.parse(req.getString("heured")); 
				st.setHeured(resultd);
			}catch(Exception e){}
			
			DAOStation stacion=new DAOStation();
			
			String staca=req.getString("idsa");
			
			try{
			if(staca != null ){
				 st.setSa(stacion.getStation(Integer.parseInt(staca)));
			}else{
				 st.setSa(null);
			}
			}catch(Exception e){}
			
			try{
          String stacd =req.getString("idsd");
			
			if(stacd != null ){
				 st.setSd(stacion.getStation(Integer.parseInt(stacd)));
			}else{
				 st.setSd(null);
			}
			}catch(Exception e){}
			
			DAOUser us= new DAOUser();
			
			try{
          String user=req.getString("idu");
			
			if(user != null ){
				 st.setU(us.getUser(Integer.parseInt(user)));
			}else{
				 st.setU(null);
			}
			}catch(Exception e){}
			
			st.setId(id);
			
			req.close();

			stmt_test.close(); 
			con.close();
			return st;
			}

			System.out.println("dans DAOstation, deletestation, fin ************" + st.getId());
			}catch( Exception e ){
			e.printStackTrace();
		}
		return null;
		
	}


	public ArrayList<Trajet> getTrajetsByStationDepart(Station sd) {
		
		ArrayList<Trajet> trajets = new ArrayList<Trajet>();

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE idsd="+sd.getId()); 

			while (rec.next()) {
				Trajet st = new Trajet();
				DAOStation station= new DAOStation(); // sans parametres ? 
				DAOUser user= new DAOUser();
				
				st.setId(Integer.parseInt(rec.getString("id")));
				st.setCommentaire(rec.getString("commentaire")); 
			
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date result =  df.parse(rec.getString("dandh")); 
				st.setDanh(result);
				
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resulta =  dfa.parse(rec.getString("heurea")); 
				st.setHeureA(resulta);
				
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resultd =  dfd.parse(rec.getString("heured")); 
				st.setHeured(resultd);
				
				
				st.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet"))); 
				st.setSd(sd);
				st.setSa(station.getStation(Integer.parseInt(rec.getString("idsa"))));
				st.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				
				trajets.add(st);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajets;
	}
	
public ArrayList<Trajet> getTrajetsByStationArrive(Station sa) {
		
		ArrayList<Trajet> trajets = new ArrayList<Trajet>();

		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE idsa="+sa.getId()); 

			while (rec.next()) {
				Trajet st = new Trajet();
				DAOStation station= new DAOStation(); // sans parametres ? 
				DAOUser user= new DAOUser();
				
				st.setId(Integer.parseInt(rec.getString("id")));
				st.setCommentaire(rec.getString("commentaire")); 
			
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date result =  df.parse(rec.getString("dandh")); 
				st.setDanh(result);
				
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resulta =  dfa.parse(rec.getString("heurea")); 
				st.setHeureA(resulta);
				
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resultd =  dfd.parse(rec.getString("heured")); 
				st.setHeured(resultd);
				
				
				st.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet"))); 
				st.setSa(sa);
				st.setSd(station.getStation(Integer.parseInt(rec.getString("idsd"))));
				st.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				
				trajets.add(st);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajets;
	}

	public ArrayList<Trajet> getTrajetByUser(User u) {
		
		ArrayList<Trajet> trajets = new ArrayList<Trajet>();

		ResultSet rec = null;
		Connection con = null;
		
		DAOStation station= new DAOStation(); // sans parametres ? 
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE idu="+u.getId() + " ORDER BY id DESC "); 

			while (rec.next()) {
				Trajet trajet = new Trajet();
				try{
				trajet.setId(Integer.parseInt(rec.getString("id")));
				}catch(Exception e){}
				try{
				trajet.setCommentaire(rec.getString("commentaire")); 
				}catch(Exception e){}
			
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
				try{
			    Date result =  df.parse(rec.getString("dandh")); 
				trajet.setDanh(result);
				}catch(Exception e){}
				try{
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resulta =  dfa.parse(rec.getString("heurea")); 
				trajet.setHeureA(resulta);
				}catch(Exception e){}
				try{
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resultd =  dfd.parse(rec.getString("heured")); 
				trajet.setHeured(resultd);
				}catch(Exception e){}
				try{
				trajet.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet"))); 
				}catch(Exception e){}
				trajet.setU(u);
				try{
				trajet.setSa(station.getStation(Integer.parseInt(rec.getString("idsa"))));
				}catch(Exception e){}
				try{
				trajet.setSd(station.getStation(Integer.parseInt(rec.getString("idsd"))));
				}catch(Exception e){}
				
				trajets.add(trajet);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajets;
		
		
	}

	public Trajet getTrajetByStationUser(Station stationd , Station stationa , User user) {
		

		ResultSet rec = null;
		Connection con = null;
		Trajet trajet = new Trajet();
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE (idu, idsa, idsd) IN ( '"+user.getId()+"',  '"+stationa.getId()+"', '"+stationd.getId()+"') "); 

				trajet.setId(Integer.parseInt(rec.getString("id")));
				trajet.setCommentaire(rec.getString("commentaire")); 
	
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date result =  df.parse(rec.getString("dandh")); 
				trajet.setDanh(result);
				
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resulta =  dfa.parse(rec.getString("heurea")); 
				trajet.setHeureA(resulta);
				
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resultd =  dfd.parse(rec.getString("heured")); 
				trajet.setHeured(resultd);
				
				trajet.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet"))); 
				trajet.setU(user);
				trajet.setSa(stationa);
				trajet.setSd(stationd);
				

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajet;
		
		
	}

	public Trajet getTrajet(Station stationdepart, User user, Date heured) {
		
		
		Trajet trajet = new Trajet();
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE (idsu, idsd, heured) IN ( '"+user.getId()+"',  '"+stationdepart.getId()+"', '"+heured+"' "); 

			DAOStation station= new DAOStation(); // sans parametres ? 
				
				trajet.setId(Integer.parseInt(rec.getString("id")));
				trajet.setCommentaire(rec.getString("commentaire")); 
			//	trajet.setDanh(rec.getString("lastname"));
				trajet.setHeured(heured);
				trajet.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet"))); 
				trajet.setU(user);
				trajet.setSa(station.getStation(Integer.parseInt(rec.getString("idsa"))));
				trajet.setSd(stationdepart);
				
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajet;
		
		
	}

	public ArrayList<Trajet> getTrajetByEtat(int etatTrajet) {
		
		ResultSet rec = null;
		Connection con = null;
		ArrayList<Trajet> trajets= new ArrayList<Trajet>();
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE etatTrajet='"+etatTrajet+"' "); 
    
			while(rec.next()){
				Trajet trajet = new Trajet();
				DAOStation station= new DAOStation(); // sans parametres ? 
				DAOUser user= new DAOUser();
				
				trajet.setId(Integer.parseInt(rec.getString("id")));
				trajet.setCommentaire(rec.getString("commentaire")); 
			//	trajet.setDanh(rec.getString("lastname")); 
				trajet.setEtatTrajet(etatTrajet); 
				trajet.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				trajet.setSa(station.getStation(Integer.parseInt(rec.getString("idsa"))));
				trajet.setSd(station.getStation(Integer.parseInt(rec.getString("idsd"))));
				
				trajets.add(trajet);
				
			}
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajets;
		
	}

	
	public ArrayList<Trajet> getTrajetByDate(Date danh) {
		
		ResultSet rec = null;
		Connection con = null;
		ArrayList<Trajet> trajets= new ArrayList<Trajet>();
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE dandh='"+danh+"' "); 
    
			while(rec.next()){
				Trajet trajet = new Trajet();
				DAOStation station= new DAOStation(); // sans parametres ? 
				DAOUser user= new DAOUser();
				
				trajet.setId(Integer.parseInt(rec.getString("id")));
				trajet.setCommentaire(rec.getString("commentaire")); 
			//	trajet.setDanh(rec.getString("lastname")); 
				trajet.setDanh(danh);
				trajet.setEtatTrajet(Integer.parseInt(rec.getString("etatTrajet")));
				trajet.setU(user.getUser(Integer.parseInt(rec.getString("idu"))));
				trajet.setSa(station.getStation(Integer.parseInt(rec.getString("idsa"))));
				trajet.setSd(station.getStation(Integer.parseInt(rec.getString("idsd"))));
				
				trajets.add(trajet);
				
			}
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return trajets;
		
		
	}

	public boolean modifyTrajet(int id, Station sd, Station sa, User u,
			Date danh, Date heured, Date heureA, int etatTrajet, String commentaire) {
		
		Connection con = null;
		try{	
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req_test = "SELECT id from trajet where id='"+id+"' ";
			ResultSet tr= stmt_test.executeQuery(req_test);
			int idTrajet=0;
			while(tr.next())
			{
				idTrajet=Integer.parseInt(tr.getString("id"));
			}
			
			String req = "UPDATE trajet SET idsd='"+sd.getId()+"', idsa='"+sa.getId()+"', idu='"+u.getId()+"', etatTrajet='"+etatTrajet+"', danhd='"+danh+"',heurea='"+heureA+"', heured='"+heured+"', commentaire='"+commentaire+"'  where id='"+idTrajet+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;

		
	}
	
	public boolean modifyTrajetSA(int id, Station sa) {
		
		Connection con = null;
		try{	
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE trajet SET idsa='"+sa.getId()+"' WHERE id='"+id +"'";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;

		
	}
	
	public boolean modifyTrajetSD(int id, Station sd) {
		
		Connection con = null;
		try{	
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE trajet SET idsd='"+sd.getId()+"' WHERE id='"+id +"'";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;

		
	}

	


	
}
