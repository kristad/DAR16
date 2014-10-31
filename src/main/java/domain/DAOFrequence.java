package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Frequence;
import metier.Station;
import metier.User;

public class DAOFrequence implements IDAOFrequence {

	public Frequence addFrequenceD( Station station, User user) {
		
        Frequence freq = new Frequence();

		freq.setStation(station);
		freq.setU(user);
		freq.setFrequenceD(1);
		
       Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt_test = con.createStatement();
			String req = "INSERT INTO frequence(ids, idu, frequenceD) VALUES('"+station.getId()+"','"+user.getId()+"',1)"; 
			
			
			
			stmt_test.executeUpdate(req);
			Statement stmt_id = con.createStatement();
			ResultSet rec = stmt_id.executeQuery("SELECT max(id) FROM user"); // retourne le id maximum;
			while (rec.next()) {
			freq.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			stmt_id.close(); 
			stmt_test.close(); 
			con.close();
		} catch( Exception e ){
			e.printStackTrace();
		}

		return freq;
		
	}
	
	public Frequence addFrequenceA( Station station, User user) {
		
        Frequence freq = new Frequence();
        Connection con = null;
		freq.setStation(station);
		freq.setU(user);

try{

	String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
       Class.forName("com.mysql.jdbc.Driver");
       con = DriverManager.getConnection(
                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
       
			Statement stmt_test = con.createStatement();
			String req = "INSERT INTO frequence(ids, idu, frequenceA) VALUES('"+station.getId()+"','"+user.getId()+"',1)"; 
			
			
			
			stmt_test.executeUpdate(req);
			Statement stmt_id = con.createStatement();
			ResultSet rec = stmt_id.executeQuery("SELECT max(id) FROM user"); // retourne le id maximum;
			while (rec.next()) {
			freq.setId(Integer.parseInt(rec.getString("max(id)")));  // chercher par id ? auto-incremente id
			}
			stmt_id.close(); 
			stmt_test.close(); 
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}

		return freq;
		
	}

	public int deleteFrequence(int id) {
		
		int success=0;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt_test = con.createStatement();
			String req = "DELETE  from frequence where id='"+id+"' ";
			stmt_test.executeUpdate(req);
			stmt_test.close(); 
			con.close();
			success=1;
			System.out.println("dans DAOstation, deletefrequence, fin ************");
		} catch( Exception e ){
			e.printStackTrace();
		}
		return success;
	}

	public Frequence getFrequence(int id) {
		
		
		Frequence freq= new Frequence();
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
			Statement stmt_test = con.createStatement();
			
			String res = "SELECT * from frequence where id="+id;
			ResultSet req = stmt_test.executeQuery(res);
			
			while(req.next()){
			freq.setFrequenceA(Integer.parseInt(req.getString("frequencea")));
			System.out.println("OK______________________________________");
			freq.setFrequenceD(Integer.parseInt(req.getString("frequenced")));
			DAOStation st=new DAOStation();
			String s=req.getString("ids");
			
			if(s != null ){
				freq.setStation(st.getStation(Integer.parseInt(s)));
				 
			}else{
				freq.setStation(null);
			}
			DAOUser u= new DAOUser();
            String su=req.getString("idu");
			
			if(su != null ){
				freq.setU(u.getUser(Integer.parseInt(su)));
				 
			}else{
				freq.setU(null);
			}}
			
			stmt_test.close(); 
			con.close();
			System.out.println("dans DAOstation, getfrequence, fin ************");
		} catch( Exception e ){
			e.printStackTrace();
		}
		return freq;
		
	}
	// ça incrémente de 1
	public boolean modifyFrequenceArrive( Station station, User u) {

	       Connection con = null;
	       DAOFrequence df= new DAOFrequence();
	       Frequence freq=df.getFrequenceByUserAndStation(u, station);
	       int idFreq=freq.getId();
	       System.out.println("........................"+idFreq);
			try{
				String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			       Class.forName("com.mysql.jdbc.Driver");
			       con = DriverManager.getConnection(
			                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
				Statement stmt_test = con.createStatement();
				
				int freqa=freq.getFrequenceA();
				freqa++;
				String req = "UPDATE  frequence SET  frequencea='"+freqa+"' WHERE id='"+idFreq+"' "; 
				stmt_test.executeUpdate(req);
				stmt_test.close(); 
				con.close();
				System.out.println("dans DAOstation, addstation, fin ************");
			} catch( Exception e ){
				e.printStackTrace();
			}

			return true;
		
		
		
	}
	// ça incrémente de 1
	public boolean modifyFrequenceDepart( Station station, User u) {

		Connection con = null;
	       DAOFrequence df= new DAOFrequence();
	       Frequence freq=df.getFrequenceByUserAndStation(u, station);
	       int idFreq=freq.getId();
	       System.out.println("........................"+idFreq);
			try{
				
				String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			       Class.forName("com.mysql.jdbc.Driver");
			       con = DriverManager.getConnection(
			                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
				Statement stmt_test = con.createStatement();
				
				int freqd=freq.getFrequenceD();
				freqd++;
				String req = "UPDATE  frequence SET  frequenced='"+freqd+"' WHERE id='"+idFreq+"' "; 
				stmt_test.executeUpdate(req);
				stmt_test.close(); 
				con.close();
				System.out.println("dans DAOstation, addstation, fin ************");
			} catch( Exception e ){
				e.printStackTrace();
			}

			return true;
		
		
	}

	public ArrayList<Frequence> getFrequenceByUser(User user) {
		
		
		ArrayList<Frequence> freqs = new ArrayList<Frequence>();
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM frequence WHERE idu='"+user.getId()+"' "); 

			while (rec.next()) {
				Frequence f = new Frequence();
				DAOStation stat= new DAOStation(); 
				
				f.setId(Integer.parseInt(rec.getString("id")));
				f.setFrequenceA(Integer.parseInt(rec.getString("frequenceA"))); 
				f.setFrequenceD(Integer.parseInt(rec.getString("frequenceD"))); 
				f.setU(user);  
				f.setStation (stat.getStation(Integer.parseInt(rec.getString("ids"))));
				
				
				freqs.add(f);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return freqs;
		
	}
	
public ArrayList<Frequence> getFrequenceByUserD(User user) {
		
		
		ArrayList<Frequence> freqs = new ArrayList<Frequence>();
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J");
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM frequence WHERE idu='"+user.getId()+"' ORDER BY frequenceD DESC"); 

			while (rec.next()) {
				Frequence f = new Frequence();
				DAOStation stat= new DAOStation(); 
				
				f.setId(Integer.parseInt(rec.getString("id")));
				f.setFrequenceA(Integer.parseInt(rec.getString("frequenceA"))); 
				f.setFrequenceD(Integer.parseInt(rec.getString("frequenceD"))); 
				f.setU(user);  
				f.setStation (stat.getStation(Integer.parseInt(rec.getString("ids"))));
				
				
				freqs.add(f);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return freqs;
		
	}
public ArrayList<Frequence> getFrequenceByUserA(User user) {
	
	
	ArrayList<Frequence> freqs = new ArrayList<Frequence>();
	
	ResultSet rec = null;
	Connection con = null;
	try{
		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	       Class.forName("com.mysql.jdbc.Driver");
	       con = DriverManager.getConnection(
	                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
		Statement stmt = con.createStatement();
		rec = stmt.executeQuery("SELECT * FROM frequence WHERE idu='"+user.getId()+"' ORDER BY frequenceA DESC"); 

		while (rec.next()) {
			Frequence f = new Frequence();
			DAOStation stat= new DAOStation(); 
			
			f.setId(Integer.parseInt(rec.getString("id")));
			f.setFrequenceA(Integer.parseInt(rec.getString("frequenceA"))); 
			f.setFrequenceD(Integer.parseInt(rec.getString("frequenceD"))); 
			f.setU(user);  
			f.setStation (stat.getStation(Integer.parseInt(rec.getString("ids"))));
			
			
			freqs.add(f);
		}

		stmt.close();
		rec.close();
		con.close();

	} catch( Exception e ){
		e.printStackTrace();
	}
	return freqs;
	
}

	public ArrayList<Frequence> getFrequenceByStation(Station station) {
		
		ArrayList<Frequence> freqs = new ArrayList<Frequence>();
		
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM frequence WHERE ids='"+station.getId()+"' "); 

			while (rec.next()) {
				Frequence f = new Frequence();

				DAOUser u= new DAOUser(); 
				
				f.setId(Integer.parseInt(rec.getString("id")));
				f.setFrequenceA(Integer.parseInt(rec.getString("frequenceA"))); 
				f.setFrequenceD(Integer.parseInt(rec.getString("frequenceD"))); 
				f.setStation(station);  
				f.setU (u.getUser(Integer.parseInt(rec.getString("idu"))));
				
				
				freqs.add(f);
			}

			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		return freqs;
		
		
	}

	public Frequence getFrequenceByUserAndStation(User user, Station station) {
		
         
		Frequence f = new Frequence();
		ResultSet rec = null;
		Connection con = null;
		try{
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		       Class.forName("com.mysql.jdbc.Driver");
		       con = DriverManager.getConnection(
		                "jdbc:mysql://"+host+":"+port+"/dar", "adminc1A7TAm", "6gG4scG6dM1J"); 
			Statement stmt = con.createStatement();
			rec = stmt.executeQuery("SELECT * FROM frequence WHERE ids='"+station.getId()+"' AND idu='"+user.getId()+"' ");
				while (rec.next()){
				f.setId(Integer.parseInt(rec.getString("id")));
				f.setFrequenceA(Integer.parseInt(rec.getString("frequenceA"))); 
				f.setFrequenceD(Integer.parseInt(rec.getString("frequenceD"))); 
				f.setStation(station);  
				f.setU(user);
				}
			stmt.close();
			rec.close();
			con.close();

		} catch( Exception e ){
			e.printStackTrace();
		}
		
		return f;
			
	}


	
	
}
