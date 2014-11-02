package domain;

import java.util.ArrayList;
import java.util.Date;

import metier.*;


public interface IDAOTrajet {

	
	public Trajet addTrajet(Station sd, Station sa, User u, Date danh,
			Date heured, Date heureA, int etatTrajet, String commentaire);
	public Trajet addTrajet(Station sd, Station sa, User u);
	
	public int deleteTrajet(int id);
	
	public Trajet getTrajet(int id);
	
	public boolean modifyTrajet(int id, Station sd, Station sa, User u, Date danh,
			Date heured, Date heureA, int etatTrajet, String Commentaire);
	
	public ArrayList<Trajet> getTrajetsByStationDepart(Station sd);
	
	public ArrayList<Trajet> getTrajetsByStationArrive(Station sa);
	
	public ArrayList<Trajet> getTrajetByUser(User u);
	
	public Trajet getTrajetByStationUser(Station stationd, Station stationa, User user);
	
	public Trajet getTrajet (Station station, User user, Date heured);
	
	public ArrayList<Trajet> getTrajetByEtat (int etatTrajet);
	
	public ArrayList<Trajet> getTrajetByDate (Date danh);
	
	
}
