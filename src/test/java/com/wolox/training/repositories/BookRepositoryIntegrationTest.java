package com.wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wolox.training.factory.BookFactory;
import com.wolox.training.model.Book;
import java.util.stream.Stream;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenCreateBook_thenBookIsPersisted() {
        Book book = BookFactory.withDefaultData();
        bookRepository.save(book);

        Book persistedBook = bookRepository.findByAuthor(book.getAuthor()).orElse(null);

        assert persistedBook != null;
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

    @TestFactory
    @DisplayName("Should fail when sending null to required values")
    Stream<DynamicTest> should_be_rejected() {
        return Stream.of(
            BookFactory.withNullAuthor(),
            BookFactory.withNullImage(),
            BookFactory.withNullIsbn(),
            BookFactory.withNullPages(),
            BookFactory.withNullPublisher(),
            BookFactory.withNullSubtitle(),
            BookFactory.withNullTitle(),
            BookFactory.withNullYear()
        )
            .map(input -> DynamicTest.dynamicTest("Should failed ",
                () -> assertThrows(ConstraintViolationException.class, () -> bookRepository.save(input))
            ));
    }
}