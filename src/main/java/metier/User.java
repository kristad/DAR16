package metier;

import java.util.Set;

public class User {
	

	private Contrat mon_contrat;
	private String name;
	private String firstName;
	private String email;
	private int sexe;
	private int dateNaissance;
	private String pass; 				// prof de dar a dit qu'il ne faut pas mettre un pass en claire il faut utiliser un hash ?
	private int id;
	private Set<Frequence> frequences; // pour quoi pas un tableau ?

	public User(){
		super();
	}
	
	public User(String name, String firstName, String email, int sexe,
			int dateNaissance, String pass, int id, Set<Frequence> frequences) {
		this(name, firstName, email, pass, id, frequences);
		this.sexe = sexe;
		this.dateNaissance = dateNaissance;

	}
	
	public User(String name, String firstName, String email, String pass,
			int id, Set<Frequence> frequences) {
		this(name,firstName, email, pass);
		this.id = id;
		this.frequences = frequences;
	}
	
	public User(String name, String firstName, String email, String pass) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.pass = pass;
	}
	
	public User(String name, String firstName, String email, String pass, int sexe, int dateNaissance) {
		this(name,firstName, email, pass);
		this.sexe=sexe;
		this.dateNaissance=dateNaissance;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Frequence> getFrequences() {
		return frequences;
	}
	public void setFrequences(Set<Frequence> frequences) {
		this.frequences = frequences;
	}
	
	
	public Station getFAM() { // frequence arrivee max
		Frequence temp=(Frequence) null;
		for (Frequence a: frequences){
			if(temp.getFrequenceA()<a.getFrequenceA()){
				temp=a;
			}
		}
		return temp.getStation();
	}
	public Station getFDM() { // frequence depart max
		Frequence temp=(Frequence) null;
		for (Frequence a: frequences){
			if(temp.getFrequenceD()<a.getFrequenceD()){
				temp=a;
			}
		}
		return temp.getStation();
	}

	
	public void setFrequenceD(Station f) {
		for (Frequence a: frequences){
			if(f.getId()==a.getStation().getId()){
				a.setFrequenceD(a.getFrequenceD()+1);
			}
		}
	}
	public void setFrequenceA(Station f) {
		for (Frequence a: frequences){
			if(f.getId()==a.getStation().getId()){
				a.setFrequenceA(a.getFrequenceA()+1);
			}
		}
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(int dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Contrat getMon_contrat() {
		return mon_contrat;
	}

	public void setMon_contrat(Contrat mon_contrat) {
		this.mon_contrat = mon_contrat;
	}

	public int getSexe() {
		return sexe;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public String toStrig() {

		return   " LastName: " + name + ", FirstName: " + firstName + ", Email: " + email + ", Sexe: "+ sexe + ", dateNaissance: " + dateNaissance + ", Pass: " + pass + ", Id: " + id;

	}
	
}
