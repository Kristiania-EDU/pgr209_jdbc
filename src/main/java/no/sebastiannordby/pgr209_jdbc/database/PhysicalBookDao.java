package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.models.Book;
import no.sebastiannordby.pgr209_jdbc.models.Library;
import no.sebastiannordby.pgr209_jdbc.models.PhysicalBook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PhysicalBookDao {
    List<Book> findByLibrary(long libraryId) throws Exception;

    void insert(Library library, Book book) throws Exception;

    PhysicalBook readBook(ResultSet rs) throws Exception;
}
