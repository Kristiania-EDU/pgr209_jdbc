package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcLibraryDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LibraryDaoTest {
    private final JdbcLibraryDao dao = new JdbcLibraryDao(InMemoryDatabase.createTestDataSource());

    @Test
    void shouldRetrieveSavedLibrary() throws SQLException {
        var library = SampleData.sampleLibrary();

        dao.save(library);
        assertThat(dao.retrieve(library.getId()))
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .isEqualTo(library);
    }
}
