package no.sebastiannordby.pgr209_jdbc.data;

import no.sebastiannordby.pgr209_jdbc.database.Book;

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
}
