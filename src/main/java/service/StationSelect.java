package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.Station;
import metier.Trajet;
import metier.User;
import metier.WikiVerif;
import domain.DAOStation;
import domain.DAOTrajet;
import domain.DAOUser;
import domain.DAOWikiVerif;

/**
 * Servlet implementation class StationSelect
 */
public class StationSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StationSelect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // une station peut faire partie d'un trajet ou la renseigner et afficher ses wikis
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// id station dans le get
		
		HttpSession session; 
		PrintWriter ecrire = response.getWriter();
		int id=0;
		
		try{
			 session = request.getSession(false);

			 if (session.isNew()){
					response.sendRedirect("index.html"); 
		      } else {// connecté
		    	  
		    	  	DAOUser du = new DAOUser();
					User u= du.getUser((Integer)session.getAttribute("userID"));
					
			        if(u.getMon_contrat() == null){
			        	 response.sendRedirect("Choix_contrat"); //prioritaire
			        }else{
			        	
			        	
			    		response.setContentType("text/html");
			    		
						ecrire.write("<a href='EspaceU' >");
						ecrire.write("Espace utilisateur:");
						ecrire.write("</a>");
						ecrire.write("&nbsp &nbsp");
			    
						DAOTrajet dt= new DAOTrajet();
						Trajet t= new Trajet();
						ArrayList<Trajet> mes_t= new ArrayList<Trajet>();
						
						DAOWikiVerif dw= new DAOWikiVerif();
						WikiVerif w= new WikiVerif();
						ArrayList<WikiVerif> mes_w= new ArrayList<WikiVerif>();
			
						
						DAOStation ds= new DAOStation();
						Station s= new Station();
						
						try{
						id=Integer.parseInt(request.getParameter("id"));
							if(id != 0){
								s=ds.getStation(id);
						
								mes_w=dw.getWikiVerifByStation(s); // normalement juste aujourd'hui
			
								
								// on appel et on revient
								ecrire.write("<form method='get' name'wiki' action='AddWiki'>Velos: <input type='text' name='velos' size='5'> &nbsp Places: <input type='text' name='places' size='5'><br><textarea rows='4' cols='50' name='comment' ></textarea><input class='button' type='submit' name ='addwiki' value='Valider' /> <input type='hidden' name='c' value='wiki'><input type='hidden' name='id' value='"+id+"'></form>");
						    	  
								ecrire.write("<br><br>");
								
								ecrire.write(s.toString()); // afficher les infos de la station
								
								ecrire.write("<br><br>");
		
								// séléctionner cette station pour départ ou pour arrivée et retour vers EspaceU
								ecrire.write("<a href='EspaceU?stationd="+id+"'>Séléctionner comme station de départ</a>"); // ajout fait dans la bd, prise en compte de la session
								ecrire.write("&nbsp &nbsp");
								ecrire.write("<a href='EspaceU?stationa="+id+"'>Séléctionner comme station d'arrivée</a>"); // ajout fait dans la bd
								ecrire.write("&nbsp &nbsp");
								ecrire.write("<a href='' onClick='javascript:window.history.go(-1)'>Retourner a mes recherches</a>");
								
								// filtrer par date par la suite
								ecrire.write("<br><hr>"); // ligne horizontale pour tous les wiki, sans filtre de date, changer apres la présentation
								ecrire.write("<div id='mes_wikis'><center>"); // + les wikiverifs
								ecrire.write("Voicis les dérnieres participations: <br>");
								
								for(int i=0; i<mes_w.size(); i++){ // normalement le dérnier suffit
									w=mes_w.get(i); 
									ecrire.write("<br>");
									ecrire.write(w.toString());
									ecrire.write("<br>");
									}

								ecrire.write("</center></div>");
								
								
								
								// je vais écrire tous les trajets de cette station
								
								mes_t=dt.getTrajetsByStationArrive(s);
								
								ecrire.write("<br><hr>"); // ligne horizontale pour tous les wiki, sans filtre de date, changer apres la présentation
								ecrire.write("<div id='mes_trajets'><center>"); // + les wikiverifs
								ecrire.write("Voicis les dérnieres prévisions d'arrivées: <br>");
								
								
								for(int i=0; i<mes_t.size(); i++){ 
									t=mes_t.get(i); 
									ecrire.write("<br>");
									ecrire.write(t.toString());
									ecrire.write("<br>");
									}

								ecrire.write("</center></div>");
								
								// prévisions de départ
								
								mes_t=dt.getTrajetsByStationDepart(s);
								
								ecrire.write("<br><hr>"); // ligne horizontale pour tous les wiki, sans filtre de date, changer apres la présentation
								ecrire.write("<div id='mes_trajets'><center>"); // + les wikiverifs
								ecrire.write("Voicis les dérnieres prévisions de départ: <br>");
								
								
								for(int i=0; i<mes_t.size(); i++){ 
									t=mes_t.get(i); 
									ecrire.write("<br>");
									ecrire.write(t.toString());
									ecrire.write("<br>");
									}

								ecrire.write("</center></div>");
							}
						}catch(Exception e){
						}
			       }
			  }
		}catch(Exception e){	
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
