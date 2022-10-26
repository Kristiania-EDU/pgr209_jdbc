package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.AbstractBookDaoTest;
import no.sebastiannordby.pgr209_jdbc.database.BookDao;
import no.sebastiannordby.pgr209_jdbc.database.InMemoryDatabase;

public class JdbcBookDaoTest extends AbstractBookDaoTest {
    @Override
    protected BookDao getDao() {
        return new JdbcBookDao(InMemoryDatabase.createTestDataSource());
    }
}
