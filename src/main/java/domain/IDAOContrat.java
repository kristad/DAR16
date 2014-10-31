package domain;

import java.util.ArrayList;


import metier.*;

public interface IDAOContrat {

	
	public Contrat addContrat(String country_code, String commercial_name, String contract_name, String cities);
	
	public int deleteContrat(int id);
	
	public int deleteContratByContratName( String contract_name);
	
	public Contrat getContrat(int id);
	
	public boolean modifyContrat(int id, String country_code, String commercial_name, String contract_name, String citie);
	
	public Contrat getContratByCommercialName(String commercial_name);
	
	public Contrat getContratByContractName(String contract_name);
	
	public ArrayList<Contrat> getContratByCountryName(String country_code);
	
}
