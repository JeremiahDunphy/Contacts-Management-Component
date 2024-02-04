package dataaccess;

import com.contacts.contactsAPI.dataaccess.ContactsJDBC;
import com.contacts.contactsAPI.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContactapiApplicationTests {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private SqlRowSet sqlRowSet;

	@InjectMocks
	private ContactsJDBC contactsJDBC;

	private Contact contact;
	private UUID contactId;

	@BeforeEach
	void setUp() {
		contactId = UUID.randomUUID();
		contact = new Contact();
		contact.setId(contactId);
		contact.setName("John Doe");
		contact.setTitle("Mr.");
		contact.setEmail("johndoe@example.com");
		contact.setLocation("Some Location");
		contact.setPhone("1234567890");
		contact.setAddress("Some Address");
		contact.setStatus(true);
		contact.setPhotoUrl("http://example.com/photo.jpg");

		// Setup common mock behaviors
		when(jdbcTemplate.queryForRowSet(anyString(), any(UUID.class))).thenReturn(sqlRowSet);
		when(sqlRowSet.next()).thenReturn(true).thenReturn(false); // to simulate row iteration
		when(sqlRowSet.getString("id")).thenReturn(contact.getId().toString());
		when(sqlRowSet.getString("name")).thenReturn(contact.getName());
		when(sqlRowSet.getString("title")).thenReturn(contact.getTitle());
		when(sqlRowSet.getString("email")).thenReturn(contact.getEmail());
		when(sqlRowSet.getString("location")).thenReturn(contact.getLocation());
		when(sqlRowSet.getString("phone")).thenReturn(contact.getPhone());
		when(sqlRowSet.getString("address")).thenReturn(contact.getAddress());
		when(sqlRowSet.getBoolean("status")).thenReturn(contact.isStatus());
		when(sqlRowSet.getString("photo_url")).thenReturn(contact.getPhotoUrl());
	}

	@Test
	void getContactByIdShouldReturnContact() {
		// Arrange is handled in setUp

		// Act
		Contact found = contactsJDBC.getContactById(contactId);

		// Assert
		assertNotNull(found);
		assertEquals(contact.getId(), found.getId());
		assertEquals(contact.getName(), found.getName());
		// ... other properties
	}

	@Test
	void getAllContactsShouldReturnContactsList() {
		// Arrange is handled in setUp

		// Act
		List<Contact> contacts = contactsJDBC.getAllContacts();

		// Assert
		assertNotNull(contacts);
		assertFalse(contacts.isEmpty());
		assertEquals(contact.getId(), contacts.get(0).getId());
		assertEquals(contact.getName(), contacts.get(0).getName());
		// ... other properties
	}

	@Test
	void addContactShouldReturnSuccessMessage() {
		// Arrange
		when(jdbcTemplate.queryForObject(anyString(), eq(UUID.class), any(), any(), any(), any(), any(), any(), any()))
				.thenReturn(contactId);

		// Act
		String result = contactsJDBC.addContact(contact);

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("was successfully added"));
	}

	@Test
	void deleteContactByIdShouldReturnSuccessMessage() {
		// Arrange
		when(jdbcTemplate.queryForObject(anyString(), eq(String.class), any(UUID.class)))
				.thenReturn(contact.getName());

		// Act
		String result = contactsJDBC.deleteContactById(contactId);

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("was successfully deleted"));

	}

	@Test
	void uploadPhotoShouldReturnSuccessMessage() throws IOException {
		// Arrange
		String expectedUrl = "http://localhost/photos/test-image.jpg";
		MockMultipartFile mockMultipartFile = new MockMultipartFile(
				"file",
				"test-image.jpg",
				MediaType.IMAGE_JPEG_VALUE,
				"test image content".getBytes()
		);

		// Assume storeFileAndGetUrl would perform actual file saving and return a URL
		// For the test, we simulate this by returning a pre-defined URL
		when(contactsJDBC.storeFileAndGetUrl(mockMultipartFile)).thenReturn(expectedUrl);

		// Act
		String result = contactsJDBC.uploadPhoto(contactId, mockMultipartFile);

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("Photo uploaded successfully"));
	}

}
