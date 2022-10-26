package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.AbstractLibraryDaoTest;
import no.sebastiannordby.pgr209_jdbc.database.InMemoryDatabase;
import no.sebastiannordby.pgr209_jdbc.database.LibraryDao;

public class JdbcLibraryDaoTest extends AbstractLibraryDaoTest {
    @Override
    protected LibraryDao getDao() {
        return new JdbcLibraryDao(InMemoryDatabase.createTestDataSource());
    }
}
