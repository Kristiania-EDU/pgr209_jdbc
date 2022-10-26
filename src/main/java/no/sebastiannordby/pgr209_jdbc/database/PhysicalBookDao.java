package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.models.Book;
import no.sebastiannordby.pgr209_jdbc.models.Library;
import no.sebastiannordby.pgr209_jdbc.models.PhysicalBook;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhysicalBookDao {
    private final DataSource dataSource;
    private final BookDao bookDao;

    public PhysicalBookDao(DataSource dataSource, BookDao bookDao) {
        this.dataSource = dataSource;
        this.bookDao = bookDao;
    }

    public List<Book> findByLibrary(long libraryId) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            var sql = """
                SELECT b.* FROM physical_books AS pb 
                INNER JOIN books AS b on pb.book_id = b.id
                where library_id = ?""";

            try(var statement =
                connection.prepareStatement(sql)) {
                statement.setLong(1, libraryId);

                try(var rs = statement.executeQuery()) {
                    var books = new ArrayList<Book>();

                    while(rs.next()) {
                        books.add(bookDao.readBook(rs));
                    }

                    return books;
                }
            }
        }
    }

    public void insert(Library library, Book book) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            var query = "INSERT INTO physical_books (library_id, book_id) VALUES(?, ?)";

            try(var statement = connection.prepareStatement(query)) {
                statement.setLong(1, library.getId());
                statement.setLong(2, book.getId());
                statement.executeUpdate();
            }
        }
    }

    private PhysicalBook readBook(ResultSet rs) throws SQLException {
        var physicalBook = new PhysicalBook();

        physicalBook.setId(rs.getLong("id"));
        physicalBook.setLibraryId(rs.getLong("libraryId"));
        physicalBook.setBookId(rs.getLong("bookId"));

        return physicalBook;
    }
}
