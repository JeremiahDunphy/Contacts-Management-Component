CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Drop the Contacts table if it already exists
DROP TABLE IF EXISTS Contacts;

-- Start the transaction
BEGIN;

CREATE TABLE Contacts (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name VARCHAR(255),
                          title VARCHAR(255),
                          email VARCHAR(255),
                          location VARCHAR(255),
                          phone VARCHAR(20),
                          address VARCHAR(255),
                          status BOOLEAN,
                          photo_url VARCHAR(255)
);

INSERT INTO Contacts (name, title, email, location, phone, address, status, photo_url) VALUES
                                                                                           ('Alice', 'Developer', 'alice@example.com', 'New York', '555-1234', '123 Main St', TRUE, 'http://example.com/photos/alice.jpg'),
                                                                                           ('Bob', 'Manager', 'bob@example.org', 'Los Angeles', '555-2345', '456 Elm St', FALSE, 'http://example.com/photos/bob.jpg'),
                                                                                           ('Charlie', 'Analyst', 'charlie@test.net', 'Chicago', '555-3456', '789 Oak St', TRUE, 'http://example.com/photos/charlie.jpg'),
                                                                                           ('David', 'Designer', 'david@sample.com', 'Houston', '555-4567', '101 Pine St', FALSE, 'http://example.com/photos/david.jpg'),
                                                                                           ('Eve', 'Engineer', 'eve@demo.com', 'Phoenix', '555-5678', '202 Maple St', TRUE, 'http://example.com/photos/eve.jpg'),
                                                                                           ('Frank', 'Director', 'frank@company.com', 'Philadelphia', '555-6789', '303 Birch St', FALSE, 'http://example.com/photos/frank.jpg'),
                                                                                           ('Grace', 'Coordinator', 'grace@organisation.net', 'San Antonio', '555-7890', '404 Cedar St', TRUE, 'http://example.com/photos/grace.jpg'),
                                                                                           ('Hank', 'Consultant', 'hank@enterprise.org', 'San Diego', '555-8901', '505 Dogwood St', FALSE, 'http://example.com/photos/hank.jpg'),
                                                                                           ('Ivy', 'Specialist', 'ivy@firm.co.uk', 'Dallas', '555-9012', '606 Fir St', TRUE, 'http://example.com/photos/ivy.jpg'),
                                                                                           ('Jack', 'Architect', 'jack@business.com', 'San Jose', '555-0123', '707 Redwood St', FALSE, 'http://example.com/photos/jack.jpg');

-- Commit the transaction
COMMIT;
