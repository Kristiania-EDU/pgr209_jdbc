package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.models.Library;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbstractLibraryDaoTest {
    private LibraryDao  dao;
    protected abstract LibraryDao getDao();

    @BeforeEach
    void setup() {
        dao = getDao();
    }

    @Test
    void shouldRetrieveSavedLibrary() throws Exception {
        var library = SampleData.sampleLibrary();

        dao.save(library);
        AssertionsForClassTypes.assertThat(dao.retrieve(library.getId()))
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .isEqualTo(library);
    }
}
