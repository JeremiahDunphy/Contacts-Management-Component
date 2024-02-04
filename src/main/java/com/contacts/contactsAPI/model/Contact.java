package com.contacts.contactsAPI.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Table(name = "Contacts")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "email")
    private String email;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private String phone; // Changed int to String for phone number

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private boolean status;

    @Column(name = "photo_url")
    private String photoUrl;


}
