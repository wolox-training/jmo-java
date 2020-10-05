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

    public static List<Book> bookList() {
        Book book1 = withDefaultData();

        Book book2 = new Book();
        book2.setGenre("Adventure");
        book2.setAuthor("J. R. R. Tolkien");
        book2.setImage("image2.png");
        book2.setTitle("The Lord of the rings");
        book2.setSubtitle("-");
        book2.setPublisher("Allen & Unwin");
        book2.setYear("1954");
        book2.setPages(152);
        book2.setIsbn("148758");

        Book book3 = new Book();
        book3.setGenre("Children's novel");
        book3.setAuthor("Rob Kidd");
        book3.setImage("image3.png");
        book3.setTitle("Pirates of the Caribbean: Jack Sparrow");
        book3.setSubtitle("-");
        book3.setPublisher("Disney Press");
        book3.setYear("2006");
        book3.setPages(236);
        book3.setIsbn("7346345");

        return Arrays.asList(book1, book2, book3);
    }


}
