package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
 * Servlet implementation class AddWiki
 */
public class AddWiki extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWiki() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session; 
		PrintWriter ecrire = response.getWriter();
		int id=0;
		
		try{
			 session = request.getSession(false);

			 if (session.isNew()){
					response.sendRedirect("index.html"); 
		      } else {// connecte
		    	  
		    	  	DAOUser du = new DAOUser();
					User u= du.getUser((Integer)session.getAttribute("userID"));
					
			        if(u.getMon_contrat() == null){
			        	 response.sendRedirect("Choix_contrat"); //prioritaire
			        }else{
			        	
	
						DAOWikiVerif dw= new DAOWikiVerif();
				
						DAOStation ds= new DAOStation();
						Station s= new Station();
						Date mad= new Date();
						
						try{
						id=Integer.parseInt(request.getParameter("id"));
						
						int velos=Integer.parseInt(request.getParameter("velos"));
						int places=Integer.parseInt(request.getParameter("places"));
						String commentaire=request.getParameter("comment");
						
							if(id != 0){
								s=ds.getStation(id);
						
								dw.addWikiVerif(s, u, velos, places, mad, commentaire);
								
								ds.modifyStationByUser(id, velos, places); // on ne modifie pas le lastupdate
								response.sendRedirect("StationSelect?id="+id+"");
							}else{
							response.sendRedirect("EspaceU"); // retourner, l'utilisateur s'en rendra compte ?
							}
						}catch(Exception e){
							response.sendRedirect("EspaceU");
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
