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

import metier.Frequence;
import metier.Station;
import metier.User;
import domain.DAOFrequence;
import domain.DAOStation;
import domain.DAOUser;
import domain.DAOWikiVerif;

/**
 * Servlet implementation class DrequenceDA
 */

// regarder dans Station toString
// séparer les statiques et les dynamiques

public class FrequenceDA extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrequenceDA() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int max=5; // combien de station dans l'historique ?
		
		metier.Frequence fa;
		metier.Frequence fd;
		DAOUser du= new DAOUser();
		DAOFrequence df= new DAOFrequence();
		DAOStation ds= new DAOStation();
		Station mas=null;
		User u= new User();
		HttpSession session; 
		session = request.getSession(false); // ne pas créer, doit rediriger vers l'entrée
		
		 if (session.isNew()){ // ? je veux juste savoir si il y a un userID
				response.sendRedirect("index.html"); // anormale, mais c'est arrivé une fois je ne sais pour quoi ?
			 
	      } else {
	    	  
	  				String s=request.getParameter("c");
	    	  		PrintWriter ecrire = response.getWriter();
	    	  		
	    	  			try{
	    	  				u= du.getUser((Integer)session.getAttribute("userID")); // parse ?
	    	  				if(u == null){
	    	  					response.sendRedirect("index.html");
	    	  				}
	    	  			}catch (Exception e){
	    	  				response.sendRedirect("index.html");
	    	  			}
	    	  		
	    	  		ArrayList<Frequence> freqsa=df.getFrequenceByUserA(u); //ordonnées déjas dans le DAO
	    	  		ArrayList<Frequence> freqsd=df.getFrequenceByUserD(u); 
	    	  		response.setContentType("text/html");
	    	  		
	    	  		
	    	  		if(s == null){
	    	  			
	    	  		// je retourne du texte simple avec lien vers servlet avec station séléctionné, ça sera dans le doPost
	    	  		// le premier accè à la servlet
	    	  		ecrire.write("<br>- <a href='FrequenceDA?c=arrivee'> Les stations les plus fréquentées à l'arrivée?</a> <br><br>"); // l'utilisateur doit spésifier
	    	  		for(int i=0; i<max; i++){
	    	  			try{	
	    	  				fd=freqsd.get(i);
	    	  				if(fd != null){ 
	    	  					mas=fd.getStation();
	    	  					ecrire.write(" &nbsp &nbsp <a href='FrequenceDA?c="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
	    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
	    	  				}
	    	  			}catch(Exception e){
	    	  			}  
	    	  		}	
	    	  		
	    	  	}else{	// fin if du paramettre c= id station clické
	    	  		
	    	  		if(s.equals("depart")){
	    	  			ecrire.write("<br>- <a href='FrequenceDA?c=arrivee'> Les stations les plus fréquentées à l'arrivée?</a> <br><br>"); // l'utilisateur doit spésifier
		    	  		
	    	  			for(int i=0; i<max; i++){
		    	  			try{	
		    	  				fd=freqsd.get(i);
		    	  				if(fd != null){ 
		    	  					mas=fd.getStation();
		    	  					ecrire.write(" &nbsp &nbsp <a href='FrequenceDA?c="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
		    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
		    	  				}
		    	  			}catch(Exception e){
		    	  			}  
		    	  		}	
	    	  		}
	    	  		if(s.equals("arrivee")){
	    	  			ecrire.write("<br>- <a href='FrequenceDA?c=depart'> Les stations les plus fréquentées au départ?</a> <br><br>"); // l'utilisateur doit spésifier
		    	  		for(int i=0; i<max; i++){
		    	  			try{	
		    	  				fa=freqsa.get(i);
		    	  				if(fa != null){ 
		    	  					mas=fa.getStation();
		    	  					ecrire.write(" &nbsp &nbsp <a href='FrequenceDA?c="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
		    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
		    	  				}
		    	  			}catch(Exception e){
		    	  			}  
		    	  		}
	    	  		}
	    	  		
	    	  	

	    	  		
	    	  		if(s.equals("wiki")){ // l'utilisateur a renseigner le wiki dans son historique // il devrai y avoir un autre choix
	   
	    	  			try{
	    	  			//faire les testes
	    	  		
	    	  		int tempo=Integer.parseInt(request.getParameter("ids"));
	    	  		int velos=Integer.parseInt(request.getParameter("velos"));
	    	  		int places=Integer.parseInt(request.getParameter("places"));
	    	  		String comment=request.getParameter("comment");
	    	  		Date mad= new Date();
	    	  		mas=ds.getStation(tempo);
	    	  		DAOWikiVerif dw = new DAOWikiVerif();
	    	  		dw.addWikiVerif(mas, u, velos, places, mad, comment);
	    	  		ds.modifyStationByUser(mas.getId(), velos, places); // on peut chercher et afficher tous les wikiverifs pour chercher la derniere wiki et valider, apres
	    	  		
	    	  		//df.modifyFrequence...(mas, u); // incrémenter la fréquence aussi
	    	  		response.sendRedirect("FrequenceDA"); // il faut que ça soit à la fin comme un return? je ne suis pas arrivé à cette redirection
	    	  		
		    	  		}catch(Exception e){
		    	  			System.out.println("Erreur: La station n'a pas été séléctionné !");
		    	  		}
	    	  			}
	    	  		// comment envoyer cette donnée à une autre servlet, dispatcher ?
	    	  		
	    	  		// ça s'éxécute si aucun des trois cas, l'emplacement peut etre modifié
	    	  		// renvoyer l'idé de la station dans un hidden input
	    	  		if(!(s.equals("depart")) && !(s.equals("wiki")) && !(s.equals("arrivee"))){
	    	  			
	    	  		ecrire.write("<form method='get' name'wiki' action='FrequenceDA'>Velos: <input type='text' name='velos' size='5'> &nbsp Places: <input type='text' name='places' size='5'><br><textarea rows='4' cols='50' name='comment' ></textarea><input class='button' type='submit' name ='addwiki' value='Valider' /> <input type='hidden' name='c' value='wiki'><input type='hidden' name='ids' value='"+s+"'></form>");
	    	  		int tempo=Integer.parseInt(s);
	    	  		mas=ds.getStation(tempo);
	    	  		ecrire.write("<br> &nbsp &nbsp "+mas.toString()+"<br>"); 

	    	  		}
	    	  		
	
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
