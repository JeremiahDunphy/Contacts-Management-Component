package Controller;
import Model.Contact;
import Service.ContactsDao;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class ContactsController {
private final ContactsDao contactsDao;

public ContactsController(ContactsDao dao) {
    this.contactsDao = dao;
}

@RequestMapping(path = "/contacts/{Contact_Id}", method = RequestMethod.GET)
public Contact getContactById(@PathVariable UUID Contact_Id) {
return null;
}


}
