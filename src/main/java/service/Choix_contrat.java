package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.*;
import metier.*;
/**
 * Servlet implementation class Choix_contrat
 */
public class Choix_contrat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Choix_contrat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    // meme si l'utilisateur a un contrat il peut le changer
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		HttpSession session;
		DAOContrat dc= new DAOContrat();
		Contrat c;
		String s=request.getParameter("c");
		

		
		PrintWriter ecrire = response.getWriter();
		response.setContentType("text/html");
		ecrire.write("<html lang='fr'> <head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='X-UA-Compatible' content='IE=edge'/><meta name='viewport' content='width=device-width, initial-scale=1'/>");
		ecrire.write(" <title>Choix de contrat</title> <link href='css/bootstrap.min.css' rel='stylesheet'><link href='css/bootstrap-theme.min.css' rel='stylesheet'>");
		ecrire.write("<link href='theme.css' rel='stylesheet'> <script src='js/ie-emulation-modes-warning.js'></script></head><body>");
		
		if(s==null){
		// se renseigner sur esponse.encodeUrl
			
		
		ecrire.write("<a href='EspaceU'>Retourner à l'éspace utilisateur</a> <br><br><br> <center><h3>Séléctionner le contrat qui correspond à votre ville</h3><br></center>");
		
		for(int i=1; i<=26; i++){ // nombre totale de contrats est fixé à 26, à changer si il y a du nouveau
			
			c= dc.getContrat(i);

			String panel;
            if((i%2)==1)
            	panel="panel panel-info";
            else panel="panel panel-warning";
            
            	
			ecrire.write("<br>&nbsp<div width=20%><div class='"+panel+"'><div class='panel-heading'><h3 class='panel-title'><a href='Choix_contrat?c="+i+"'></h3>");
			ecrire.write(c.toString());
			ecrire.write("</a></div>");
			ecrire.write("<div class='panel-body'>");		
		    ecrire.write(c.toStringcities());
			ecrire.write("</div></div>");
			ecrire.write("</div></div>");

		}
		ecrire.write("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js'></script><script src='js/bootstrap.min.js'></script>");
		ecrire.write("<script src='js/docs.min.js'></script><script src='js/docs.min.js'></script><script src='js/ie10-viewport-bug-workaround.js'></script></body></html>");
		}else
		{
			//fermeture session pas propre
			if(s.equals("200")){ // appelé dans g.html ou EspaceU pour se déconnecter, c'est un choix
				session = request.getSession(true);
				session.invalidate();
				
				response.sendRedirect("index.html"); 
			}else{
			
			
			c=dc.getContrat(Integer.parseInt(s));
			
			
			 session = request.getSession(false);

			 if (session.isNew()){
					response.sendRedirect("index.html"); // anormale d'etre là quand on n'est pas connecté
				 
		      } else {

		    	  DAOUser du= new DAOUser();
		    	  User u= du.getUser((Integer)session.getAttribute("userID")); //!! à chaque foi je charge de la bd !!!! on ne peut pas sauver un objet dans la session ?
		    	  u.setMon_contrat(c);
		    	  
		    	  du.modifyUser(u.getId(), u.getFirstName(), u.getName(), u.getEmail(), u.getPass(), u.getSexe(), u.getDateNaissance(), c); 
		    	  
				response.sendRedirect("EspaceU");
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
