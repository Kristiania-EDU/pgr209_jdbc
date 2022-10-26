import no.sebastiannordby.pgr209_jdbc.data.SampleData;
import no.sebastiannordby.pgr209_jdbc.database.BookDao;
import no.sebastiannordby.pgr209_jdbc.database.LibraryDao;
import no.sebastiannordby.pgr209_jdbc.database.PhysicalBookDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Program {
    private final BookDao bookDao;
    private final LibraryDao libraryDao;
    private final PhysicalBookDao physicalBookDao;

    public Program(DataSource dataSource) {
        this.bookDao = new BookDao(dataSource);
        this.libraryDao = new LibraryDao(dataSource);
        this.physicalBookDao = new PhysicalBookDao(dataSource, bookDao);
    }

    public void run() throws SQLException {
        var book = SampleData.sampleBook();
        var library = SampleData.sampleLibrary();

        bookDao.save(book);
        libraryDao.save(library);
        physicalBookDao.insert(library, book);
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
