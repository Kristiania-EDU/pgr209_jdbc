package no.sebastiannordby.pgr209_jdbc.database.jpa;

import no.sebastiannordby.pgr209_jdbc.database.AbstractLibraryDaoTest;
import no.sebastiannordby.pgr209_jdbc.database.LibraryDao;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {
    @Override
    protected LibraryDao getDao() {
        return new JpaLibraryDao();
    }
}
