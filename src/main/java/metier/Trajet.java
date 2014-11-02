package metier;
import java.util.Date;

public class Trajet {

	private int id;
	private Station Sd;
	private Station Sa;
	private User u;
	private Date danh;
	private String commentaire;
	private Date heured; 	// par defaut = danh : qui est la date de la saisie du trajet
	private Date heureA; 	// ppar defaut = heured + 30 to 45 minutes
	private int etatTrajet; // confirme/ execute/annulle/enretard...

	public Trajet() {
		super();

	}
	
	
	public Trajet(int id, Station sd, Station sa, User u, Date danh) {
		super();
		this.id = id;
		Sd = sd;
		Sa = sa;
		this.u = u;
		this.danh = danh;
	}
	
	public Trajet(int id, Station sd, Station sa, User u, Date danh,
			Date heured, Date heureA, int etatTrajet) {
		super();
		this.id = id;
		Sd = sd;
		Sa = sa;
		this.u = u;
		this.danh = danh;
		this.heured = heured;
		this.heureA = heureA;
		this.etatTrajet = etatTrajet;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Station getSd() {
		return Sd;
	}

	public void setSd(Station sd) {
		Sd = sd;
	}

	public Station getSa() {
		return Sa;
	}

	public void setSa(Station sa) {
		Sa = sa;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public Date getDanh() {
		return danh;
	}

	public void setDanh(Date danh) {
		this.danh = danh;
	}

	public Date getHeured() {
		return heured;
	}

	public void setHeured(Date heured) {
		this.heured = heured;
	}

	public Date getHeureA() {
		return heureA;
	}

	public void setHeureA(Date heureA) {
		this.heureA = heureA;
	}

	public int getEtatTrajet() {
		return etatTrajet;
	}

	public void setEtatTrajet(int etatTrajet) {
		this.etatTrajet = etatTrajet;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	//@Override
	public String toString_() {
		return "Trajet [id=" + id + ", Sd=" + Sd + ", Sa=" + Sa + ", u=" + u
				+ ", danh=" + danh + ", commentaire=" + commentaire
				+ ", heured=" + heured + ", heureA=" + heureA + ", etatTrajet="
				+ etatTrajet + "]";
	}
	
	public String toString() { //html
		return "Station de départ=" + Sd.getAddress() + ", Station d'arrivée=" + Sa.getAddress()+ "<br> Heure de départ:" + heured + ", heure d'arrivée prévue:" + heureA + ((etatTrajet ==  1)? "": "Etat du trajet: "+ etatTrajet )+ (commentaire.equals("")? "" : " <br> Commentaire: "+ commentaire);
	}
	
	
	
}
