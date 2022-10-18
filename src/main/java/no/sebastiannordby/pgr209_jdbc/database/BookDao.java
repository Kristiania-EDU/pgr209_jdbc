package no.sebastiannordby.pgr209_jdbc.database;

import java.sql.SQLException;
import java.sql.Statement;
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
            var sql = "INSERT INTO books(title) values(?)";

            try(var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, book.getTitle());
                statement.executeUpdate();

                try(var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    book.setId(generatedKeys.getLong("id"));
                }
            }
        }

        allBooks.put(book.getId(), book);
    }

    public Book retrieve(long id) {
        return allBooks.get(id);
    }
}
