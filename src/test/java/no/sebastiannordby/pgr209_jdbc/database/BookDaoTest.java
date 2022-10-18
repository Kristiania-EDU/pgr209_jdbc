package no.sebastiannordby.pgr209_jdbc.database;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {
    private BookDao dao;

    @BeforeEach
    void setup() {
        var dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testDatabase;DB_CLOSE_DELAY=-1");

        try(var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate("create table books (id serial primary key, title varchar(100))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dao = new BookDao(dataSource);
    }

    @Test
    void shouldRetrieveBook() throws SQLException {
        var book = sampleBook();

        book.setTitle("Hello");

        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
            .usingRecursiveComparison()
            .isEqualTo(book)
            .isNotSameAs(book);
    }

    private Book sampleBook() {
        return new Book();
    }
}
