package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import org.flywaydb.core.Flyway;
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

        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        try(var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate("create table if not exists books (id serial primary key, title varchar(100))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dao = new BookDao(dataSource);
    }

    @Test
    void shouldRetrieveBook() throws SQLException {
        var book = SampleData.sampleBook();

        book.setTitle("Hello");

        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
            .usingRecursiveComparison()
            .isEqualTo(book)
            .isNotSameAs(book);
    }

    @Test
    void shouldRetrieveNullForMissingBook() throws SQLException {
        assertThat(dao.retrieve(-1)).isNull();
    }
}
