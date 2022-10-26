package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {
    private final BookDao dao = new BookDao(InMemoryDatabase.createTestDataSource());

    @Test
    void shouldRetrieveBook() throws SQLException {
        var book = SampleData.sampleBook();

        book.setTitle("Hello");
        book.setAuthor("Mika");

        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .isEqualTo(book)
            .isNotSameAs(book);
    }

    @Test
    void shouldRetrieveNullForMissingBook() throws SQLException {
        assertThat(dao.retrieve(-1)).isNull();
    }

    @Test
    public void shouldFindBooksByAuthorName() throws SQLException {
        var book = SampleData.sampleBook();
        var bookWithSameAuthor = SampleData.sampleBook();

        book.setAuthor(bookWithSameAuthor.getAuthor());

        var bookWithOtherAuthor = SampleData.sampleBook();

        bookWithOtherAuthor.setAuthor("Other Author");

        dao.save(book);
        dao.save(bookWithSameAuthor);
        dao.save(bookWithOtherAuthor);

        assertThat(dao.findByAuthorName(book.getAuthor()))
            .extracting(Book::getId)
            .contains(book.getId(), bookWithSameAuthor.getId())
            .doesNotContain((bookWithOtherAuthor.getId()));
    }
}
