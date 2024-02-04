package com.contacts.contactsAPI.controller;

import com.contacts.contactsAPI.dataaccess.ContactDao;
import com.contacts.contactsAPI.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
// Any other imports needed for annotations and types you use in this controller



import com.contacts.contactsAPI.dataaccess.ContactDao;
import com.contacts.contactsAPI.model.Contact;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class ContactsController {
private final ContactDao contactsDao;
@Autowired
public ContactsController(ContactDao dao) {
    this.contactsDao = dao;
}

@RequestMapping(path = "/contacts/{Contact_Id}", method = RequestMethod.GET)
public Contact getContactById(@PathVariable UUID Contact_Id) {
    return contactsDao.getContactById(Contact_Id);
}
@RequestMapping(path = "/contacts", method = RequestMethod.GET)
    public List<Contact> getAllContacts() {
    return contactsDao.getAllContacts();
}

    @GetMapping("/test")
    public String testEndpoint() {
        return "The test endpoint is working!";
    }
@RequestMapping(path = "contacts", method = RequestMethod.POST)
    public String addContact(@RequestBody Contact contact) {
        return contactsDao.addContact(contact);
}
    @RequestMapping(path = "/contacts/{Contact_Id}", method = RequestMethod.DELETE)
        public String deleteContactById(@PathVariable UUID Contact_Id) {
        return contactsDao.deleteContactById(Contact_Id);
    }
    @PostMapping(path = "/contacts/{Contact_Id}/uploadPhoto")
    public String uploadPhoto(@PathVariable UUID Contact_Id, @RequestParam("file") MultipartFile file) {
        try {
            return contactsDao.uploadPhoto(Contact_Id, file);
        } catch (Exception e) {
            log.error(STR."Error uploading photo: \{e.getMessage()}");
            return "Error uploading photo";
        }
    }
}