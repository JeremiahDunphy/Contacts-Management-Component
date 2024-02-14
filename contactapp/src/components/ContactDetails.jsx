import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faMapMarkerAlt, faPhone, faHome, faHeart } from '@fortawesome/free-solid-svg-icons';
import ReactPaginate  from 'react-paginate'; // Import ReactPaginate

export function ContactDetails() {
    const [contacts, setContacts] = useState([]);
    const[currentPage, setCurrentPage] = useState(0);
    const itemsPerPage = 9;

    useEffect(() => {
        const fetchContacts = async () => {
            try {
                const responce = await axios.get('/api/contacts');
                setContacts(responce.data); // update state with fetched data
            } catch(error) {
                console.error("failed to fetch contacts: ", error);
            }
        };
        fetchContacts();
    }, []); // The empty array ensures this effect runs only once after the initial render

    //calculate the current state of displayed contacts
    const offset = currentPage * itemsPerPage;
    const currentPageContacts = contacts.slice(offset, offset + itemsPerPage);


    // Handle page click
    const handlePageClick = (data) => {
        setCurrentPage(data.selected); // Update the currentPage state with the selected page
    };
    return (
        <div>
            <h2 className="contact__header">Contact Details</h2>
            {contacts.length > 0 ? (
                <>
                <ul className="contact__list">
                    {currentPageContacts.map((contact) => (
                        <li key={contact.id} className="contact__item">
                            {contact.photo_url && (
                                <div className="contact__image">
                                    <img src={contact.photo_url} alt={contact.name} />
                                </div>
                            )}
                            <h3 className="contact_name">{contact.name}</h3>
                            {contact.title && <div className="contact_title">{contact.title}</div>}
                            <div className="contact__body">
                                {contact.email && (
                                    <p>
                                        <FontAwesomeIcon icon={faEnvelope} /> {contact.email}
                                    </p>
                                )}
                                {contact.location && (
                                    <p>
                                        <FontAwesomeIcon icon={faMapMarkerAlt} /> {contact.location}
                                    </p>
                                )}
                                {contact.phone && (
                                    <p>
                                        <FontAwesomeIcon icon={faPhone} /> {contact.phone}
                                    </p>
                                )}
                                {contact.address && (
                                    <p>
                                        <FontAwesomeIcon icon={faHome} /> {contact.address}
                                    </p>
                                )}
                                {contact.status && (
                                    <p>
                                        <FontAwesomeIcon icon={faHeart} /> {contact.status}
                                    </p>
                                )}

                            </div>
                        </li>
                    ))}
                </ul>
                    <ReactPaginate
                        previousLabel={"<"}
                        nextLabel={">"}
                        breakLabel={"..."}
                        breakClassName={"break-me"}
                        pageCount={Math.ceil(contacts.length / itemsPerPage)}
                        marginPagesDisplayed={2}
                        pageRangeDisplayed={5}
                        onPageChange={handlePageClick}
                        containerClassName={"pagination"}
                        activeClassName={"selected"}
                        previousClassName={"previous"}
                        nextClassName={"next"}
                        pageClassName={"page-item"}
                        pageLinkClassName={"page-link"}
                        disabledClassName={"disabled"}
                    />

                </>
            ) : (
                <p>No contacts found.</p>
            )}
        </div>
    );
}

