package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.exceptions.UserNotFoundException;
import com.wolox.training.exceptions.UserHasNotTheBookException;
import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api
public class UserController {

    public static final String USERS = "/api/users";
    public static final String ADD_BOOK = "/addBook/{idUser}/{idBook}";
    public static final String REMOVE_BOOK = "/removeBook/{idUser}/{idBook}";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    @ApiOperation(value = "Save a User", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully saved user."),
        @ApiResponse(code = 400, message = "User already exist.")
    })
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
    public User updateUser(@RequestBody User user) {
        if (!userRepository.findById(user.getIdUser()).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return userRepository.save(user);
    }

    @DeleteMapping(value = "/{idUser}")
    public void deleteUser(@PathVariable Long idUser) {
        if (!userRepository.findById(idUser).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(idUser);
    }

    @PutMapping (value = ADD_BOOK)
    public void addBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook).orElseThrow(() -> new BookNotFoundException("Book not found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.addBook(book);

        userRepository.save(user);
    }

    @PutMapping (value = REMOVE_BOOK)
    public void removeBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        Book book = bookRepository.findById(idBook).orElseThrow(() -> new BookNotFoundException("Book not found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!user.getBooks().contains(book)) {
            throw new UserHasNotTheBookException("User has not the book");
        }

        user.removeBook(book);

        userRepository.save(user);
    }

    @GetMapping(path = "/{idUser}")
    public User findUser(@PathVariable Long idUser) {
        return userRepository.findById(idUser)
            .orElseThrow(() -> new BookNotFoundException("User not found"));
    }
}