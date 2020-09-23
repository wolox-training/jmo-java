package com.wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.wolox.training.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private final Book book = new Book();

    @BeforeEach
    void before() {
        book.setGenre("Fantasy");
        book.setAuthor("J. K. Rowlingn");
        book.setImage("image.png");
        book.setTitle("Harry Potter");
        book.setSubtitle("-");
        book.setPublisher("Bloomsbury");
        book.setYear("1997");
        book.setPages(223);
        book.setIsbn("6453723453");
    }

    @Test
    void whenCreateBook_thenBookIsPersisted() {
        entityManager.persist(book);

        Book persistedBook = bookRepository.findByAuthor(book.getAuthor()).orElse(null);

        assertNotNull(persistedBook.getIdBook());
        assertEquals(book.getGenre(), persistedBook.getGenre());
        assertEquals(book.getAuthor(), persistedBook.getAuthor());
        assertEquals(book.getImage(), persistedBook.getImage());
        assertEquals(book.getTitle(), persistedBook.getTitle());
        assertEquals(book.getSubtitle(), persistedBook.getSubtitle());
        assertEquals(book.getPublisher(), persistedBook.getPublisher());
        assertEquals(book.getYear(), persistedBook.getYear());
        assertEquals(book.getPages(), persistedBook.getPages());
        assertEquals(book.getIsbn(), persistedBook.getIsbn());
    }
}