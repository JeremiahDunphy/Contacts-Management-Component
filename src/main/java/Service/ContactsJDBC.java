package Service;

import Model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactsJDBC implements ContactsDao {
private List<Contact> contact;
private final JdbcTemplate jdbcTemplate;

@Autowired
    public ContactsJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Contact getContactById() {
    String sql = "SELECT Contact FROM Contact WHERE id = ?;";
    return null;
    }
    public List<Contact> getAllContacts() {
        String sql = "Select * FROM Contact;";
        return null;
    }

}
