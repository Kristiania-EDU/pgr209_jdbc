package no.sebastiannordby.pgr209_jdbc.data;

import no.sebastiannordby.pgr209_jdbc.models.Book;
import no.sebastiannordby.pgr209_jdbc.models.Library;

import java.util.Random;

public class SampleData {
    private static Random random = new Random();

    public static Book sampleBook() {
        var book = new Book();

        book.setTitle(
            pickOne(
                "The Adventures of",
                "Becoming",
                "The start of"
            ) + " " + sampleFullName()
        );

        book.setAuthor(
            sampleFullName()
        );

        return book;
    }

    private static String sampleFullName() {
        return pickOne("Ola", "Emil", "Anders") + " " +
            pickOne("Normann", "Krøllman", "Samuelsen");
    }

    private static String pickOne(String... alternatives) {
        random = new Random();
        return alternatives[random.nextInt(alternatives.length - 1)];
    }

    public static Library sampleLibrary() {
        var library = new Library();

        library.setName(
            pickOne("Oslo", "Lillestrøm", "Bergen", "Sogndal", "Røros")
            + " " +
            pickOne("Public Library", "Deichmanske", "City LIbrary", "Freefree")
        );

        library.setAddress(
            sampleFullName() + " gate " + random.nextInt(100)
        );

        return library;
    }
}
