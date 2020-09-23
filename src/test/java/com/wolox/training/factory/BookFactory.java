package com.wolox.training.factory;

import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import java.util.Arrays;
import java.util.List;

public class BookFactory {

    public static Book withDefaultData() {
        Book book = new Book();
        book.setGenre("Fantasy");
        book.setAuthor("J. K. Rowlingn");
        book.setImage("image.png");
        book.setTitle("Harry Potter");
        book.setSubtitle("-");
        book.setPublisher("Bloomsbury");
        book.setYear("1997");
        book.setPages(223);
        book.setIsbn("6453723453");
        return book;
    }

    public static Book withNullAuthor() {
        return new Book(
            "Fantasy",
            null,
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            223,
            "6453723453");
    }

    public static Book withNullImage() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            null,
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            223,
            "6453723453");
    }

    public static Book withNullTitle() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            null,
            "-",
            "Bloomsbury",
            "1997",
            223,
            "6453723453");
    }

    public static Book withNullSubtitle() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            null,
            "Bloomsbury",
            "1997",
            223,
            "6453723453");
    }

    public static Book withNullPublisher() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            null,
            "1997",
            223,
            "6453723453");
    }

    public static Book withNullYear() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            null,
            223,
            "6453723453");
    }

    public static Book withNullPages() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            null,
            "6453723453");
    }

    public static Book withNullIsbn() {
        return new Book(
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            223,
            null);
    }

    public static Book withId(Long idUser) {
        return new Book(
            idUser,
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            223,
            "6453723453");
    }

    public static List<Book> bookList() {
        Book book1 = new Book(
            1L,
            "Fantasy",
            "J. K. Rowlingn",
            "image.png",
            "Harry Potter",
            "-",
            "Bloomsbury",
            "1997",
            223,
            "6453723453");

        Book book2 = new Book(
            2L,
            "Adventure",
            "J. R. R. Tolkien",
            "image2.png",
            "The Lord of the rings",
            "-",
            "Allen & Unwin",
            "1954",
            152,
            "148758");

        Book book3 = new Book(
            3L,
            "Children's novel",
            "Rob Kidd",
            "image3.png",
            "Pirates of the Caribbean: Jack Sparrow",
            "-",
            "Disney Press",
            "2006",
            236,
            "7346345");

        return Arrays.asList(book1, book2, book3);
    }


}
