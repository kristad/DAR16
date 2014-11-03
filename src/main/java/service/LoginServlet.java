package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.*;
import application.*;
import metier.*;
/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    // si l'utilisateur n'a pas de contrat, il doit en avoir d'abord
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// ça c'est juste pour tester la fonction de recherche par distance

		/*
		DAOStation ds= new DAOStation();
		
		Station s= ds.getStation(4);
		int t=20;
		
		Station as [] = new Station[t];
		UtilsStation us = new UtilsStation();
		as=us.getStations(s.getLat(), s.getLng(), t, s.getContrat());
		
		for(int i=0; i<t; i++){
			System.out.println(as[i].toString());
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// comment somme nous arrive la quelle bouton ?, reset ? inscription ? connection ? mot de pass oublie ?
		// verifier que le email contient un aerobase et n'existe pas dans la bd et que les deux mots de passes sont egaux
		// verifier que le mot de passe est le meme que dans la bd
		// si mot de passe oublie on envoie un email automatique ou un sms ? ou question secrete ?
		
		//Date maDate= new Date();
		
		HttpSession session; // il faut ajouter l'utilisation de cookies et url et autres ?
		//int  visitCount=0;
		 
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		String password2=request.getParameter("password2");
		String name=request.getParameter("name");
		String firstname=request.getParameter("firstname");
		String datenaissance=request.getParameter("datenaissance");
		String sexe=request.getParameter("sexe");
		
		String insc=request.getParameter("inscription");
		String con=request.getParameter("connect");
		response.setContentType("text/html");
		if((insc == null && con != null) || (insc != null && con == null)){ // un seul bouton clicke

			 DAOUser du = new DAOUser();
			 User u;
		if(insc == null && con != null){ // connection
			if(!(email.equals("")) && !(password.equals(""))){
				
				// verifier patern mail
				// et password, minimum 4 caracteres -----------
				try{
				 u=du.getUserByEmail(email); // connexion bd
				 if(u != null){
					 if(password.equals(u.getPass())){ // verifier password et hasher !
						 
						
						 // RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html"); // revoyer la requete
						 
						 // il faut toujours donner a l'utilisateur la possibilite de se deconnecter ou savoir qu'il est connecte ?
					
						try{
						 session = request.getSession(false); // detruire les anciennes sessions
						 session.invalidate();
						}
						catch(Exception e){
						}
						 session = request.getSession(true);
						 response.getWriter().write(u.toStrig()); 
				         session.setAttribute("userID", u.getId());
				         
				         // si pas de contrat choix_xontrat sinon EspaceU 
				         if(u.getMon_contrat() == null){
				        	 response.sendRedirect("Choix_contrat");
				         }else{
				        	 response.sendRedirect("EspaceU");
				         }
						 
					 }else{
							
						 response.getWriter().write("<center> <br> mot de pass eronne ! <br> Clickez sur precedent dans votre navigateur pour retourner a  la page d'authentification </centre>");
					 }
				 }else{
					 response.getWriter().write("<center> <br> utilisateur introuvable ! <br> Clickez sur precedent dans votre navigateur pour retourner a  la page d'authentification </centre>");
				 }
				}catch(Exception e){
					response.getWriter().write("<center> <br> utilisateur introuvable ! <br> Clickez sur precedent dans votre navigateur pour retourner a  la page d'authentification </centre>");
				}

			}else{
				response.sendRedirect("index.html");
			}
		}else{ // inscription
			
			if(sexe == null || !(password.equals(password2)) || email.equals("") || password.equals("") || password2.equals("") || name.equals("") || firstname.equals("") || datenaissance.equals("")){
				response.sendRedirect("index.html");
			}else{
				//a faire, verifier le reste des paterns

				
				u=du.getUserByEmail(email);
				int j=0;
				int s=10;
				if(sexe.equals("homme")){ // completer ------------------
					s=1;
				}else{
					s=0;
				}
				try{
					j=Integer.parseInt(datenaissance);
				}catch (Exception e){
					response.sendRedirect("index.html");
				}
				if(u == null && j != 0 && j<100000000 && j>= 10000000){ // inscrire
					u=du.addUser(firstname, name, email, password, s, j, null);
					
				
					
					 session = request.getSession(true);
					 session.setAttribute("userID", u.getId());
					
			
					 response.sendRedirect("Choix_contrat");
					
					
				}else{ // utilisateur existant
					response.getWriter().write("<center> <br> Email dejas utilise ! <br> Clickez sur precedent dans votre navigateur pour retourner a  la page d'authentification </centre>");

				}
					
			}	
		}}	
	}
}
