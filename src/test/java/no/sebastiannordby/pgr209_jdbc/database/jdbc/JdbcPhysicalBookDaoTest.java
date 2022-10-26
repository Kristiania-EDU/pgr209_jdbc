package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.*;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcBookDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcLibraryDao;
import no.sebastiannordby.pgr209_jdbc.database.jdbc.JdbcPhysicalBookDao;

import javax.sql.DataSource;

public class JdbcPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {
    private final DataSource testDataSource = InMemoryDatabase.createTestDataSource();
    private final JdbcBookDao bookDao = new JdbcBookDao(testDataSource);
    private final JdbcLibraryDao libraryDao = new JdbcLibraryDao(testDataSource);
    private final JdbcPhysicalBookDao dao = new JdbcPhysicalBookDao(testDataSource, bookDao);

    @Override
    protected PhysicalBookDao getPhysicalBookDao() {
        return dao;
    }

    @Override
    protected BookDao getBookDao() {
        return bookDao;
    }

    @Override
    protected LibraryDao getLibraryDao() {
        return libraryDao;
    }
}
