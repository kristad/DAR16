package service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.Contrat;
import metier.Station;
import metier.User;
import metier.WikiVerif;
import domain.DAOContrat;
import domain.DAOStation;
import domain.DAOUser;
import domain.DAOWikiVerif;

/**
 * Servlet implementation class VeloAvailable
 */
public class VeloAvailable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VeloAvailable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  testet le dernier ajout
		
		int idstation=Integer.parseInt(request.getParameter("station"));
		int places=Integer.parseInt(request.getParameter("places"));
		int velos=Integer.parseInt(request.getParameter("velos"));
		String comm=request.getParameter("comment");
		
		DAOStation stat=new DAOStation();
		Station station=new Station();
		station=stat.getStation(idstation);
		
		DAOUser du=new DAOUser();
		User us=new User(); 
		us=du.getUser(1);
		
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		Date date = new Date();
		dateFormat.format(date);
		
		
		DAOWikiVerif dw=new DAOWikiVerif();
		WikiVerif wiki= new WikiVerif();
		wiki=dw.addWikiVerif(station, us, velos, places, date, comm);
		station.setUPlacesDispos(places); // à tester // ajouter les wikis verif dans leurs station, le dernier a raison !
		station.setUVelosDispos(velos);
		
		station.getUPD().add(wiki); // ajouter dans la liste de la station
		station.getUVD().add(wiki);
		
		stat.modifyStationByUser(station.getId(), velos, places, station.getLast_update()); // ici on considère la lastupdate comme le dernier temps ou on a eu une iinfo et non seule de l'api selement ?!! pas necessaire !! car c'est enregistré dans les wikis, donc ne pas perdre une info
		// il faut alors faire update de la station modifié
	}

}
