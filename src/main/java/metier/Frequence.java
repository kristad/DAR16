package metier;

public class Frequence {
	

	private Station station;
	private User u;
	private int frequenceD;
	private int frequenceA;
	private int id;
	//private static int ids=0;
	
	
	public Frequence() {
		super();
	}

	public Frequence(Station station, User frequence) {
		super();
		this.station = station;
		this.u = frequence;
		/*
		ids++; // elle s'incrémente à la création de chaque frequence
		id=ids;
		*/
	}
	
	public Frequence(Station station, User u, int frequenceD, int frequenceA,
			int id) {
		super();
		this.station = station;
		this.u = u;
		this.frequenceD = frequenceD;
		this.frequenceA = frequenceA;
		this.id = id;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	/*
	public static void setId(int id) {
		Frequence.id = id;
	}
	 */
	
	public int getFrequenceD() {
		return frequenceD;
	}
	
	public void setFrequenceD(int frequencedepart) {
		this.frequenceD = frequencedepart;
	}	
	
	public int getFrequenceA() {
		return frequenceA;
	}

	public void setFrequenceA(int frequencearrivee) {
		this.frequenceA = frequencearrivee;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}


	public String toStringD() {
		return " Nombre de Départs " + frequenceD;
	}
	public String toStringA() {
		return " Nombre d'Arrivées " + frequenceA;
	}


}
