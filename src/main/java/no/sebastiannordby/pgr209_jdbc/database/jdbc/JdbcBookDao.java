package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.BookDao;
import no.sebastiannordby.pgr209_jdbc.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JdbcBookDao implements BookDao {
    private DataSource dataSource;

    public JdbcBookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Book book) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            var sql = "INSERT INTO books(title, author) values(?, ?)";

            try(var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.executeUpdate();


                try(var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    book.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    @Override
    public Book retrieve(long id) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);

                try(var resultSet = statement.executeQuery()) {
                    return resultSet.next() ? readBook(resultSet) : null;
                }
            }
        }
    }

    @Override
    public List<Book> findByAuthorName(String authorName) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            var query = "SELECT * FROM BOOKS WHERE author = ?";

            try(var statement = connection.prepareStatement(query)) {
                statement.setString(1, authorName);

                try(var resultSet = statement.executeQuery()) {
                    var result = new ArrayList<Book>();

                    while(resultSet.next()) {
                        result.add(readBook(resultSet));
                    }

                    return result;
                }
            }
        }
    }

    @Override
    public Book readBook(ResultSet resultSet) throws SQLException {
        var book = new Book();

        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));

        return book;
    }
}
