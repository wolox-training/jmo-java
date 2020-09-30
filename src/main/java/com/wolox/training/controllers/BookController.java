package com.wolox.training.controllers;

import com.wolox.training.constants.Message;
import com.wolox.training.constants.Route;
import com.wolox.training.dto.BookDto;
import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.mapper.BookMapper;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.services.OpenLibraryService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.BOOKS)
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @PostMapping
    public Book createBook(@Valid @RequestBody Book book) {
        try {
            return bookRepository.save(book);
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(Message.BOOK_ALREADY_EXIST);
        }
    }

    @GetMapping
    public Iterable<Book> listBooks() {
        return bookRepository.findAll();
    }

    @PutMapping
    public void updateBook(@Valid @RequestBody Book book) {
        if (!bookRepository.findById(book.getIdBook()).isPresent()) {
            throw new BookNotFoundException(Message.BOOK_NOT_FOUND);
        }
        bookRepository.save(book);
    }

    @DeleteMapping(path = Route.ID_BOOK)
    public void deleteBook(@PathVariable Long idBook) {
        if (!bookRepository.findById(idBook).isPresent()) {
            throw new BookNotFoundException(Message.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(idBook);
    }

    @GetMapping(path = Route.ID_BOOK)
    public Book findBook(@PathVariable Long idBook) {
        return bookRepository.findById(idBook)
            .orElseThrow(() -> new BookNotFoundException(Message.BOOK_NOT_FOUND));
    }

    @GetMapping(path = Route.ISBN)
    public ResponseEntity<BookDto> findByIsbn(@Valid @PathVariable String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book.map(value -> ResponseEntity.ok(BookMapper.modelToDto(value)))
            .orElseGet(() -> openLibraryService.bookInfo(isbn));
    }

    @GetMapping(path = Route.BOOK_PARAMS)
    public ResponseEntity<List<Book>> findByParameters(@PathVariable String publisher,
        @PathVariable String genre, @PathVariable String year) {
        List<Book> books = bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);
        return ResponseEntity.ok(books);
    }

}
