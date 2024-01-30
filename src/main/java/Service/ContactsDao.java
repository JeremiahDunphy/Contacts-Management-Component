package Service;

import Model.Contact;

import java.util.List;

public interface ContactsDao {
public Contact getContactById();
public List<Contact> getAllContacts();

}
