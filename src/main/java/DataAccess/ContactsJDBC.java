package DataAccess;
import Model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static java.lang.StringTemplate.STR;
@Repository
public class ContactsJDBC implements ContactDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ContactsJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Contact getContactById(UUID id) {
        Contact contact = null;
        String sql = "SELECT Contacts FROM Contacts WHERE id = ?;";
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
            while (rs.next()) {
                contact = mapRowsToContact(rs);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DataAccessException("Unable to connect to server or Database", e) {
            };
        }
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "Select * FROM Contacts;";
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
            while (rs.next()) {
                Contact contact = mapRowsToContact(rs);
                contacts.add(contact);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DataAccessException("Unable to connect to server or Database", e) {
            };
        }
        return contacts;
    }
    public String addContact(Contact contact) {
        UUID id;
        String sql = "INSERT INTO contacts (name, title, email, location, phone, address, status, photo_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING id";
        try {
            id = jdbcTemplate.queryForObject(sql, UUID.class, contact.getName(),
                    contact.getTitle(), contact.getEmail(), contact.getLocation(),
                    contact.getPhone(), contact.getAddress(), contact.isStatus(),
                    contact.getPhotoUrl());
            if (id != null) {
                contact.setId(id);
                return STR."The contact \{contact.getName()} was sucessfully added, with an id of \{contact.getId()}";
            } else {
                return "The Contact was not successfully added, please try again or contact an administrator.";
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or Database", e) {
            };
        } catch (DataAccessException e) {
            // Add more Exceptions here as needed.
            return "The Contact was not successfully added, please try again or contact an administrator.";
        }
    }
    @Override
    public String deleteContactById(UUID id) {
        String name;
        String sql = "DELETE FROM contacts WHERE id = ? RETURNING name; ";
        try {
            name = jdbcTemplate.queryForObject(sql, String.class, id);
            if(name != null) {
                return STR."The contact \{name} was sucessfully deleted.";
            } else {
                return "The contact was unsuccessfully added, please try again or contact your administrator.";
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new RuntimeException("We were unable to connect to the database, please try again or contact an Administrator", e) {
            };
            } catch (DataAccessException e) {
                // Add more Exceptions here as needed.
                return "The Contact was not successfully added, please try again or contact an administrator.";
        }

    }
    @Override
    public String uploadPhoto(UUID id, MultipartFile file) {
        try {
            // Store the file and get its URL
            String photoUrl = storeFileAndGetUrl(file);
            // Update the contact's photo URL in the database
            String sql = "UPDATE contacts SET photo_url = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, photoUrl, id);
            if (rowsAffected != 1) {
                return "Failed to update contact photo, please try again.";
            }
            //Tell the user that the upload was successful.
            return STR."Photo uploaded successfully for contact ID \{id}.";
        } catch (IOException e) {
            return "Failed to store the file, please try again.";
        } catch (DataAccessException e) {
            return "Database error occurred, please try again.";
        }
    }
    String storeFileAndGetUrl(MultipartFile file) throws IOException {
        // Absolute path to the directory where files will be stored
        String directoryPath = "/Users/jeremiahdunphy/Desktop/contactapi/src/main/resources/Photos";

        // Create a unique file name, perhaps using a UUID
        String fileName = STR."\{UUID.randomUUID().toString()}_\{file.getOriginalFilename()}";

        // Full path where the file will be stored
        String filePath = directoryPath + File.separator + fileName;

        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file
        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);

        // Return the file path or a URL to access the file
        // Adjust this return statement based on how you wish to access the file
        return filePath;
    }
    public Contact mapRowsToContact(SqlRowSet rs) {
    Contact contact = new Contact();
            contact.setId(UUID.fromString(rs.getString("id")));
            contact.setName(rs.getString("name"));
            contact.setTitle(rs.getString("email"));
            contact.setLocation(rs.getString("location"));
            contact.setPhone(rs.getString("phone"));
            contact.setAddress(rs.getString("address"));
            contact.setStatus(rs.getBoolean("status"));
            contact.setPhotoUrl(rs.getString("photo_url"));
        return contact;
    }
}
