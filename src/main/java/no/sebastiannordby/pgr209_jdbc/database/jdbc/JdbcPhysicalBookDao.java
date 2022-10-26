package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.PhysicalBookDao;
import no.sebastiannordby.pgr209_jdbc.models.Book;
import no.sebastiannordby.pgr209_jdbc.models.Library;
import no.sebastiannordby.pgr209_jdbc.models.PhysicalBook;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPhysicalBookDao implements PhysicalBookDao {
    private final DataSource dataSource;
    private final JdbcBookDao bookDao;

    public JdbcPhysicalBookDao(DataSource dataSource, JdbcBookDao bookDao) {
        this.dataSource = dataSource;
        this.bookDao = bookDao;
    }

    @Override
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

    @Override
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

    @Override
    public PhysicalBook readBook(ResultSet rs) throws SQLException {
        var physicalBook = new PhysicalBook();

        physicalBook.setId(rs.getLong("id"));
        physicalBook.setLibraryId(rs.getLong("libraryId"));
        physicalBook.setBookId(rs.getLong("bookId"));

        return physicalBook;
    }
}
