package com.contacts.contactsAPI.Controller;
// For the Application class


import com.contacts.contactsAPI.controller.ContactsController;
import com.contacts.contactsAPI.dataaccess.ContactDao;
import com.contacts.contactsAPI.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactsController.class)
class ContactsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactDao contactDao;

    // Example contact
    private Contact contact;
    private UUID contactId;

    @BeforeEach
    void setUp() {
        contactId = UUID.randomUUID();
        contact = new Contact();
        contact.setId(contactId);
        // Set other properties of the contact
    }

    @Test
    void getContactByIdShouldReturnContact() throws Exception {
        when(contactDao.getContactById(contactId)).thenReturn(contact);

        mockMvc.perform(get("/contacts/{Contact_Id}", contactId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contactId.toString())));
        // Add other JSON path checks for contact fields

        verify(contactDao).getContactById(contactId);
    }

    @Test
    void getAllContactsShouldReturnContactsList() throws Exception {
        when(contactDao.getAllContacts()).thenReturn(Arrays.asList(contact));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(contactId.toString())));
        // Add other JSON path checks for contact fields

        verify(contactDao).getAllContacts();
    }

    @Test
    void addContactShouldReturnSuccessMessage() throws Exception {
        String successMessage = "Contact added";
        when(contactDao.addContact(any(Contact.class))).thenReturn(successMessage);

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"New Contact\", \"email\": \"contact@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(successMessage)));

        verify(contactDao).addContact(any(Contact.class));
    }

    @Test
    void deleteContactByIdShouldReturnSuccessMessage() throws Exception {
        String successMessage = "Contact deleted";
        when(contactDao.deleteContactById(contactId)).thenReturn(successMessage);

        mockMvc.perform(put("/contacts/{Contact_Id}", contactId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(successMessage)));

        verify(contactDao).deleteContactById(contactId);
    }

    @Test
    void uploadPhotoShouldReturnSuccessMessage() throws Exception {
        String successMessage = "Photo uploaded successfully";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        when(contactDao.uploadPhoto(eq(contactId), any(MultipartFile.class))).thenReturn(successMessage);

        mockMvc.perform(multipart("/contacts/{Contact_Id}/uploadPhoto", contactId)
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(successMessage)));

        verify(contactDao).uploadPhoto(eq(contactId), any(MultipartFile.class));
    }
}
