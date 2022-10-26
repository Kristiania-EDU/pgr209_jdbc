CREATE TABLE physical_books(
    id SERIAL PRIMARY KEY,
    library_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    CONSTRAINT Library_PB_FK FOREIGN KEY (library_id) REFERENCES libraries(id),
    CONSTRAINT Book_PB_FK FOREIGN KEY (book_id) REFERENCES books(id)
);