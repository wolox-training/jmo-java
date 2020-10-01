package com.wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wolox.training.factory.BookFactory;
import com.wolox.training.model.Book;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    private final Pageable pageable = Pageable.unpaged();

    private final Book book = BookFactory.withDefaultData();

    @AfterEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void whenCreateBook_thenBookIsPersisted() {
        bookRepository.save(book);

        Book persistedBook = bookRepository.findByAuthor(book.getAuthor()).orElse(null);

        assert persistedBook != null;
        validateBookAtributtes(persistedBook);
    }

    @Test
    void whenListBooks_thenAllBookAreReturned() {
        List<Book> bookList = BookFactory.bookList();
        List<Book> savedBooks = bookRepository.saveAll(bookList);

        Page<Book> books = bookRepository.findAll(pageable);

        assertTrue(savedBooks.containsAll(books.getContent()));
    }

    @Test
    void whenFindByIdWitchExists_thenBookIsReturned() {
        Book savedBook = bookRepository.save(book);

        Book persistedBook = bookRepository.findById(savedBook.getIdBook()).orElse(null);

        assert persistedBook != null;
        validateBookAtributtes(persistedBook);
    }

    @Test
    void whenUpdateBook_ifExistsThenBookIsUpdated() {
        bookRepository.save(book);
        book.setImage("HappyHarry.png");

        Book persistedBook = bookRepository.save(book);

        assertEquals("HappyHarry.png", persistedBook.getImage());
    }

    @Test
    void whenDeleteBook_ifExistsThenBookIsRemoved() {
        Book saveBook = bookRepository.save(book);
        bookRepository.deleteById(saveBook.getIdBook());

        Book persistedBook = bookRepository.findById(saveBook.getIdBook()).orElse(null);

        assertNull(persistedBook);
    }

    @Test
    void whenFindByIsbnWitchExists_thenBookIsReturned() {
        Book savedBook = bookRepository.save(book);

        Book persistedBook = bookRepository.findByIsbn(savedBook.getIsbn()).orElse(null);

        assert persistedBook != null;
        validateBookAtributtes(persistedBook);
    }

    private void validateBookAtributtes(Book persistedBook) {
        assertNotNull(persistedBook.getIdBook());
        assertEquals("Fantasy", persistedBook.getGenre());
        assertEquals("J. K. Rowlingn", persistedBook.getAuthor());
        assertEquals("image.png", persistedBook.getImage());
        assertEquals("Harry Potter", persistedBook.getTitle());
        assertEquals("-", persistedBook.getSubtitle());
        assertEquals("Bloomsbury", persistedBook.getPublisher());
        assertEquals("1997", persistedBook.getYear());
        assertEquals(Integer.valueOf(223), persistedBook.getPages());
        assertEquals("6453723453", persistedBook.getIsbn());
    }

    @Test
    void whenFindByPublisherAndGenreAndYear_ThenReturnListOfBooks() {
        List<Book> savedBooks = listPersistedBooks();

        Page<Book> books = bookRepository
            .findByPublisherAndGenreAndYear("Walt Disney", "Adventure", "1988", pageable);

        assertEquals(3, books.getContent().size());
        assertTrue(savedBooks.containsAll(books.getContent()));
    }

    @Test
    void whenFindByPublisherAndNullGenreAndNullYear_ThenReturnListOfBooks() {
        List<Book> savedBooks = listPersistedBooks();

        Page<Book> books = bookRepository
            .findByOptionalPublisherAndGenreAndYear("Walt Disney", "Adventure", null, pageable);

        assertEquals(3, books.getContent().size());
        assertTrue(savedBooks.containsAll(books.getContent()));
    }

    @Test
    void whenFindByNullPublisherANullGenreAndNullYear_ThenReturnListOfBooks() {
        List<Book> savedBooks = listPersistedBooks();

        Page<Book> books = bookRepository
            .findByOptionalPublisherAndGenreAndYear("Walt Disney", null, null, pageable);

        assertEquals(3, books.getContent().size());
        assertTrue(savedBooks.containsAll(books.getContent()));
    }

    @Test
    void whenFindByOptionalPublisherAndNullGenreAndNullYear_ThenReturnListOfBooks() {
        List<Book> savedBooks = listPersistedBooks();

        Page<Book> books = bookRepository
            .findByOptionalPublisherAndGenreAndYear("Walt Disney", null, null, pageable);

        assertEquals(3, books.getContent().size());
        assertTrue(savedBooks.containsAll(books.getContent()));
    }

    private List<Book> listPersistedBooks() {
        List<Book> userListWithParams = BookFactory.bookListWithSameParameters();
        List<Book> userList = BookFactory.bookList();

        List<Book> list = Stream.of(userListWithParams, userList)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return bookRepository.saveAll(list);
    }

    @TestFactory
    @DisplayName("Should allow querying with different null parameters")
    Stream<DynamicTest> should_be_allowed() {
        List<Book> bookListWithParams = BookFactory.bookListWithSameParameters();
        bookRepository.saveAll(bookListWithParams);

        return Stream.of(
            bookRepository.findByOptionalPublisherAndGenreAndYear(
                "Walt Disney", null, null, pageable),
            bookRepository.findByOptionalPublisherAndGenreAndYear(
                null, "Adventure", null, pageable),
            bookRepository.findByOptionalPublisherAndGenreAndYear(
                null, null, "1988", pageable),
            bookRepository.findByOptionalPublisherAndGenreAndYear(
                "Walt Disney", null, "1988", pageable),
            bookRepository.findByOptionalPublisherAndGenreAndYear(
                "Walt Disney", "Adventure", "1988", pageable)
        )
            .map(input -> DynamicTest.dynamicTest("Allowed: " + input,
                () -> assertEquals(3, input.getContent().size()))
            );
    }
}