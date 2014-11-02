package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import application.UtilsStation;
import metier.Frequence;
import metier.Station;
import metier.User;
import domain.DAOFrequence;
import domain.DAOStation;
import domain.DAOTrajet;
import domain.DAOUser;

/**
 * Servlet implementation class EspaceU
 */
public class EspaceU extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EspaceU() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session;
		session = request.getSession(false);
		 if (session.isNew()){
				response.sendRedirect("index.html"); 
			 
	      } else {

		    	DAOUser du= new DAOUser();
		    	User u= du.getUser((Integer)session.getAttribute("userID")); 
	    	  
		    	if(u.getMon_contrat() == null){
		    		response.sendRedirect("index.html"); 
		    	}else{
		    		
		    		// ici je suis connecté
		    		
			  		PrintWriter ecrire = response.getWriter();
					response.setContentType("text/html");
					// ces information doivent toujours être affichés
					
					ecrire.write("<html lang='fr'> <head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'/><meta name='viewport' content='width=device-width, initial-scale=1'/>");
					ecrire.write(" <title>Choix de contrat</title> <link href='css/bootstrap.min.css' rel='stylesheet'><link href='css/bootstrap-theme.min.css' rel='stylesheet'>");
					ecrire.write("<link href='theme.css' rel='stylesheet'> <script src='js/ie-emulation-modes-warning.js'></script>");
					ecrire.write("</head><body>");
					
					ecrire.write("<h2><a href='EspaceU' class='btn btn-primary btn-lg' role='button'>Allez vers votre Espace </a>");
					ecrire.write("&nbsp &nbsp");
					ecrire.write("<div align='right'><button type='button' class='btn btn-lg btn-warning ' ><a href='Choix_contrat?c=200' >Se deconecter</a></button><div><h2>");
					ecrire.write("<div align='left'>");
					
					ecrire.write("<button type='button' class='btn btn-lg btn-success'><a href='Profil'>Profil</a></button> &nbsp &nbsp");
					ecrire.write("<button type='button' class='btn btn-lg btn-info'><a href='Trajet'>Trajet</a></button> &nbsp &nbsp");
					ecrire.write("<button type='button' class='btn btn-lg btn-warning'><a href='Choix_contrat'>Changer le contrat:'"+u.getMon_contrat().getContract_name()+"' </a></button>");
 			        ecrire.write("</div>");
					
					
					// ici on gère une partie du trajet

					try{
						String sd="";
						String sa="";
						DAOStation ds= new DAOStation();
						Station mast= new Station();
						
						try{
						sd=request.getParameter("stationd");
						}catch(Exception e){}
						
						try{
						sa=request.getParameter("stationa");
						}catch(Exception e){}
					
				
						
						if((sd == "" || sd == null) && (sa == null || sa == "")){
							//
						}else{ // rentrer dans le try
						
							try{
						
								metier.Trajet t= new metier.Trajet(); // retourne deja l'id ici ?
								DAOTrajet dt=new DAOTrajet();
						
								int idt=0;
								
								try{ idt=(Integer)session.getAttribute("trajetID"); } catch(Exception e){}
								
								if(idt != 0 ){ // c'est à modifier pour la session ?
									t=dt.getTrajet(idt); 
									if(t != null){
									try{ if(sd != "" && sd != null){ // le modify ne marche pas !!!
										dt.modifyTrajetSD(t.getId(), ds.getStation(Integer.parseInt(sd)));}
									System.out.println("sddddddddddddddddddddddddddd");
									}catch(Exception e){}
									
									try{ if(sa != null && sa != ""){
										dt.modifyTrajetSA(t.getId(), ds.getStation(Integer.parseInt(sa)));} 
									System.out.println("saaaaaaaaaaaaaaaaaaaaaaaaa");
									}catch(Exception e){}
									
									}else{
										session.setAttribute("trajetID", 0);
										response.sendRedirect("EspaceU"); // anormmale !
									}
								}else{ // créer un trajet et le référencie dans la session
									
									try{
										if(sd != null && sd != "" ){
											mast=ds.getStation(Integer.parseInt(sd));
											//t.setSd(mast);
											t=dt.addTrajet(mast, null, u);
											int j=t.getId();
											session.setAttribute("trajetID", j);
										}
									}catch(Exception e){ }
									
									try{
										if(sa != null && sa != "" ){
											mast=ds.getStation(Integer.parseInt(sa));
											//t.setSa(mast);
											t=dt.addTrajet(null, mast, u);
											int j=t.getId();
											session.setAttribute("trajetID", j);
										}
									}catch(Exception e){ }
								}// fin else
							}catch(Exception e){ }
							
						}// fin else
					}catch(Exception e){ }
					
					
					
			    				// je donne ici la possibilité de chercher une station et de regarder l'historique
								// innerHTML, dan sle head ?
								
								ecrire.write("<br><hr><br>");
								
								
								
								
								
								
								// contenue antérieure de Maps.html
								//ecrire.write(" <style> html, body, #map-canvas { position: absolute; height: 300px; width: 300px; left: 0px;padding: 0px} #panel { position: absolute; top: 5px; left: 100%; margin-left: -180px; z-index: 5; background-color: #fff; padding: 5px; border: 1px solid #999; } </style> <div id='panel2' >ici</div> <script languague='javascript' src='https://maps.googleapis.com/maps/api/js?v=3.exp'></script> <script language='javascript' src='//code.jquery.com/jquery-1.10.2.js'></script> <script language='javascript'> var geocoder; var map; function initialize() { geocoder = new google.maps.Geocoder(); var latlng = new google.maps.LatLng(48.856614, 2.3522219000000177); var mapOptions = {  zoom: 8, center: latlng } map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions); } function codeAddress() { var address = document.getElementById('address').value; geocoder.geocode( { 'address': address}, function(results, status) { if (status == google.maps.GeocoderStatus.OK) { map.setCenter(results[0].geometry.location); var lat=results[0].geometry.location.lat(); var lng=results[0].geometry.location.lng();  var url='EspaceU?lat='+lat+'&lng='+lng; $('#panel2').load( url, function() {  }); var marker = new google.maps.Marker({  map: map, position: results[0].geometry.location }); } else { alert('Geocode was not successful for the following reason: ' + status); } }); } google.maps.event.addDomListener(window, 'load', initialize); </script>  <div id='map-canvas'></div><div id='panel'><input id='address' type='text' value='Paris, fr'><input type='button' value='Geocode' onclick='codeAddress()'></div>");
								// fin contenue antérieure de Maps.html
								
								
								
								
								
								// contenue antérieure de Trajet
			    	  			try{
			    	  				int max=5;
			    	  				UtilsStation us= new UtilsStation();
			    	  				Station mas=new Station();
			    	  				Station [] mesS= new Station[max];
			    	  				
			    	  				// ?var=&var2= dans l'url
			    	  				Double lat=Double.parseDouble(request.getParameter("lat"));
			    	  				Double lng=Double.parseDouble(request.getParameter("lng"));
				  				
			    	  				ecrire.write("<br><hr><br>");
			    	  				mesS= us.getStations(lat,lng,max,u.getMon_contrat());
			    	  				
			    	  				ecrire.write("<br> - Voici les stations de votre contrat les plus proches de vous: <br>"); 
			    	  				
			    	  				for(int i=0; i<mesS.length; i++){ // je peux faire plus proches et status ok après
			    	  					mas=mesS[i];
			    	  					try{
			    	  						ecrire.write(" &nbsp &nbsp <a href='StationSelect?id="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
			    	  						ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>");
			    	  					}catch(Exception e){
			    	  						System.out.println("plus proches");
			    	  					}
			    	  				}
			    	  				ecrire.write("<br><hr><br>");
			    	  			}catch(Exception e){ }
								
								// fin contenue de trajet
			    	  			
			    	  			
			    	  			
			    	  			
			    	  			
			    	  			
			    	  			
			    	  			
								// contenue antérieure de FreqenceDA
								  
							      try{
							    	  
							    	int max=5; 
							    	Station mas=null;
							    	String s="";
							  		DAOFrequence df= new DAOFrequence();
							  		metier.Frequence fa;
									metier.Frequence fd;
							    	  
							    	  	try{ s=request.getParameter("c"); } catch(Exception e){ }
							    	  		
							    	  		ArrayList<Frequence> freqsa=df.getFrequenceByUserA(u); //ordonnées déjas dans le DAO
							    	  		ArrayList<Frequence> freqsd=df.getFrequenceByUserD(u); 

							    	  		
							    	  		if(s == null || s ==""){
							    	  			
							    	  		// je retourne du texte simple avec lien vers servlet avec station séléctionné, ça sera dans le doPost
							    	  		// le premier accè à la servlet
							    	  		ecrire.write("<div style='background: url('http://www.eutouring.com/velib_m13_DSC00303FP_lrg.JPG'); repeat;'>");	
							    	  		ecrire.write("<br> <div class='alert alert-danger' role='alert' align='center'><strong>Allez à: </strong><a href='EspaceU?c=arrivee'>&nbsp &nbsp &nbsp Les stations les plus fréquentées à l'arrivée?</a></div> <br><br>"); // l'utilisateur doit spésifier
							    	  		
							    	  		for(int i=0; i<max; i++){
							    	  			try{	
							    	  				fd=freqsd.get(i);
							    	  				if(fd != null){ 
							    	  					mas=fd.getStation();
							    	  					ecrire.write(" &nbsp &nbsp <a href='StationSelect?id="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
							    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
							    	  				}
							    	  			}catch(Exception e){ }  
							    	  		}// fin for	
							    	  		
							    	  		}else{	// fin if du paramettre c= id station clické
							    	  		
							    	  		if(s.equals("depart")){
								    	  		ecrire.write("<br> <div class='alert alert-danger' role='alert' align='center'><strong>Allez à: </strong><a href='EspaceU?c=arrivee'>&nbsp &nbsp &nbsp Les stations les plus fréquentées à l'arrivée?</a></div> <br><br>"); // l'utilisateur doit spésifier
								    	  		
							    	  			for(int i=0; i<max; i++){
								    	  			try{	
								    	  				fd=freqsd.get(i);
								    	  				if(fd != null){ 
								    	  					mas=fd.getStation();
								    	  					ecrire.write(" &nbsp &nbsp <a href='StationSelect?id="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
								    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
								    	  				}
								    	  			}catch(Exception e){ }  
								    	  		}// fin fro	
							    	  		}// fin if
							    	  		
							    	  		if(s.equals("arrivee")){
							    	  			ecrire.write("<br> <div class='alert alert-success' align='center' role='alert'><strong>Allea à: </strong><a href='EspaceU?c=depart'>&nbsp &nbsp &nbsp Les stations les plus fréquentées au depart?</a></div> <br><br>"); // l'utilisateur doit spésifier
								    	  		for(int i=0; i<max; i++){
								    	  			try{	
								    	  				fa=freqsa.get(i);
								    	  				if(fa != null){ 
								    	  					mas=fa.getStation();
								    	  					ecrire.write(" &nbsp &nbsp <a href='StationSelect?id="+mas.getId()+"'>"+mas.toStringSta()+"</a><br>"); 
								    	  					ecrire.write(" &nbsp &nbsp "+mas.toStringDyn()+"<br>"); 
								    	  				}
								    	  			}catch(Exception e){ }  
								    	  		}
							    	  		}// fin if
							
							    	  	} // fin else
							    	  		
							    	  }catch(Exception e){}
							   // Fin contenue de FreqenceDA
							      
							      
							      
							      
							      
							      
							      
							      ecrire.write("</div></body></html>");
							      
								
				}// fin else principale
		    	
		
		    }  // fin else
	 } // fin doget
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
