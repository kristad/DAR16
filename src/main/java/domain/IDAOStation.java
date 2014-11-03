package domain;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;





import metier.*;


public interface IDAOStation {

	
	
	
	public Station addStation(  long number,	String contract_name, String name, String address, double lng,	double lat, Contrat contrat);
	
	public void deleteStation(int id);
	
	public boolean modifyStation(int id, int available_bikes, int available_bike_stands,Date last_update,boolean bonus, boolean status);
	
	public void deleteStationByContratname(String c);
	
	public Station getStation(int id);
	
	public boolean modifyStationByUser(int id, int UVelosDispos, int UPlacesDispos);
	
	public boolean modifyStation(int id, long number, String contract_name, String name, String address, double lng,	double lat, int bike_stands); // modification du station statique
	
	public boolean modifyStation(int id, int available_bikes, int available_bike_stands,  Date last_update); // modification par le API
	
	public boolean modifyStationConnected(long number, int bike_stands, boolean banking_boolean, int available_bikes,int available_bike_stands, Date last_update, boolean bonus, boolean status, Statement stmt_test);
	
	public boolean modifyStationConnected(String number,String available_bikes, String available_bike_stands, Date date, Statement mas);
	
	public boolean modifyStationConnected(long number, int available_bikes,int available_bike_stands, Date last_update, Statement stmt_test);
	
	public boolean modifyStationByUser(int id, int UVelosDispos, int UPlacesDispos,  Date last_update); // modification par utilisateur u
	
	public Station getStationByNumber(long number);
	
	public ArrayList<Station> getStatiosByContrat(Contrat cont);
	
	public Station getStationByName (String name);
	
	public Station getStationByAddress(String address);
	
	public Station getStationByPosition(String lat, String lng);
	
	public ArrayList<WikiVerif> getWikiDuStation(int id);

	/*
	private int UVelosDispos; 		// info actuelle calculé des récoltes des utilisateurs
	private int UPlacesDispos;
	private Set<WikiVerif> UVD; 	// toutes les infos du "jour" pour cette station
	private Set<WikiVerif> UPD;	
	*/
}
