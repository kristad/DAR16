package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.URL;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import domain.*;



public class Json {
	
	public Json(){
	}
	
	public static void main(String[]args){ 
		
		
		Json j=new Json();
		Date ma_date=new Date();
		Date courent=new Date();
		long delais=0;
		int freq=60000; // une minute
		
		j.parse26init();
		
		while(true){
			ma_date=new Date();
			System.out.println("start - "+ma_date.toString() +" en millisecondes: "+ ma_date.getTime());
			try{
				j.p26();	
			}catch(Exception e){

			}
			courent=new Date();
			delais=ma_date.getTime()+freq-courent.getTime();
			try {
				System.out.println("sleep - "+ma_date.toString() +" en millisecondes: "+ ma_date.getTime());
			    TimeUnit.MILLISECONDS.sleep(delais); // ne pas recommence dans moins d'une minute du départ	
			} catch (InterruptedException e) {
				System.out.println( "Dans sleep, dans main, dans Json..." );
			}
		}
		
		/*
		Json j=new Json();
		Date ma_date=new Date();
		Date courent=new Date();
		long delais=60000;
		
		while(true){
			ma_date=new Date();
			System.out.println(ma_date.toString() +" en millisecondes: "+ ma_date.getTime());
			try{ // pour tester parse avec paris
				(new Json()).parsings("http://localhost:8080/JCDecaux/test.html"); 
				System.out.println("********************************************");
			}catch(Exception e){
				System.out.println("erreur dans le main, il y a t il un test.html ?");
			}
			
			courent=new Date();
			delais=ma_date.getTime()+60000-courent.getTime();
			try {
			    TimeUnit.MILLISECONDS.sleep(delais); // ne pas recommence dans moins d'une minute du départ	
			} catch (InterruptedException e) {
				System.out.println( "Dans sleep, dans main, dans Json..." );
			}
		}
		*/	
	}
	
	
	
	 /*
	 you should surround your call with a try{...} catch{...} block. But since Java 1.5 was released,
	 there is TimeUnit class which do the same as Thread.sleep(millis) but is more convenient.
	 You can pick time unit for sleep operation.
	 
	 	TimeUnit.NANOSECONDS.sleep(100);
	    TimeUnit.MICROSECONDS.sleep(100);
	    TimeUnit.MILLISECONDS.sleep(100);
	    TimeUnit.SECONDS.sleep(100);
	    TimeUnit.MINUTES.sleep(100);
	    TimeUnit.HOURS.sleep(100);
	    TimeUnit.DAYS.sleep(100);
	 */
	
	
	
public boolean parse(String a){
		
        Connection mac = null; // pour ne pas se connecter et se deconnecter à chaque update.
        Statement mas= null; 
    	boolean IsOk = false; // la fin de l'opération
	  	          
	    Map StationJsonData;
        Set<Entry> entrySet;
        
        long number=0;  
        long last_update=0;   
        int available_bike_stands=0;
        int available_bikes=0; 
        int bike_stands=0;
        boolean status_boolean=false;
        boolean bonus_boolean=false;
        boolean banking_boolean=false;
        String bonus; // c'est un boolean "OPEN" ou else ?
        String status; // c'est un boolean "OPEN" ou else ?
        Date last_update_Date=new Date(); 
        
    	JsonParserFactory factory=null;
    	JSONParser parser=null;
        
        DAOStation ds=new DAOStation();
        URL url;
	  try {

	    	factory = JsonParserFactory.getInstance();
	    	parser = factory.newJsonParser();
		    parser.setValidating(false);
		        
	          /*
	          final String jsonContent="[{\"number\":31705,\"name\":\"31705 - CHAMPEAUX (BAGNOLET)\",\"address\":\"RUE DES CHAMPEAUX (PRES DE LA GARE ROUTIERE) - 93170 BAGNOLET\",\"position\":{\"lat\":48.8645278209514,\"lng\":2.416170724425901},\"banking\":true,\"bonus\":true,\"status\":\"OPEN\",\"contract_name\":\"Paris\",\"bike_stands\":50,\"available_bike_stands\":37,\"available_bikes\":13,\"last_update\":1414657964000},{\"number\":10042,\"name\":\"10042 - POISSONNIÃˆRE - ENGHIEN\",\"address\":\"52 RUE D'ENGHIEN / ANGLE RUE DU FAUBOURG POISSONIERE - 75010 PARIS\",\"position\":{\"lat\":48.87242006305313,\"lng\":2.348395236282807},\"banking\":true,\"bonus\":false,\"status\":\"OPEN\",\"contract_name\":\"Paris\",\"bike_stands\":33,\"available_bike_stands\":3,\"available_bikes\":28,\"last_update\":1414657710000},{\"number\":8020,\"name\":\"08020 - METRO ROME\",\"address\":\"74 BOULEVARD DES BATIGNOLLES - 75008 PARIS\",\"position\":{\"lat\":48.882148945631904,\"lng\":2.319860054774211},\"banking\":true,\"bonus\":true,\"status\":\"OPEN\",\"contract_name\":\"Paris\",\"bike_stands\":44,\"available_bike_stands\":42,\"available_bikes\":2,\"last_update\":1414658056000},{\"number\":1022,\"name\":\"01022 - RUE DE LA PAIX\",\"address\":\"37 RUE CASANOVA - 75001 PARIS\",\"position\":{\"lat\":48.8682170167744,\"lng\":2.330493511399174},\"banking\":true,\"bonus\":false,\"status\":\"OPEN\",\"contract_name\":\"Paris\",\"bike_stands\":37,\"available_bike_stands\":2,\"available_bikes\":35,\"last_update\":1414657985000}]";
	          Map finalJsonData = parser.parseJson(jsonContent); 
	          
	          // probleme de protection des guillemets, les guillemets sont supprimés dans la map ! pour reparser 
	          */          
	    	
	        
            url = new URL(a);
	        BufferedReader in = new java.io.BufferedReader(new InputStreamReader(url.openStream()));
	

	        /*
			String inputLine;
			StringBuffer resp = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
			  resp.append(inputLine + "\n");
			} 
			in.close();
			
            Map finalJsonData = parser.parseJson(resp.toString());
            */
	        
	        /*
	         URL url = new URL("http://localhost:8080/CarnetContactStart/");
			BufferedReader in = new java.io.BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			StringBuffer resp = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
		  	resp.append(inputLine + "\n");
			}
			in.close();
			out.println(resp.toString());
	         */
	        
            Map finalJsonData = parser.parseJson(readAll(in).toString());
			in.close();
			//_|
			
			
	        mac = ds.Connect1();
	        mas= ds.Connect2(mac); 
            

	        // loop through the map entries to display all the key/value pairs parsed from JSOn string
            ArrayList addressList=(ArrayList)finalJsonData.get("root"); // le nom du grand tableau 
            
	        for(Object elmnt:addressList){ // si il n y a pas de nom ? ça sera appelé adress ? 
	        
	        	  StationJsonData=(Map)elmnt;
	        	  entrySet=StationJsonData.entrySet();
	        	  
		          for(Entry entry:entrySet){
		        	  // les mettre dans l'ordre "le quel ?" pour aller plus vite ?
		        	  /*
		              System.out.println("Key ::"+entry.getKey());
		              System.out.println("Value ::"+entry.getValue()); 
		        	  */
		              number=Long.parseLong((String)StationJsonData.get("number"));
		              status=(String)StationJsonData.get("status");
		              bonus=(String)StationJsonData.get("bonus");
		              
		              bike_stands=Integer.parseInt((String)StationJsonData.get("bike_stands"));
		              String banking=(String)StationJsonData.get("banking");
		              
		              available_bike_stands=Integer.parseInt((String)StationJsonData.get("available_bike_stands"));
		              available_bikes=Integer.parseInt((String)StationJsonData.get("available_bikes"));
		              
		              last_update=Long.parseLong((String)StationJsonData.get("last_update"));
		              last_update_Date= new Date(last_update); // c'est des millisecondes, un int suffir normalement
		              
		              try{
		            	  if(banking.equals("true")){
		            		  banking_boolean=true;
		            	  }else{
		            		  banking_boolean=false;
		            	  }
		              }catch(Exception e){

		              }
		              
		              try{
		            	  if(status.equals("OPEN")){
		            		  status_boolean=true;
		            	  }else{
		            		  status_boolean=false;
		            	  }
		              }catch(Exception e){

		              }
		              
		              try{
		            	  if(bonus.equals("true")){
		            		  bonus_boolean=true;
		            	  }else{
		            		  bonus_boolean=false;
		            	  }
		              }catch(Exception e){
		            	  
		              }     
		          	}
		          
	              try{ // à l'éxtérieure du for des éléments
	            	  
	            	//  banking_boolean et le bike stands et et bonus status ça doit pas etre recurent
	            	  
	              ds.modifyStationConnected(number, bike_stands, banking_boolean, available_bikes, available_bike_stands, last_update_Date, bonus_boolean, status_boolean, mas);

	              }catch(Exception e){
	            	  return false;
	              }
	        	  
            }
	           }catch(Exception e){	        	   
	        	   System.out.println("Parse exception !");
	        	   return false;
	           }
      IsOk = ds.DConnect(mac, mas);
      return true;
	}
	
	
	
	
	public boolean parsing(String a){
		
        Connection mac = null; // pour ne pas se connecter et se deconnecter à chaque update.
        Statement mas= null; 
    	boolean IsOk = false; // la fin de l'opération
	  	          
	    Map StationJsonData;
        Set<Entry> entrySet;
        
        long number=0;  
        long last_update=0;   
        int available_bike_stands=0;
        int available_bikes=0;  
        
    	JsonParserFactory factory=null;
    	JSONParser parser=null;
        
        DAOStation ds=new DAOStation();
        URL url;
	  try {

	    	factory = JsonParserFactory.getInstance();
	    	parser = factory.newJsonParser();
		    parser.setValidating(false);
		
            url = new URL(a);
	        BufferedReader in = new java.io.BufferedReader(new InputStreamReader(url.openStream()));
	        
            Map finalJsonData = parser.parseJson(readAll(in).toString());
			in.close();
			//_|
			
			
	        mac = ds.Connect1();
	        mas= ds.Connect2(mac); 
            

	        // loop through the map entries to display all the key/value pairs parsed from JSOn string
            ArrayList addressList=(ArrayList)finalJsonData.get("root"); // le nom du grand tableau 
            
	        for(Object elmnt:addressList){ // si il n y a pas de nom ? ça sera appelé adress ? 
	        
	        	  StationJsonData=(Map)elmnt;
	        	  entrySet=StationJsonData.entrySet();
	             // System.out.println("**********************************************");
		          for(Entry entry:entrySet){
		        	  // les mettre dans l'ordre "le quel ?" pour aller plus vite ?
		        	  
		              //System.out.println("Key ::"+entry.getKey());
		             // System.out.println("Value ::"+entry.getValue()); 
		        	  
		              available_bikes=Integer.parseInt((String)StationJsonData.get("available_bikes"));
		              available_bike_stands=Integer.parseInt((String)StationJsonData.get("available_bike_stands"));
		              last_update=Long.parseLong((String)StationJsonData.get("last_update")); // en millisecondes
		              number=Long.parseLong((String)StationJsonData.get("number"));
		          }
		          
	           try{ // à l'éxtérieure du for des éléments  
	            	//  banking_boolean et le bike stands et et bonus status ça doit pas etre recurent	  
	              ds.modifyStationConnected(number, available_bikes, available_bike_stands, new Date(last_update), mas);
	              }catch(Exception e){
	            	  return false;
	              } 
	           
            } // fin for
	        
	  }catch(Exception e){	        	   
	       System.out.println("Parse exception !");
	       return false;
	  }
      IsOk = ds.DConnect(mac, mas);
      return true;
	}
	
	
	
public boolean parsings(String a){ 
		
        Connection mac = null; 
        Statement mas= null; 
    	boolean IsOk = false;
    	Date date=new Date();
	    Map StationJsonData;
        Set<Entry> entrySet;
    	JsonParserFactory factory=null;
    	JSONParser parser=null;
        DAOStation ds=new DAOStation();
        URL url;
	  try {
	    	factory = JsonParserFactory.getInstance();
	    	parser = factory.newJsonParser();
		    parser.setValidating(false);
            url = new URL(a);
	        BufferedReader in = new java.io.BufferedReader(new InputStreamReader(url.openStream()));
            Map finalJsonData = parser.parseJson(readAll(in).toString());
			in.close();
	        mac = ds.Connect1();
	        mas= ds.Connect2(mac); 
            ArrayList addressList=(ArrayList)finalJsonData.get("root"); 
            
	        for(Object elmnt:addressList){ 
	        	  StationJsonData=(Map)elmnt;
	        	  entrySet=StationJsonData.entrySet();
		          for(Entry entry:entrySet){
		              date=new Date(Long.parseLong((String)StationJsonData.get("last_update"))); // en millisecondes
		          }  
	           try{ 
	              ds.modifyStationConnected((String)StationJsonData.get("number"),(String)StationJsonData.get("available_bikes"), (String)StationJsonData.get("available_bike_stands"),date, mas);
	              }catch(Exception e){
	            	  return false;
	              } 
	           
            } // fin for
	        
	  }catch(Exception e){	        	   
	       return false;
	  }
      IsOk = ds.DConnect(mac, mas);
      return true;
	}
	


	public boolean p26(){ // normalement à faire chaque minute, avec while true ?
	boolean erreur=true;
	DAOContrat dc = new DAOContrat();
	String s1= "https://api.jcdecaux.com/vls/v1/stations?contract=";
	String s2= "&apiKey=ba9e3bf0bf4ef4fb60a39958f5a19f883560c35f";
	String s= "";
	for(int i =1; i<= 26; i ++){
		s=s1+dc.getContrat(i).getContract_name()+s2; // fabrique les url
		try{
			this.parsings(s); 	// le this est t il necessaire ?
		}catch(Exception e){ // rien faire et passer à la suivante	
			erreur=false;
		}
	}
	return erreur;
	}

	
	public boolean pi(int i){ // i =2 ça veut dire paris ! 35 secondes
	boolean erreur=true;
	DAOContrat dc = new DAOContrat();
	String s1= "https://api.jcdecaux.com/vls/v1/stations?contract=";
	String s2= "&apiKey=ba9e3bf0bf4ef4fb60a39958f5a19f883560c35f";
	String s= "";

		s=s1+dc.getContrat(i).getContract_name()+s2; 
		try{
			this.parsings(s); 	
		}catch(Exception e){ 
			erreur=false;
		}
	return erreur;
	}
	
	
	public boolean pp(){ // paris ! 35 secondes
	boolean erreur=true;
	DAOContrat dc = new DAOContrat();
	String s= "https://api.jcdecaux.com/vls/v1/stations?contract=paris&apiKey=ba9e3bf0bf4ef4fb60a39958f5a19f883560c35f";
		try{
			this.parsings(s); 	
		}catch(Exception e){ 
			erreur=false;
		}
	return erreur;
	}

	public boolean parse26(){
		boolean erreur=true;
		DAOContrat dc = new DAOContrat();
		String s1= "https://api.jcdecaux.com/vls/v1/stations?contract=";
		String s2= "&apiKey=ba9e3bf0bf4ef4fb60a39958f5a19f883560c35f";
		String s= "";
		for(int i =1; i<= 26; i ++){
			s=s1+dc.getContrat(i).getContract_name()+s2; 
			try{
				this.parsing(s); 
			}catch(Exception e){ 	
				erreur=false;
			}
		}
		return erreur;
	}
	
	
	public boolean parse26init(){
		boolean erreur=true;
		DAOContrat dc = new DAOContrat();
		String s1= "https://api.jcdecaux.com/vls/v1/stations?contract=";
		String s2= "&apiKey=ba9e3bf0bf4ef4fb60a39958f5a19f883560c35f";
		String s= "";
		for(int i =1; i<= 26; i++){
			s=s1+dc.getContrat(i).getContract_name()+s2; 
			try{
				this.parse(s); 	
			}catch(Exception e){ 
				erreur=false;
			}
		}
		return erreur;
	}
	
	
	
	
	
	private static String readAll(Reader rd) throws IOException { // pour créer une string avec un reader
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
}
