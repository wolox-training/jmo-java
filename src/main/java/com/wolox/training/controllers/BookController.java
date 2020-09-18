package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BookController.BOOKS)
public class BookController {

    public static final String BOOKS = "/books";

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        try {
            return bookRepository.save(book);
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException("Book already exists.");
        }
    }

    @GetMapping
    public Iterable<Book> listBooks() {
        return bookRepository.findAll();
    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        if(bookRepository.findById(book.getIdBook()).isPresent()) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.save(book);
    }

    @DeleteMapping(value = "/{idBook}")
    public void deleteBook(@PathVariable Long id) {
        if(bookRepository.findById(id).isPresent()) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

}
