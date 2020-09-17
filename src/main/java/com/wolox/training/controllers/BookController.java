package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.exceptions.PrivilegeManagementConstraintException;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
@RequestMapping(BookController.BOOKS)
public class BookController {

    public static final String BOOKS = "/BookController";
    public static final String CREATE = "/create";
    public static final String READ = "/books";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete/{idBoom}";

    @Autowired
    private BookRepository bookRepository;

    @PostMapping(value = CREATE)
    public Book createBook(@RequestBody Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new PrivilegeManagementConstraintException("Book already exists.");
        }
    }

    @GetMapping(value = READ)
    public List<Book> listBooks() {
        return bookRepository.findAllBooks();
    }

    @PutMapping(value = UPDATE)
    public void updateBook(@RequestBody Book book) {
        bookRepository.findById(book.getIdBook())
            .orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.save(book);
    }

    @DeleteMapping(value = DELETE)
    public void deleteBook(@PathVariable Long id) {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.deleteById(id);
    }


}
