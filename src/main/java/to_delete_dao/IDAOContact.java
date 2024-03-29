package to_delete_dao;

import java.util.ArrayList;

public interface IDAOContact {

	
	public Contact addContact(long idContact, String firstname, String lastname, String email);
	
	public int deleteContact(long id);
	
	public Contact getContact(long id);
	
	public boolean modifyContact(long id, String firstname, String lastname, String email);
	
	public ArrayList<Contact> getContactByFirstName(String firstname);
	
	public ArrayList<Contact> getContactByLastName(String lastname);
	
	public ArrayList<Contact> getContactByEmail(String email);
	
}
