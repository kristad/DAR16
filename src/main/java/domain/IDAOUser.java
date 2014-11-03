package domain;

import java.util.ArrayList;
import metier.*;
import service.*;

public interface IDAOUser {

	
	public User addUser(String firstname, String lastname, String email, String pass, int sexe, int DateNaissance, Contrat idc);
	
	public int deleteUser(int id);
	
	public User getUser(int id);
	
	public boolean modifyUser(int id, String firstname, String lastname, String email, String pass, int sexe, int DateNaissance, Contrat idc);
	
	public ArrayList<User> getUserByFirstName(String firstname);
	
	public ArrayList<User> getUserByLastName(String lastname);
	
	public ArrayList<User> getUserByFisrtAndLastName(String firstname, String lastname);
	
	public User getUserByEmail(String email);
	
}
