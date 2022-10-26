package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcBookDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcLibraryDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcPhysicalBookDao;
import no.sebastiannordby.pgr209_jdbc.models.Book;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalBookDaoTest {
    private final DataSource testDataSource = InMemoryDatabase.createTestDataSource();
    private final JdbcBookDao bookDao = new JdbcBookDao(testDataSource);
    private final JdbcLibraryDao libraryDao = new JdbcLibraryDao(testDataSource);
    private final JdbcPhysicalBookDao dao = new JdbcPhysicalBookDao(testDataSource, bookDao);

    @Test
    void shouldListBooksByLibrary() throws SQLException {
        var firstBook = SampleData.sampleBook();
        var secondBook = SampleData.sampleBook();
        bookDao.save(firstBook);
        bookDao.save(secondBook);

        var firstLibrary = SampleData.sampleLibrary();
        var secondLibrary = SampleData.sampleLibrary();
        libraryDao.save(firstLibrary);
        libraryDao.save(secondLibrary);

        dao.insert(firstLibrary, firstBook);
        dao.insert(firstLibrary, secondBook);
        dao.insert(secondLibrary, firstBook);

        assertThat(dao.findByLibrary(firstLibrary.getId()))
            .extracting(Book::getId)
            .contains(firstBook.getId(), secondBook.getId());

        assertThat(dao.findByLibrary(secondLibrary.getId()))
            .extracting(Book::getId)
            .contains(firstBook.getId())
            .doesNotContain(secondBook.getId());
    }
}
