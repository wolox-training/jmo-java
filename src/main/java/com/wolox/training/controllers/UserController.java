package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.exceptions.UserNotFoundException;
import com.wolox.training.exceptions.UserHasNotTheBookException;
import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
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
@RequestMapping(UserController.USERS)
public class UserController {

    public static final String USERS = "users";
    public static final String ADD_BOOK = "addBook/{idUser}/{idBook}";
    public static final String REMOVE_BOOK = "removeBook/{idUser}/{idBook}";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userRepository.save(user);
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException("User already exists.");
        }
    }

    @GetMapping
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        if (userRepository.findById(user.getIdUser()).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.save(user);
    }

    @DeleteMapping(value = "/{idUser}")
    public void deleteUser(@PathVariable Long id) {
        if (userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @PutMapping (value = ADD_BOOK)
    public User addBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook).orElseThrow(() -> new BookNotFoundException("Book not found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.addBook(book);

        return userRepository.save(user);
    }

    @PutMapping (value = REMOVE_BOOK)
    public User removeBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook).orElseThrow(() -> new BookNotFoundException("Book not found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!user.getBooks().contains(book)) {
            throw new UserHasNotTheBookException("User has not the book");
        }

        user.removeBook(book);

        return userRepository.save(user);
    }
}
