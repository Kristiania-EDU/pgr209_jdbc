package no.sebastiannordby.pgr209_jdbc.database;

import org.junit.jupiter.api.Test;

public class BookDaoTest {
    @Test
    void shouldRetrieveBook() {
        var book = sampleBook();

        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
            .usingRecusiveComparison()
            .isEqualTo(book);
    }
}
