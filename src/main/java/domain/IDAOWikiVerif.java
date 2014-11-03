package domain;

import java.util.ArrayList;
import java.util.Date;

import metier.*;

public interface IDAOWikiVerif {

	
	public WikiVerif addWikiVerif( Station s, User u, int velos, int places, Date dandh, String commentaire);
	
	public int deleteWikiVerif(int id);
	
	public WikiVerif getWikiVerif(int id);
	
	public boolean modifyWikiVefif(int id, Station s, User u, int velos, int places, Date dandh, String commentaire);
	
	public ArrayList<WikiVerif> getWikiVerifByUser(User user);
	
	public ArrayList<WikiVerif> getWikiVerifByStation(Station station); // only today normalement
	
	public ArrayList<WikiVerif> getWikiVerifByUserStation(User user, Station station);
	
	public ArrayList<WikiVerif> getWikiVerifByDate(Date dandh);
	
	
	public WikiVerif getWikiVerifByUserStationDate (User user, Station station, Date dandh);
	
	
}
