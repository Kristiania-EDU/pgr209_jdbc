package no.sebastiannordby.pgr209_jdbc.models;

public class PhysicalBook {
    private long id;
    private long libraryId;
    private long bookId;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setLibraryId(long libraryId) {
        this.libraryId = libraryId;
    }

    public long getLibraryId() {
        return libraryId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }
}
