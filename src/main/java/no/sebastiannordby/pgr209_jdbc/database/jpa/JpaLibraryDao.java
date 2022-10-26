package no.sebastiannordby.pgr209_jdbc.database.jpa;

import no.sebastiannordby.pgr209_jdbc.database.LibraryDao;
import no.sebastiannordby.pgr209_jdbc.models.Library;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JpaLibraryDao implements LibraryDao {
    @Override
    public void save(Library library) throws SQLException {

    }

    @Override
    public Library retrieve(long id) throws SQLException {
        return null;
    }

    @Override
    public Library readLibrary(ResultSet resultSet) throws SQLException {
        return null;
    }
}
