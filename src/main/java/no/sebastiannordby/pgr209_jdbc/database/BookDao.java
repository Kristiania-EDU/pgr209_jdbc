package no.sebastiannordby.pgr209_jdbc.database;

import java.util.HashMap;
import java.util.Map;

public class BookDao {
    private Map<Long, Book> allBooks = new HashMap<>();

    public void save(Book book) {
        book.setId(allBooks.size());
        allBooks.put(book.getId(), book);
    }

    public Book retrieve(long id) {
        return allBooks.get(id);
    }
}
