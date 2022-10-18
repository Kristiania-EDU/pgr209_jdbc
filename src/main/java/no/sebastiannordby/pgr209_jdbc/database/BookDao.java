package no.sebastiannordby.pgr209_jdbc.database;

import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class BookDao {
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
    }

    public Book retrieve(long id) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);

                try(var resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        var book = new Book();

                        book.setId(resultSet.getLong("id"));
                        book.setTitle(resultSet.getString("title"));

                        return book;
                    }

                    return null;
                }
            }
        }
    }
}
