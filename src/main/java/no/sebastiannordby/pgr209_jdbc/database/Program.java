package no.sebastiannordby.pgr209_jdbc.database;

import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Program {
    private BookDao bookDao;
    private LibraryDao libraryDao;

    public Program(DataSource dataSource) {
        this.bookDao = new BookDao(dataSource);
        this.libraryDao = new LibraryDao(dataSource);
    }

    public void run() throws SQLException {
        bookDao.save(SampleData.sampleBook());
        libraryDao.save(SampleData.sampleLibrary());
    }

    public static void main(String[] args) throws SQLException {
        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/library");
        dataSource.setUser("library_app");
        dataSource.setPassword("secretPassword");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        Program program = new Program(dataSource);

        program.run();
    }
}
