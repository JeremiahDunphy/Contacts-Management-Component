CREATE TABLE Contacts (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    title VARCHAR(255),
    email VARCHAR(255),
    location VARCHAR(255),
    phone VARCHAR(20),
    address VARCHAR(255),
    status BOOLEAN,
    photo_url VARCHAR(255)
);
