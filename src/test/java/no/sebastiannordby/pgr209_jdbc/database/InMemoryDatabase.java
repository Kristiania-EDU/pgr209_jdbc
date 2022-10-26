package no.sebastiannordby.pgr209_jdbc.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class InMemoryDatabase {
    public static JdbcDataSource createTestDataSource() {
        var dataSource = new JdbcDataSource();

        dataSource.setURL("jdbc:h2:mem:testDatabase;DB_CLOSE_DELAY=-1");

        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }
}
