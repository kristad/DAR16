package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.Trajet;
import metier.User;
import metier.WikiVerif;
import domain.DAOTrajet;
import domain.DAOUser;
import domain.DAOWikiVerif;

/**
 * Servlet implementation class Trajets
 */
public class Trajets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trajets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // ici on doit donner à l'utilisateur la possibilité de términer le dérnier la saisie du dérnier trajet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
						
						// si les deux stations sont séléctionnés ont peut ajouter trajet en submettant ce form
						ecrire.write("<form method='get' name='trajet' action='ajouteTrajet'>Vous partez dans combien de minutes: <input type='text' name='hdepart' size='5'> &nbsp durée éstimé du trajet: <input type='text' name='duree' size='5'><br><textarea rows='4' cols='50' name='comment' ></textarea><input class='button' type='submit' name ='addwiki' value='Valider' /> </form>");
				    	
			    
						DAOTrajet dt= new DAOTrajet();
						
						Trajet t= new Trajet();
						
						DAOWikiVerif dw= new DAOWikiVerif();
						
						WikiVerif w= new WikiVerif();
						
						ArrayList<WikiVerif> mes_w= new ArrayList<WikiVerif>();
						ArrayList<Trajet> mes_t= new ArrayList<Trajet>();
						
						try{
						id=Integer.parseInt(request.getParameter("id"));
							if(id != 0){
								dt.deleteTrajet(id);	// supprimer le trajet avant de renouveler la requette
							}
						}catch(Exception e){}
						
						mes_w=dw.getWikiVerifByUser(u);
						mes_t=dt.getTrajetByUser(u);
						
						ecrire.write("<div id='mes_trajets'><center>"); // affichage trajets
						ecrire.write("Clicker sur un trajet pour l'annuler: <br>"); 
						for(int i=0; i<mes_t.size(); i++){ // ? problème récurent si l'array n'est pas copié, je pense
							try{
							t=mes_t.get(i);
						
								ecrire.write("<a href='Trajets?id="+t.getId()+"'>"); //clicker dessus pour annuler/supprimer
								ecrire.write("<br>");
								ecrire.write(t.toString());
								ecrire.write("</a>");
								ecrire.write("<br>");
								
							}catch(Exception e){}
							}
						
						ecrire.write("</center></div>");
			        	//ecrire.write("<html><head><script>document.getElementById'MyDiv').innerHTML = 'profil.html'</script></head><body><div id='MyDiv'></div></body></html>"); // index.html dans un div
			        		
						ecrire.write("<br><hr>"); // ligne horizontale
						ecrire.write("<div id='mes_wikis'><center>"); // + les wikiverifs
						ecrire.write("Voicis vos dérniéres participations: <br>");
						
						for(int ii=0; ii<mes_w.size(); ii++){ // ? problème récurent si l'array n'est pas copié, je pense
							w=mes_w.get(ii); 
							ecrire.write("<br>");
							ecrire.write(w.toString());
							ecrire.write("<br>");
							
							}
						
						ecrire.write("</center></div>");
						
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
