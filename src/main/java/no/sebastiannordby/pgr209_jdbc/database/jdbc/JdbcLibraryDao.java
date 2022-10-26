package no.sebastiannordby.pgr209_jdbc.database.jdbc;

import no.sebastiannordby.pgr209_jdbc.database.LibraryDao;
import no.sebastiannordby.pgr209_jdbc.models.Library;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcLibraryDao implements LibraryDao {
    private final DataSource dataSource;

    public JdbcLibraryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Library library) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            var query = "INSERT INTO libraries(name, address) VALUES(?, ?)";

            try(var statement =
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, library.getName());
                statement.setString(2, library.getAddress());
                statement.executeUpdate();

                try(var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    library.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    @Override
    public Library retrieve(long id) throws SQLException {
        try(var connection = dataSource.getConnection()) {
            try(var statement =
                connection.prepareStatement("SELECT * FROM libraries where id = ?")){
                statement.setLong(1, id);

                try(var rs = statement.executeQuery()) {
                    return rs.next() ? readLibrary(rs) : null;
                }
            }
        }
    }

    @Override
    public Library readLibrary(ResultSet resultSet) throws SQLException {
        var library = new Library();

        library.setId(resultSet.getLong("id"));
        library.setName(resultSet.getString("name"));
        library.setAddress(resultSet.getString("address"));

        return library;
    }
}
