package metier;
import java.util.Date;

/*
 * 
 *  un WikiVerif est valable 24h max !
 * 
 */

public class WikiVerif {
	

	private int id;
	private Station s;
	private User u;
	private int velos; 	// seules des information profitables à l'utilisateur sont saisies, donc les velos dispos
	private int places; // si les velos ou places dispos sont plus de 5, pas besoin de preciser le nombre juste tapper par exmeple 99
	private Date dandh; // valable un certain temps ou jusquà modification par api ou autres utilisateurs
	private String commentaire;
	
	public WikiVerif() {
		super();

	}
	
	
	public WikiVerif(int id, Station s, User u, int velos, int places,
			Date dandh) {
		super();
		this.id = id;
		this.s = s;
		this.u = u;
		this.velos = velos;
		this.places = places;
		this.dandh = dandh;
	}
	
	public WikiVerif(int id, Station s, User u, int velos, int places,
			Date dandh, String commentaire) {
		super();
		this.id = id;
		this.s = s;
		this.u = u;
		this.velos = velos;
		this.places = places;
		this.dandh = dandh;
		this.commentaire = commentaire;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Station getS() {
		return s;
	}
	public void setS(Station s) {
		this.s = s;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public int getVelos() {
		return velos;
	}
	public void setVelos(int velos) {
		this.velos = velos;
	}
	public int getPlaces() {
		return places;
	}
	public void setPlaces(int places) {
		this.places = places;
	}
	public Date getDandh() {
		return dandh;
	}
	public void setDandh(Date dandh) {
		this.dandh = dandh;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	/*
	public String getDandh() {
		return dandh;
	}
	
	public void setDandh(String dandh) {
		this.dandh = dandh;
	}
	*/
	

}
