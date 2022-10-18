package no.sebastiannordby.pgr209_jdbc.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class BookDao {
    private Map<Long, Book> allBooks = new HashMap<>();
    private DataSource dataSource;

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            try(var statement =
                    connection.prepareStatement("INSERT INTO Books(title) value(?)")) {
                statement.setString(1, book.getTitle());
                statement.executeUpdate();
            }
        }

        book.setId(allBooks.size());
        allBooks.put(book.getId(), book);
    }

    public Book retrieve(long id) {
        return allBooks.get(id);
    }
}
