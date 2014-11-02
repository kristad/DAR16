package metier;

import java.util.Set;



/* 

Contracts

For each client agglomeration of JCDecaux, there's an associated contract:

{
  "name" : "Paris",
  "commercial_name" : "Vélib'",
  "country_code" : "FR",
  "cities" : [
    "Paris",
    "Neuilly",
    ...
  ]
}

*/


public class Contrat {

	private String country_code; 	// country_code is the code (ISO 3166) of the country
	private String commercial_name; //  commercial_name is the commercial name of the contract, the one users usually know
	private String contract_name; 	// name is the identifier of the contract
	private int id;
	/*
	private String[] cities; 	// cities the cities that are concerned by this contract
	
	// je n'arrive pas utiliser array dans les hbm.xml donc j'utilise les set à la place
	*/
	private String cities; 	// cities the cities that are concerned by this contract
	

	
	public Contrat(String country_code, String commercial_name,
			String contract_name, int id, String cities) {
		super();
		this.country_code = country_code;
		this.commercial_name = commercial_name;
		this.contract_name = contract_name;
		this.id = id;
		this.cities = cities;
	}
	

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCommercial_name() {
		return commercial_name;
	}

	public void setCommercial_name(String commercial_name) {
		this.commercial_name = commercial_name;
	}

	public String getContract_name() {
		return contract_name;
	}

	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}
	

	@Override
	public String toString() {
		return "Contrat "+ id +". Country_code=" + country_code + ", Commercial_name="
				+ commercial_name + ", Contract_name=" + contract_name +"]";
	}

	public String toStringcities() {
		return "Cities: " + cities + ".";
	}
	public Contrat(){
	}
	
}
