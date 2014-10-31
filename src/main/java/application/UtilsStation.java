package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.*;
import domain.*;

public class UtilsStation {

	public UtilsStation(){
		
	}
	
	public Station[] getStations(double lat, double lng, int i, Contrat cont){
		
		ArrayList<Station> stations_contrat= new ArrayList<Station>();
		double mesDi []= new double [i];
		Station mesSt []= new Station [i];
		
		Double dst=0.0;
		
		Double tempo1=0.0;
		Double tempo2=0.0;
		Double tempo3=0.0;
		Double tempo4=0.0;
		Station s; // station en coure

		int nb=0; // toujours inférieure à i, ou <= le nombre de station plus proches que la station encoure,
		DAOStation ds= new DAOStation();
		stations_contrat=ds.getStatiosByContrat(cont); // toutes les stations du contrat, peut etre des miliers
		/*// le test macrhe
		for(int t=0; t<stations_contrat.size(); t++){ 
			System.out.println(stations_contrat.get(t).toString());
		}
		*/
		for(int j=0; j<stations_contrat.size(); j++){ //B1
			

			s=stations_contrat.get(j);
			
			// parcourire toutes les stations si il ya moins de i stations qui sont plus proches l'ajouter dans la liste
			
			tempo3= lat - s.getLat();
			tempo4= lng - s.getLng();
			
			tempo1= tempo3 * tempo3;
			tempo2= tempo4 * tempo4;
			
			dst= Math.sqrt(tempo1 + tempo2); // distance station et parametres
			
			if(nb < i){ // < care nb commence à zero et i c'est le nombre
				mesSt[nb]=s;
				mesDi[nb]=dst;
				nb++;
			}else{
				for(int k=0; k<i; k++){
					if(dst < mesDi[k]){
						mesDi[k]=dst;
						mesSt[k]=s;
					}
				}
			}
		
		} // B1

	return mesSt; // les stations ne sont pas le bon ordre
	}
}
