package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import metier.Contrat;
import metier.Station;
import metier.WikiVerif;

import java.lang.String;

/*
 
Stations

Stations are represented as follows:

{
  "number": 123,
  "contract_name" : "Paris",
  "name": "stations name",
  "address": "address of the station",
  "position": {
    "lat": 48.862993,
    "lng": 2.344294
  },
  "banking": true,
  "bonus": false,
  "status": "OPEN",
  "bike_stands": 20,
  "available_bike_stands": 15,
  "available_bikes": 5,
  "last_update": <timestamp>
}

*/

public class DAOStation implements IDAOStation{
	


	public Station addStation( long number,	String contract_name, String name, String address, double longitude, double latitude, Contrat contrat)
			{
		
//int lastId=0;
		
		Station st = new Station();
		
		st.setAddress(address);
		st.setContract_name(contract_name);
		st.setLat(latitude);
		st.setLng(longitude);
		st.setName(name);
		st.setNumber(number);
		st.setContrat(contrat);
		
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
			if (contrat == null){
			// le idc sera à null automatiquement, attention au get
			request_test = "INSERT INTO station(name, contract_name, address, lat, lng , number) VALUES('"+name+"','"+contract_name+"','"+address+"','"+latitude+"','"+longitude+"','"+number+"')";
			}else{
			request_test = "INSERT INTO station(name, contract_name, address, lat, lng , number, idc) VALUES('"+name+"','"+contract_name+"','"+address+"','"+latitude+"','"+longitude+"','"+number+"', '"+contrat.getId()+"')";
			}
			stmt_test.executeUpdate(request_test); // ligne ajouté dans la bd, faire une requette pour le max id
			
			// la modife commence ici

			Statement stmt_id = con.createStatement();
			rec = stmt_id.executeQuery("SELECT max(id) FROM station"); // retourne le id maximum;
			while (rec.next()) {
			st.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			stmt_id.close(); 
			rec.close();
			// la modife termine ici	
			
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		return st;
		
			}

	public void deleteStation(int id) {
		
		
		
		Connection con = null;
		try{
			System.out.println("dans DAOstation, deletestation, start ************");
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "DELETE  from station where id='"+id+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
	}
// à changer vers int c
public void deleteStationByContratname(String c) {
		
		
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "DELETE  from station where contract_name='"+c+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}

		
		
	}
	
	public Station getStation(int id) { // get toutes les données
		
		
		ResultSet rec = null;
		Station st = new Station();
		DAOContrat mon_contrat= new DAOContrat();  // créer l'objet Contrat qui sera chercher dans la bd par getContrat()
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM station WHERE id = "+id); 

			while (rec.next()) {
				try{
				st.setAddress(rec.getString("address"));
				}catch(Exception e){}
				try{
				st.setNumber(Long.parseLong(rec.getString("number")));
				}catch(Exception e){}
				//st.setContract_name(rec.getString("contract_name"));
				try{
				st.setName(rec.getString("name"));
				}catch(Exception e){}
				try{
				st.setLat(Double.parseDouble(rec.getString("lat")));
				st.setLng(Double.parseDouble(rec.getString("lng")));
				}catch(Exception e){}
				try{
				String s=rec.getString("idc");
				
				if(s != null ){
					 st.setContrat(mon_contrat.getContrat(Integer.parseInt(s)));
				}else{
					 st.setContrat(null);
				}
				}catch(Exception e){}

				
			// nouveauté
				try{
				st.setStatus( ( (rec.getString("status")).equals("1") )? true : false);
				}catch(Exception e){}
				try{
				st.setAvailable_bike_stands(Integer.parseInt(rec.getString("available_bike_stands")));
				}catch(Exception e){}
				try{
				st.setBike_stands(Integer.parseInt(rec.getString("bike_stands")));
				}catch(Exception e){}
				try{
				st.setAvailable_bikes(Integer.parseInt(rec.getString("available_bikes")));
				}catch(Exception e){}
				try{
				st.setUVelosDispos(Integer.parseInt(rec.getString("UVelosDispos")));
				}catch(Exception e){}
				try{
				st.setUPlacesDispos(Integer.parseInt(rec.getString("UPlacesDispos")));
				}catch(Exception e){}
				try{
				st.setBanking( ( (rec.getString("banking")).equals("1") )? true : false);
				}catch(Exception e){}
				try{
				st.setBonus( ((rec.getString("bonus")).equals("1"))? true : false);
				}catch(Exception e){}


				try{
				DateFormat dfd = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date td =  dfd.parse(rec.getString("available_bikes"));
				st.setLast_update(td);
				}catch(Exception e){}
				// normalement boucler sur les wiki verifs ou le faire directement dans le FrequenceDA
				// donc c'est à éffacer ça ?
				
				//Set<WikiVerif> UVD; 	
				// Set<WikiVerif> UPD;													
	
			}
				stmt.close();
				rec.close();
				con.close();
				
		} catch( Exception e ){
			e.printStackTrace();
		}
		st.setId(id);
		try{
		st.setContract_name(st.getMon_Contrat().getContract_name());
		}catch(Exception e){
		}
		return st;
		
	}

	public boolean modifyStation(int id,long number, String contract_name, String name, String address,
			double lng, double lat,  int bike_stands) {
		
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req_test = "SELECT id from contrat where name='contract_name' ";
			int idStat= stmt_test.executeUpdate(req_test);
			String req = "UPDATE from station SET name='"+name+"', number='"+number+"', contract_name='"+address+"', contract_name='"+address+"', lat='"+lat+"',lng='"+lng+"', idc='"+idStat+"',contract_name='"+address+"', where id='"+id+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return true;
	}

	public Station getStationByNumber(long number) {
		
		Station st= new Station();
		Connection con = null;
		try{

			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String res = "SELECT * from station where number='"+number+"' ";
			ResultSet req=stmt_test.executeQuery(res);
			while (req.next()){
			st.setId(Integer.parseInt(req.getString("id")));
			st.setNumber(Long.parseLong(req.getString("number")));
			st.setAddress(req.getString("address"));
			st.setContract_name(req.getString("contract_name"));
			st.setName(req.getString("name"));
			st.setLat(Double.parseDouble(req.getString("lat")));
			st.setLng(Double.parseDouble(req.getString("lng")));
			}
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return st;
		
		
		
	}

	public ArrayList<Station> getStatiosByContrat(Contrat cont) {
		
		ArrayList<Station> stations= new ArrayList<Station>();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "SELECT * from station where idc='"+cont.getId()+"' ";
			ResultSet res=stmt_test.executeQuery(req);
			int count=0;
			while (res.next()){
				count++;
				Station st= new Station();
			st.setNumber(Long.parseLong(res.getString("number")));
			st.setAddress(res.getString("address"));
			st.setMon_Contrat(cont);
			st.setName(res.getString("name"));
			st.setLat(Double.parseDouble(res.getString("lat")));
			st.setLng(Double.parseDouble(res.getString("lng")));
			stations.add(st);
			}
			stmt_test.close(); 
			con.close();
			System.out.println(count);
		} catch( Exception e ){
			e.printStackTrace();
		}
		return stations;
	}
	


	public Station getStationByName(String name) {
		
		Station st= new Station();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String res = "SELECT * from station where name='"+name+"' ";
			ResultSet req=stmt_test.executeQuery(res);
			st.setNumber(Long.parseLong(req.getString("number")));
			st.setAddress(req.getString("address"));
			st.setContract_name(req.getString("contract_name"));
			st.setName(req.getString("name"));
			st.setLat(Double.parseDouble(req.getString("lat")));
			st.setLng(Double.parseDouble(req.getString("lng")));
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return st;
		
	}

	public Station getStationByAddress(String address) {
		Station st= new Station();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String res = "SELECT * from station where address='"+address+"' ";
			ResultSet req=stmt_test.executeQuery(res);
			while (req.next()){
			st.setNumber(Long.parseLong(req.getString("number")));
			st.setAddress(req.getString("address"));
			st.setContract_name(req.getString("contract_name"));
			st.setName(req.getString("name"));
			st.setLat(Double.parseDouble(req.getString("lat")));
			st.setLng(Double.parseDouble(req.getString("lng")));
			}
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return st;
	}

	public Station getStationByPosition(String lat, String lng) {
		Station st= new Station();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String res = "SELECT * from station where (lat, lng) IN ('"+lat+"', '"+lng+"') ";
			ResultSet req=stmt_test.executeQuery(res);
			st.setNumber(Long.parseLong(req.getString("number")));
			st.setAddress(req.getString("address"));
			st.setContract_name(req.getString("contract_name"));
			st.setName(req.getString("name"));
			st.setLat(Double.parseDouble(req.getString("lat")));
			st.setLng(Double.parseDouble(req.getString("lng")));
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean modifyStation(int id, int available_bikes,int available_bike_stands, Date last_update) {

		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE station SET available_bikes='"+available_bikes+"' where id="+id;
			String sqlB = "UPDATE  station SET available_bike_stands='"+available_bike_stands+"' WHERE id = "+id ; 
			String sqlL = "UPDATE station SET date='"+last_update+"'"+" WHERE id = "+id ; 
			stmt_test.executeUpdate(req);
			stmt_test.executeUpdate(sqlB);
			stmt_test.executeUpdate(sqlL);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		
		return false;
	}
	

	public boolean modifyStation(int id, int available_bikes,int available_bike_stands, Date last_update, boolean bonus, boolean status) {

		Connection con = null;
		String req1, req2;
		
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			
			String req = "UPDATE station SET available_bikes='"+available_bikes+"' where id="+id;
			String sqlB = "UPDATE  station SET available_bike_stands='"+available_bike_stands+"' WHERE id = "+id ; 
			String sqlL = "UPDATE station SET date='"+last_update+"'"+" WHERE id = "+id ; 
			
			String req1and2 = "UPDATE station SET bike_stands='"+ (available_bikes + available_bike_stands) +"' where id="+id;

			if (status){
				req2 = "UPDATE station SET status='"+1+"' where id="+id; // pas besoin de transformer le boolean en OPEN ou CLOSED
			}
		    else {
		    	req2 = "UPDATE station SET status='"+0+"' where id="+id; 
			}
			
			if (bonus){
				req1 = "UPDATE station SET bonus='"+1+"' where id="+id;
			}
		    else {
				req1 = "UPDATE station SET bonus='"+0+"' where id="+id;
			}
			
			stmt_test.executeUpdate(req1);
			stmt_test.executeUpdate(req2);
			stmt_test.executeUpdate(req1and2);
			
			stmt_test.executeUpdate(req);
			stmt_test.executeUpdate(sqlB);
			stmt_test.executeUpdate(sqlL);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	public Statement Connect2(Connection con) {
		Statement stmt_test=null;
		try{
		stmt_test=con.createStatement();
		}catch(Exception e){
			return null;
		}
		return stmt_test;
	}
	public boolean DConnect(Connection con, Statement stmt_test) {
		try{
			stmt_test.close(); 
			con.close();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	public Connection Connect1() {
		Connection con=null;
		Statement stmt_test;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

		} catch( Exception e ){
			return null;
		}	
		return con;
	}
	
	// pour quoi se déconnecter et se reconnecter à la base à chaque fois ?
		public boolean modifyStationConnected(long number, int bike_stands, boolean banking_boolean , int available_bikes,int available_bike_stands, Date last_update, boolean bonus, boolean status, Statement stmt_test) {
			//System.out.println(number);
			String req1, req2, bank;
			try{
				String req = "UPDATE station SET available_bikes='"+available_bikes+"' where number="+number;
				String sqlB = "UPDATE  station SET available_bike_stands='"+available_bike_stands+"' WHERE number= "+number ; 
				String sqlL = "UPDATE station SET date='"+last_update.toString()+"'"+" WHERE number= "+number ; 
				
				String req1and2 = "UPDATE station SET bike_stands='"+bike_stands+"' where number="+number; 
				
				if (banking_boolean){
					bank = "UPDATE station SET banking='"+1+"' where number="+number; // pas besoin de transformer le boolean en OPEN ou CLOSED
				}
			    else {
			    	bank = "UPDATE station SET banking='"+0+"' where number="+number; 
			    }
				
				if (status){
					req2 = "UPDATE station SET status='"+1+"' where number="+number; // pas besoin de transformer le boolean en OPEN ou CLOSED
				}
			    else {
			    	req2 = "UPDATE station SET status='"+0+"' where number="+number; 
				}
				
				if (bonus){
					req1 = "UPDATE station SET bonus='"+1+"' where number="+number;
				}
			    else {
					req1 = "UPDATE station SET bonus='"+0+"' where number="+number;
				}
				
				stmt_test.executeUpdate(req1);
				stmt_test.executeUpdate(req2);
				stmt_test.executeUpdate(req1and2);
				
				stmt_test.executeUpdate(req);
				stmt_test.executeUpdate(sqlB);
				stmt_test.executeUpdate(sqlL);
				
			} catch( Exception e ){
				e.printStackTrace();
			}	
			return true;
		}
		
		public boolean modifyStationConnected(long number, int available_bikes,int available_bike_stands, Date last_update, Statement stmt_test) {
			//System.out.println(number);

			try{
				String req = "UPDATE station SET available_bikes='"+available_bikes+"' where number="+number;
				String sqlB = "UPDATE  station SET available_bike_stands='"+available_bike_stands+"' WHERE number= "+number ; 
				String sqlL = "UPDATE station SET date='"+last_update.toString()+"'"+" WHERE number= "+number ; 

				stmt_test.executeUpdate(req);
				stmt_test.executeUpdate(sqlB);
				stmt_test.executeUpdate(sqlL);
				
			} catch( Exception e ){
				e.printStackTrace();
			}	
			return true;
		}
		////////////////////////////////////////////////////////// juste des String
		public boolean modifyStationConnected(String number, String available_bikes, String available_bike_stands, Date last_update, Statement stmt_test) {
			//System.out.println(number);

			try{
				String req = "UPDATE station SET available_bikes='"+available_bikes+"' where number="+number;
				String sqlB = "UPDATE  station SET available_bike_stands='"+available_bike_stands+"' WHERE number= "+number ; 
				String sqlL = "UPDATE station SET date='"+last_update.toString()+"'"+" WHERE number= "+number ; 

				stmt_test.executeUpdate(req);
				stmt_test.executeUpdate(sqlB);
				stmt_test.executeUpdate(sqlL);
				
			} catch( Exception e ){
				e.printStackTrace();
			}	
			return true;
		}
		
	public boolean modifyStationByUser(int id, int UVelosDispos, int UPlacesDispos,  Date last_update){
		
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE station SET UPlacesDispos='"+UPlacesDispos+"' where id="+id;
			String sqlB = "UPDATE  station SET UVelosDispos='"+UVelosDispos+"' WHERE id = "+id ; 
			String sqlL = "UPDATE station SET date='"+last_update+"'"+" WHERE id = "+id ; 
			stmt_test.executeUpdate(req);
			stmt_test.executeUpdate(sqlB);
			stmt_test.executeUpdate(sqlL);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		
		return false;
		
	}
public boolean modifyStationByUser(int id, int UVelosDispos, int UPlacesDispos){
		
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");

			Statement stmt_test = con.createStatement();
			String req = "UPDATE station SET UPlacesDispos='"+UPlacesDispos+"' where id="+id;
			String sqlB = "UPDATE  station SET UVelosDispos='"+UVelosDispos+"' WHERE id = "+id ; 
			stmt_test.executeUpdate(req);
			stmt_test.executeUpdate(sqlB);
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}
		
		
		return false;
		
	}
	
	

	public ArrayList<WikiVerif> getWikiDuStation(int id){
		
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
			rec = stmt.executeQuery("SELECT * FROM wikiverif WHERE ids ='"+id+"' "); 

			while (rec.next()) {
				WikiVerif wiki = new WikiVerif();
				DAOUser user= new DAOUser(); // sans parametres ? 
				DAOStation station=new DAOStation();
				
				wiki.setId(Integer.parseInt(rec.getString("id")));
				wiki.setCommentaire(rec.getString("commentaire")); 
		
				DateFormat dfa = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH); 
			    Date resulta =  dfa.parse(rec.getString("dandh")); 
				wiki.setDandh(resulta);
				
				
				wiki.setPlaces(Integer.parseInt(rec.getString("places"))); 
				wiki.setVelos(Integer.parseInt(rec.getString("velos"))); 
				wiki.setS(station.getStation(id));
				
				
				String u=rec.getString("idu");
				
				if(u != null ){
					wiki.setU(user.getUser(Integer.parseInt(u)));
				}else{
					wiki.setU(null);
				}
				
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
	
	
	}

	
	

	
	

