package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcBookDao;
import no.sebastiannordby.pgr209_jdbc.models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractBookDaoTest {

    private BookDao dao;

    protected abstract BookDao getDao();

    @BeforeEach
    void setup() {
        dao = getDao();
    }

    @Test
    void shouldRetrieveBook() throws Exception {
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
    void shouldRetrieveNullForMissingBook() throws Exception {
        assertThat(dao.retrieve(-1)).isNull();
    }

    @Test
    public void shouldFindBooksByAuthorName() throws Exception {
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
