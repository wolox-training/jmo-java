package com.wolox.training.controllers;

import com.wolox.training.constants.Message;
import com.wolox.training.constants.Route;
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
@RequestMapping(Route.USERS)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userRepository.save(user);
        } catch (ConstraintViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException(Message.USER_ALREADY_EXIST);
        }
    }

    @GetMapping
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!userRepository.findById(user.getIdUser()).isPresent()) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        return userRepository.save(user);
    }

    @DeleteMapping(value = Route.ID_USER)
    public void deleteUser(@PathVariable Long idUser) {
        if (!userRepository.findById(idUser).isPresent()) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        userRepository.deleteById(idUser);
    }

    @PutMapping(value = Route.ADD_BOOK)
    public void addBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
            .orElseThrow(() -> new BookNotFoundException(Message.BOOK_NOT_FOUND));
        User user = userRepository.findById(idUser)
            .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND));

        user.addBook(book);

        userRepository.save(user);
    }

    @PutMapping(value = Route.REMOVE_BOOK)
    public void removeBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook)
            .orElseThrow(() -> new BookNotFoundException(Message.BOOK_NOT_FOUND));
        User user = userRepository.findById(idUser)
            .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND));

        if (!user.getBooks().contains(book)) {
            throw new UserHasNotTheBookException(Message.USER_HAS_NOT_BOOK);
        }

        user.removeBook(book);

        userRepository.save(user);
    }

    @GetMapping(path = Route.ID_USER)
    public User findUser(@PathVariable Long idUser) {
        return userRepository.findById(idUser)
            .orElseThrow(() -> new BookNotFoundException(Message.USER_NOT_FOUND));
    }
}
