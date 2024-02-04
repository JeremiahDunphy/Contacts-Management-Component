package com.contacts.contactsAPI.dataaccess;
import com.contacts.contactsAPI.model.Contact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ContactDao {
Contact getContactById(UUID id);
List<Contact> getAllContacts();
String addContact(Contact contact);
String deleteContactById(UUID id);
String uploadPhoto(UUID id, MultipartFile file);

}
