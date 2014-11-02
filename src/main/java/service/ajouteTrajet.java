                                        package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.Frequence;
import metier.Station;
import metier.Trajet;
import metier.User;
import domain.DAOFrequence;
import domain.DAOStation;
import domain.DAOTrajet;
import domain.DAOUser;

/**
 * Servlet implementation class ajouteTrajet
 */
public class ajouteTrajet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ajouteTrajet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session;
		int idt=0;
	  	DAOUser du = new DAOUser();
	  	DAOTrajet dt= new DAOTrajet();
		
		try{
			 session = request.getSession(false);

			 if (session.isNew()){
					response.sendRedirect("index.html"); 
		      } else {// connecté
		    	  

					User u= du.getUser((Integer)session.getAttribute("userID"));
					
			        if(u.getMon_contrat() == null){
			        	 response.sendRedirect("Choix_contrat"); //prioritaire
			        }else{
			        	
						try{ 
							idt=(Integer)session.getAttribute("trajetID"); 
							if(idt == 0){
								response.sendRedirect("EspaceU");
							}else{
								

								try{
									
									Trajet t=dt.getTrajet(idt); 
									
									/****************************************************/
									Calendar now = Calendar.getInstance();
									int year = now.get(Calendar.YEAR);
									int month = now.get(Calendar.MONTH); // Note: zero based!
									int day = now.get(Calendar.DAY_OF_MONTH);
									int hour = now.get(Calendar.HOUR_OF_DAY);
									int minute = now.get(Calendar.MINUTE);
									int second = now.get(Calendar.SECOND);
								
									
							        int mind=Integer.parseInt(request.getParameter("hdepart"));
									int trajetmin=Integer.parseInt(request.getParameter("duree"));
									
									DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
									Date date = new Date();
									dateFormat.format(date);

									
									hour += Math.round((minute + mind)/60);
									minute=(minute + mind)-(Math.round(((minute + mind)/60) * 60));
											
									Date hd=new Date();
									hd.setDate(day);
									hd.setMonth(month);
									hd.setYear(year);
									hd.setHours(hour);
									hd.setMinutes(minute);
									hd.setSeconds(second);

									
									hour += Math.round((minute+trajetmin)/60);
									minute=(minute + trajetmin)-(Math.round(((minute+trajetmin)/60) * 60));
									
									Date ha=new Date();
									ha.setDate(day);
									ha.setMonth(month);
									ha.setYear(year);
									ha.setHours(hour);
									ha.setMinutes(minute);
									ha.setSeconds(second);
									
									/************************************/
									
									String commentaire=request.getParameter("comment"); // et si vide ?
									
									//etatTrajet =1; par défaut sauf changé par la suite
							
									dt.modifyTrajet(t.getId(), t.getSd(), t.getSa(), u, date, hd, ha, 1, commentaire);
									response.sendRedirect("Trajets");
									
									// supprimer trajetID dans la session ici
									session.setAttribute("trajetID", 0);
						
								}catch(Exception e){
									response.sendRedirect("EspaceU"); 
								}
								
							}
						} 
						catch(Exception e){
							response.sendRedirect("EspaceU");
						}
						
			        }
		      }
		}catch(Exception e){
			
			response.sendRedirect("EspaceU"); 
	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idStD=Integer.parseInt(request.getParameter("stationD"));
		int idStA=Integer.parseInt(request.getParameter("stationA"));
		String comm=request.getParameter("comment");
		
		/****************************************************/
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH); // Note: zero based!
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
	
		
        int mind=Integer.parseInt(request.getParameter("heured"));
		int trajetmin=Integer.parseInt(request.getParameter("heurea"));
		
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		Date date = new Date();
		dateFormat.format(date);

		
		hour += Math.round((minute + mind)/60);
		minute=(minute + mind)-(Math.round(((minute + mind)/60) * 60));
				
		Date hd=new Date();
		hd.setDate(day);
		hd.setMonth(month);
		hd.setYear(year);
		hd.setHours(hour);
		hd.setMinutes(minute);
		hd.setSeconds(second);

		
		hour += Math.round((minute+trajetmin)/60);
		minute=(minute + trajetmin)-(Math.round(((minute+trajetmin)/60) * 60));
		
		Date ha=new Date();
		ha.setDate(day);
		ha.setMonth(month);
		ha.setYear(year);
		ha.setHours(hour);
		ha.setMinutes(minute);
		ha.setSeconds(second);
		
		/************************************/
	    
	
		DAOUser du=new DAOUser();
		User u=du.getUser(1);
		System.out.println("....................................................USER "+u.toStrig());
		DAOStation ds=new DAOStation();
		Station sta=ds.getStation(idStA);

		Station std=ds.getStation(idStD);

		DAOFrequence dfreq= new DAOFrequence();
		
		
		DAOTrajet dt=new DAOTrajet();
	   Trajet trajet=dt.getTrajetByStationUser(std, sta, u);
	   int idtrajet=trajet.getId();
	   System.out.println("..........................."+trajet.getId());
	      if (idtrajet != 0){  System.out.println("...........................TRAJET existe");
	    	  
	    	    Trajet tr= new Trajet();
		     	tr=dt.getTrajetByStationUser(std, sta, u);
		    	dfreq.modifyFrequenceDepart(std, u);
		     	dfreq.modifyFrequenceArrive(sta, u);
	      }
	      else{  System.out.println("...........................TRAJET n'existe");
	    	
	          dt.addTrajet(std, sta, u, date, hd ,ha, 1, comm);
	          System.out.println("...........................CREATION TRAJET");
	          Frequence f=new Frequence();
	          f=dfreq.getFrequenceByUserAndStation(u, std);
	          int idfreqd=f.getId();
	          f=dfreq.getFrequenceByUserAndStation(u, sta);
	          int idfreqa=f.getId();
	    	  if (idfreqd == 0 && idfreqa ==0 )    // l'utilisateur a utilisé le station de depart
	    	  {
	    		  dfreq.addFrequenceD(std, u);
	    		  	dfreq.addFrequenceA(sta, u);
	    		  	System.out.println("...........................AUCUNE STAT UTILISE");
	    	  }
	    	  else if  ( idfreqa !=0)                                                      // l'utilisateur a utilisé le station d'arrive
	    	  {
	    		  dfreq.modifyFrequenceArrive(sta, u);
	    		  dfreq.addFrequenceD(std, u);
	    		  
	    		  System.out.println("...........................STAT ARRIVE UTILISE");
	    	  }
	    	  else
	    	  {
                    dfreq.modifyFrequenceDepart(std, u);
                    dfreq.addFrequenceA(sta, u);
	    		  System.out.println("...........................STAT DEPART  UTILISE");
	    		  	
	    	  	}
	    	  }
	      }

	}
	    



