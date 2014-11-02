package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import domain.*;
import application.*;
import metier.*;

/**
 * Servlet implementation class Profil
 */
public class Profil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profil() {
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
			    		
			    		ecrire.write("<html lang='fr'><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'> ");
			    		ecrire.write("<title>EspaceUtilisateur</title>");
			    		ecrire.write("<link href='css/bootstrap.min.css' rel='stylesheet'> <link href='signin.css' rel='stylesheet'> <script src='js/ie-emulation-modes-warning.js'></script>");
			    		ecrire.write("<script src='https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'></script><script src='https://oss.maxcdn.com/respond/1.4.2/respond.min.js'></script>");
			    		ecrire.write("<style>td {vertical-align:top; padding: 200px; padding-top: 100px; text-align:left;} .td1{vertical-align:top; }</style>");
			    		ecrire.write("</head><body><h3 style='color:red;text-decoration: overline;text-transform: capitalize; generic-family:serif; '><a href='Choix_contrat'> Retourne Espace Utilisateur '"+u.getFirstName()+"' '"+u.getName()+"'</a></h3>&nbsp&nbsp&nbsp");
   ecrire.write("<table><tr><td style='backgroundcolor: rose'>");
			    	//	ecrire.write("<div align='left' style=' position:relative;top:20px; left:60px; width:50px;'>");
			    		ecrire.write("<div class='alert alert-success' role='alert' ><strong>Données personels</strong></div>");
                        ecrire.write(" <h4>Prénom</h4><p>'"+u.getFirstName()+"'</p>");
                        ecrire.write(" <h4>Nom</h4><p>'"+u.getName()+"'</p>");
                        ecrire.write(" <h4>Date de naissance</h4><p>'"+u.getDateNaissance()+"'</p>");
                        String s;
                        if(u.getSexe() == 0)
                        	s="Homme";
                        else s="Femme";
                        ecrire.write(" <h4>Sexe</h4><p>'"+s+"'</p>");
                        ecrire.write(" <h4>E-mail</h4><p>'"+u.getEmail()+"'</p>");
                        ecrire.write(" <h4>Contrat</h4><p>'"+u.getMon_contrat().getCommercial_name()+"'</p>");
			    		ecrire.write("</div></td>"); 
			    		ecrire.write("<td><div class='alert alert-warning' role='alert'><strong>Veillez saisir vos nouvelles données:</strong></div>");		 
			        	ecrire.write("<form method='post' action='Profil'><br><i>Email: <input type='text' name='email'></i><br><i>Password: <input type='password' name='password'></i><br>Confirmer Password: <input type='password' name='password2' size='25'><br>Nom: <input type='text' name='name' size='25'><br>Prénom: <input type='text' name='firstname' size='25'><br>Sexe: <label><input type='radio' name='sexe' value='femme'>femme</label><label><input type='radio' name='sexe' value='homme'>homme</label><br>Date de naissance  <input type='text' name='datenaissance' size='25'> <br>sans separateurs, ex: 18011986<br><input class='button' type='submit' name='inscription' value='Modifier' /> <input class='button' type='reset' value='Reset'></form></div>");
			        	ecrire.write("</div></td></tr></table>"); 
			        	
			        	
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
		
		// on rentre ici si l'utilisateur est connecté

				HttpSession session; 

				String email=request.getParameter("email");
				String password=request.getParameter("password");
				
				String password2=request.getParameter("password2");
				String name=request.getParameter("name");
				String firstname=request.getParameter("firstname");
				String datenaissance=request.getParameter("datenaissance");
				String sexe=request.getParameter("sexe");
				
				String insc=request.getParameter("inscription");
				
				
				try{
					 session = request.getSession(false);

					 if (session.isNew()){
							response.sendRedirect("index.html"); 
				      } else {// connecté, possibilité de tout modifier, c'est dangereux niveau sécurité ?
				    	  
				    	  	DAOUser du = new DAOUser();
							User u= du.getUser((Integer)session.getAttribute("userID"));
							
					        if(u.getMon_contrat() == null){
					        	 response.sendRedirect("Choix_contrat"); // c'est prioritaire avant tout
					        }else{
					        	
					        	if(insc != null){  // inscription
									
									
									if(sexe == null || !(password.equals(password2)) || email.equals("") || password.equals("") || password2.equals("") || name.equals("") || firstname.equals("") || datenaissance.equals("")){
										response.sendRedirect("Profil"); // erreure de saisie
									}else{
										// possibilité de tout modifier, c'est dangereux niveau sécurité ?
										
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
												response.sendRedirect("Profil");
											}
											
											if(u != null && j != 0 && j<100000000 && j>= 10000000){ // inscrire
												try{
													du.modifyUser(u.getId(), firstname, name, email, password, s, j, u.getMon_contrat());
												}catch(Exception e){
													
												}
											}
										
										// il faut afficher le profil et les changements éffectués
											response.sendRedirect("Profil");
											}
					        	}
					        }
					}
				}
				catch(Exception e){
					response.sendRedirect("index.html");
				}
				
					
			}// fin doPOst
					
				
}// finclass
