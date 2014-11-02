package service;

import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Date;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import application.UtilsStation;
import metier.Contrat;
import metier.Frequence;
import metier.Station;
import metier.User;
import domain.DAOFrequence;
import domain.DAOStation;
import domain.DAOUser;
import domain.DAOWikiVerif;
/**
 * Servlet implementation class Trajet
 */
public class Trajet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trajet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//lat et lng en parametre
		int max=5; // combien de station voisines
		
		DAOUser du= new DAOUser();
		DAOStation ds= new DAOStation();
		UtilsStation us= new UtilsStation();
		User u= new User();
		Station mas=new Station();
		Station [] mesS= new Station[max];
		int id=0;
		
		HttpSession session; 
		session = request.getSession(false); // ne pas créer, doit rediriger vers l'entrée
		
		 if (session.isNew()){ // ? je veux juste savoir si il y a un userID
				response.sendRedirect("index.html"); // anormale, mais c'est arrivé une fois je ne sais pour quoi ?
	      } else {
	    	  
	  				try{
	  					
	  					u= du.getUser((Integer)session.getAttribute("userID")); 
	  					if(u == null){
	  						response.sendRedirect("index.html");
	  								 }
	  					}catch (Exception e){
	  					response.sendRedirect("index.html");
	  										}
	    			
	    	  		PrintWriter ecrire = response.getWriter();
	    	  		response.setContentType("text/html");
	    	  		
	    	  		try{
	    	  			
	    	  			id=Integer.parseInt(request.getParameter("id")); // comparer avec le nom choisi dans StattionSelect
	    	  		}catch(Exception e){
	    	  		}
	    	  	
	    	  		if(id==0){ // donc pas de station cliqué

	    	  			try{
	    	  				// ?var=&var2= dans l'url
	    	  				Double lat=Double.parseDouble(request.getParameter("lat"));
	    	  				Double lng=Double.parseDouble(request.getParameter("lng"));
		  				
	    	  				mesS= us.getStations(lat,lng,max,u.getMon_contrat());
	    	  				
	    	  				ecrire.write("<br> - Voici les stations de votre contrat les plus proches de vous: <br>"); 
	    	  				
	    	  				for(int i=0; i<mesS.length; i++){ // je peux faire plus proches et status ok après
	    	  					mas=mesS[i];
	    	  					try{
	    	  						ecrire.write(" &nbsp &nbsp <a href='StationSelect?id="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
	    	  						ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>");
	    	  					}catch(Exception e){
	    	  						System.out.println("plus proches");
	    	  					}
	    	  				}
	    	  			}catch(Exception e){	
	    	  			}
	    	  			
	    	  		}else{ // l'utilisateur a clické sur une station "séléction"
	    	  			
	    	  			// des modifications devrait aussi etre faites pour les stations !!
	    	  			mas=ds.getStation(id);
	    	  			// ajouter une wiki et se renseigner sur une station c'est des activités de fréquence
	    	  			ecrire.write(mas.toString()); // à changer pour donner les info à l'utilisateur, utiliser javascript pour ça
	    	  			// ajouter à la bd
	    	  			
	    	  			System.out.println(mas.toString()); 
	    	  		}	
	      }
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
