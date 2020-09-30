package com.wolox.training.controllers;

import com.wolox.training.constants.Message;
import com.wolox.training.constants.Route;
import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.exceptions.UserHasNotTheBookException;
import com.wolox.training.exceptions.UserNotFoundException;
import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping(Route.USERS)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @ApiOperation(value = "Save a User", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully saved user."),
        @ApiResponse(code = 400, message = "User already exist.")
    })
    public User createUser(@Valid @RequestBody User user) {
        try {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
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

    @GetMapping(path = "/username")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping(path = Route.USER_PARAMS)
    public ResponseEntity<List<User>> findByParameters(@Valid @PathVariable String name,
        @Valid @PathVariable String startDate,
        @Valid @PathVariable String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<User> users = userRepository.findByNameContainingIgnoreCaseAndBirthdateBetween(name, start, end);
        return ResponseEntity.ok(users);
    }
}
