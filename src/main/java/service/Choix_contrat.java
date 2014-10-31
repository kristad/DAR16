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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int visitCount=0;
		HttpSession session;
		DAOContrat dc= new DAOContrat();
		Contrat c;
		String s=request.getParameter("c");
		

		
		PrintWriter ecrire = response.getWriter();
		response.setContentType("text/html");
		ecrire.write("<html><head> <meta charset='ISO-8859-1'><title>Séléctionner le contrat qui correspond à votre ville</title></head><body>");
		
		if(s==null){
		// se renseigner sur esponse.encodeUrl
			
		
		ecrire.write("<a href='g.html'>Retourner à la page précédente</a> <br><br><br> <center><h3>Séléctionner le contrat qui correspond à votre ville</h3><br></center>");
		
		for(int i=1; i<=26; i++){ // nombre totale de contrats est fixé à 26, à changer si il y a du nouveau
			
			c= dc.getContrat(i);

			ecrire.write("<br>&nbsp<a href='Choix_contrat?c="+i+"'>"); // je rappel la meme servelet avec le paramettre c.
			ecrire.write(c.toString());
			ecrire.write("</a><br>");
			ecrire.write(c.toStringcities());
			ecrire.write("<br>");

		}
		ecrire.write("</body><html>");
		}else
		{
			//fermeture session pas propre
			if(s.equals("200")){
				session = request.getSession(true);
				session.invalidate();
				
				response.sendRedirect("index.html"); 
			}else{
			
			
			c=dc.getContrat(Integer.parseInt(s));

			//ecrire.write(c.toString()); 
			
			
			 session = request.getSession(true);

			 if (session.isNew()){
					response.sendRedirect("index.html"); // anormale
				 
		      } else {

		    	  DAOUser du= new DAOUser();
		    	  User u= du.getUser((Integer)session.getAttribute("userID")); //!! à chaque foi je charge de la bd !!!! on ne peut pas sauver un objet dans la session ?
		    	  u.setMon_contrat(c);
		    	  
		    	  du.modifyUser(u.getId(), u.getFirstName(), u.getName(), u.getEmail(), u.getPass(), u.getSexe(), u.getDateNaissance(), c); 
		    	  
				response.sendRedirect("g.html");
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
