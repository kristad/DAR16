package metier;

import java.util.Set;

public class Quee {
	

	private Station station;
	private Set<User> usersV;
	private Set<User> usersP;
	
	
	
	public Quee(Station station) { // FIFO
		super();
		this.station = station;
	}
	
	public Quee(Station station, Set<User> usersV, Set<User> usersP) {
		this(station);
		this.usersV = usersV;
		this.usersP = usersP;
	}
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public Set<User> getUsersV() {
		return usersV;
	}
	public void setUsersV(Set<User> usersV) {
		this.usersV = usersV;
	}
	public Set<User> getUsersP() {
		return usersP;
	}
	public void setUsersP(Set<User> usersP) {
		this.usersP = usersP;
	}
	
	
	// à écrire et modifier
	
	public void reordreFrom(int i){
		
	}
	public int getRang(User u){ // combien de personnes devant vous ?
		return 0; // un user peut s'inscrire dans une seule quee, le 0 à modifier
	}
	public int cancelUser(User u){ // deadline tempo
		reordreFrom(getRang(u));
		return 0; // un user peut s'inscrire dans une seule quee, le 0 à modifier
	}
	public int goUser(User u){
		reordreFrom(getRang(u));
		return 0; // un user peut s'inscrire dans une seule quee, le 0 à modifier
	}
	public int putInV(User u){ // return rang
		return 0; // le 0 à modifier
	}
	public int putInP(User u){ // return rang
		return 0; //  le 0 à modifier
	}
	
	
}
