package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.Frequence;
import metier.User;
import domain.DAOFrequence;
import domain.DAOUser;

/**
 * Servlet implementation class WikiVerif
 */
public class WikiVerif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WikiVerif() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//quand on fait un wikiverif la frequence augment aussi !
		
		HttpSession session;
		session = request.getSession(true);
		 if (session.isNew()){
				response.sendRedirect("index.html"); // anormale
			 
	      } else {

		    	DAOUser du= new DAOUser();
		    	User u= du.getUser((Integer)session.getAttribute("userID")); 
	    	  
		    	  
		    	DAOFrequence df= new DAOFrequence();
		    	ArrayList<Frequence> freqsa=df.getFrequenceByUserA(u);
		    	ArrayList<Frequence> freqsd=df.getFrequenceByUserD(u);
		    	
		  		PrintWriter ecrire = response.getWriter();
				response.setContentType("text/html");
				ecrire.write("");
	    	  
	    	  
	    	  
	      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
