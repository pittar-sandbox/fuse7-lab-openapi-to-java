INSERT INTO books (isbn, title, author, publisher, genre) VALUES ('1234', 'The Shining', 'Stephen King', 'Random House', 'Horror') ON CONFLICT (isbn) DO NOTHING;
INSERT INTO books (isbn, title, author, publisher, genre) VALUES ('3456', 'The Name of the Wind', 'Patrick Rothfuss', 'Columbia', 'Fantasy') ON CONFLICT (isbn) DO NOTHING;