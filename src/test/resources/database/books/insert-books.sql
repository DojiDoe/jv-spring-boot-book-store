INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (1, '1984', 'George Orwell', '978-0-596-52068-7', 9.99, 'Dystopian vision of the future', 'Cover image of eye', 0);
INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (2, 'Animal Farm', 'George Orwell', '978-0-596-52069-4', 6.99, 'Dystopian vision of the future for kids', 'Cover image of pig', 0);
INSERT INTO books_categories (book_id, category_id) VALUES (2, 1);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (3, 'I see that you are interested in darkness', 'Ilarion Pavliuk', '978-0-596-52077-9', 8.99, 'impenetrable human indifference and the darkness within us',
        'Cover image of eye', 0);
INSERT INTO books_categories(book_id, category_id) VALUES (3,1);