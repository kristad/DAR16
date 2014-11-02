package metier;

import java.util.Set;
import java.util.Date;

/*
 
Stations

Stations are represented as follows:

{
  "number": 123,
  "contract_name" : "Paris",
  "name": "stations name",
  "address": "address of the station",
  "position": {
    "lat": 48.862993,
    "lng": 2.344294
  },
  "banking": true,
  "bonus": false,
  "status": "OPEN",
  "bike_stands": 20,
  "available_bike_stands": 15,
  "available_bikes": 5,
  "last_update": <timestamp>
}

*/

public class Station {
	
	private int id;
	
	private Contrat mon_Contrat; 	// ContratId effacé
	
	// User wiki-Data
	
	private int UVelosDispos; 		// info actuelle calculé des récoltes des utilisateurs
	private int UPlacesDispos;
	private Set<WikiVerif> UVD; 	// toutes les infos du "jour" pour cette station
	private Set<WikiVerif> UPD;		// USER Velos/PLACES DISPONIBLES

	
	// Static data

    private long number; 			// number of the station. This is NOT an id, thus it is unique only inside a contract.
	private String contract_name; 	// name of the contract of the station
    private String name; 			// name of the station
    private String address; 		// address of the station. As it is raw data, sometimes it will be more of a comment than an address.
    private double lng;		// position of the station in WGS84 format
    private double lat;
    private boolean banking; 		// indicates whether this station has a payment terminal
    private boolean bonus;			// indicates whether this is a bonus station

    // Dynamic data

    private boolean status;			// indicates whether this station is CLOSEDor OPEN
    private int bike_stands; 		// the number of operational bike stands at this station
    private int available_bike_stands;// the number of available bike stands at this station
    private int available_bikes;	// the number of available and operational bikes at this station
    private Date last_update; 		// timestamp indicating the last update time in milliseconds since Epoch

	
	
	public int getUVelosDispos() {
		return UVelosDispos;
	}

	public void setUVelosDispos(int uVelosDispos) {
		UVelosDispos = uVelosDispos;
	}

	public int getUPlacesDispos() {
		return UPlacesDispos;
	}

	public void setUPlacesDispos(int uPlacesDispos) {
		UPlacesDispos = uPlacesDispos;
	}

	public Set<WikiVerif> getUVD() {
		return UVD;
	}

	public void setUVD(Set<WikiVerif> uVD) {
		UVD = uVD;
	}

	public Set<WikiVerif> getUPD() {
		return UPD;
	}

	public void setUPD(Set<WikiVerif> uPD) {
		UPD = uPD;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getContract_name() {
		return contract_name;
	}

	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isBanking() {
		return banking;
	}

	public void setBanking(boolean banking) {
		this.banking = banking;
	}

	public boolean isBonus() {
		return bonus;
	}

	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getBike_stands() {
		return bike_stands;
	}

	public void setBike_stands(int bike_stands) {
		this.bike_stands = bike_stands;
	}

	public int getAvailable_bike_stands() {
		return available_bike_stands;
	}

	public void setAvailable_bike_stands(int available_bike_stands) {
		this.available_bike_stands = available_bike_stands;
	}

	public int getAvailable_bikes() {
		return available_bikes;
	}

	public void setAvailable_bikes(int available_bikes) {
		this.available_bikes = available_bikes;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public Contrat getContrat() {
		return mon_Contrat;
	}

	public void setContrat(Contrat c) {
		this.mon_Contrat = c;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Contrat getMon_Contrat() {
		return mon_Contrat;
	}

	public void setMon_Contrat(Contrat mon_Contrat) {
		this.mon_Contrat = mon_Contrat;
	}

	@Override
	public String toString() { // il faut clicker pour plus d'infos
		return "Station: "+ name+ ", Adresse: " + address + ", Wiki: Velos: " + UVelosDispos + ", Places: "
				+ UPlacesDispos + ", CB: " + ((banking) ? "oui" : "non" ) + ", bonus: " + ((bonus) ? "oui" : "non" )
				+ ", Etat: " + ((status) ? "OK" : "Fermé" ) +", Places libres: " + available_bike_stands
				+ ", Velos disponibles: " + available_bikes;
	}

	public String toStringDyn() {
		return " Communauté: Velos disponibles: " + UVelosDispos + ", Places disponibles: " + UPlacesDispos +", JCdecaux: Places disponibles: " + available_bike_stands
				+ ", Velos disponibles: " + available_bikes;
	}

	public String toStringSta() { // il faut clicker pour plus d'infos
		return "Station: "+ name+ ", Adresse: " + address + ", CB: " + ((banking) ? "oui" : "non" ) + ", bonus: " + ((bonus) ? "oui" : "non" )
				+ ", Etat: " + ((status) ? "OK" : "Fermé" );
	}

}
