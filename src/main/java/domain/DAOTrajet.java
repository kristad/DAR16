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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
			Statement stmt_test = con.createStatement();
			String request_test;
			if ((u != null) & (sa !=null) & (sd != null)){
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

	public int deleteTrajet(int id) {
		
		int success=0;
		Connection con = null;
		try{
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			System.out.println("dans DAOstation, find trajet, start ************");
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
			Statement stmt_test = con.createStatement();
			String res = "SELECT * from trajet where id="+id;
			
			ResultSet req=stmt_test.executeQuery(res);
			
			while (req.next()) {
			st.setCommentaire(req.getString("commentaire"));
			
			DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
		    Date result =  df.parse(req.getString("dandh")); 
			st.setDanh(result);
			
			DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
		    Date resulta =  dfa.parse(req.getString("heurea")); 
			st.setHeureA(resulta);
			
			DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
		    Date resultd =  dfd.parse(req.getString("heured")); 
			st.setHeured(resultd);
			
			DAOStation stacion=new DAOStation();
			
			String staca=req.getString("idsa");
			
			if(staca != null ){
				 st.setSa(stacion.getStation(Integer.parseInt(staca)));
			}else{
				 st.setSa(null);
			}
			
          String stacd =req.getString("idsd");
			
			if(stacd != null ){
				 st.setSd(stacion.getStation(Integer.parseInt(stacd)));
			}else{
				 st.setSd(null);
			}
			
			DAOUser us= new DAOUser();
			
          String user=req.getString("idu");
			
			if(user != null ){
				 st.setU(us.getUser(Integer.parseInt(user)));
			}else{
				 st.setU(null);
			}
			}
	
			req.close();

			stmt_test.close(); 
			con.close();
			System.out.println("dans DAOstation, deletestation, fin ************");
		} catch( Exception e ){
			e.printStackTrace();
		}
		return st;
		
	}


	public ArrayList<Trajet> getTrajetsByStationDepart(Station sd) {
		
		ArrayList<Trajet> trajets = new ArrayList<Trajet>();

		ResultSet rec = null;
		Connection con = null;
		try{
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
		try{
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM trajet WHERE idu="+u.getId()); 

			while (rec.next()) {
				Trajet trajet = new Trajet();
				DAOStation station= new DAOStation(); // sans parametres ? 
				
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
				trajet.setU(u);
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

	public Trajet getTrajetByStationUser(Station stationd , Station stationa , User user) {
		

		ResultSet rec = null;
		Connection con = null;
		Trajet trajet = new Trajet();
		try{
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
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
			Class.forName(Messages.getString("driver")); 
			con = DriverManager.getConnection(Messages.getString("database"), Messages.getString("username"), Messages.getString("password")); 
			Statement stmt_test = con.createStatement();
			String req_test = "SELECT id from trajet where id='"+id+"' ";
			int idTrajet= stmt_test.executeUpdate(req_test);
			String req = "UPDATE from trajet SET idsd='"+sd.getId()+"', idsa='"+sa.getId()+"', idu='"+u.getId()+"', etatTrajet='"+etatTrajet+"', danhd='"+danh+"',heurea='"+heureA+"', heured='"+heured+"', commentaire='"+commentaire+"'  where id='"+idTrajet+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;

		
	}

	


	
}
