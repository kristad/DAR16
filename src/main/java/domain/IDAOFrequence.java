package domain;

import java.util.ArrayList;

import metier.*;
import service.*;

public interface IDAOFrequence {

	
	public Frequence addFrequenceA(Station station, User user);
	public Frequence addFrequenceD(Station station, User user);
	public int deleteFrequence(int id);
	
	public Frequence getFrequence(int id);
	
	public boolean modifyFrequenceArrive( Station station, User user);
	
	public boolean modifyFrequenceDepart( Station station, User user);
	
	public ArrayList<Frequence> getFrequenceByUser(User user);
	public ArrayList<Frequence> getFrequenceByUserD(User user);
	public ArrayList<Frequence> getFrequenceByUserA(User user);
	public ArrayList<Frequence> getFrequenceByStation(Station station);
	
	public Frequence getFrequenceByUserAndStation(User user, Station station);
	
}
