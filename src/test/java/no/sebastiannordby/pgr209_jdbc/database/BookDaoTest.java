package no.sebastiannordby.pgr209_jdbc.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {
    private BookDao dao = new BookDao();

    @Test
    void shouldRetrieveBook() throws SQLException {
        var book = sampleBook();

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
