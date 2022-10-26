package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcBookDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcLibraryDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcPhysicalBookDao;
import no.sebastiannordby.pgr209_jdbc.models.Book;
import no.sebastiannordby.pgr209_jdbc.models.PhysicalBook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPhysicalBookDaoTest {

    private PhysicalBookDao dao;
    private BookDao bookDao;
    private LibraryDao libraryDao;

    protected abstract PhysicalBookDao getPhysicalBookDao();
    protected abstract BookDao getBookDao();
    protected abstract LibraryDao getLibraryDao();

    @BeforeEach
    void setup() {
        dao = getPhysicalBookDao();
        libraryDao = getLibraryDao();
        bookDao = getBookDao();
    }

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

        Assertions.assertThat(dao.findByLibrary(firstLibrary.getId()))
            .extracting(Book::getId)
            .contains(firstBook.getId(), secondBook.getId());

        Assertions.assertThat(dao.findByLibrary(secondLibrary.getId()))
            .extracting(Book::getId)
            .contains(firstBook.getId())
            .doesNotContain(secondBook.getId());
    }
}
