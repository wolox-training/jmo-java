package com.wolox.training.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wolox.training.constants.Message;
import com.wolox.training.exceptions.BookAlreadyOwnedException;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity(name = "Usser")
@ApiModel(description = "user from training java")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUser;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> books;

    public User() {
    }

    public void setUsername(String username) {
        checkNotNull(username, "Username is required");
        this.username = username;
    }

    public void setName(String name) {
        checkNotNull(name, "Name is required");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        checkNotNull(birthdate, "Birthdate is required");
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        checkNotNull(books, "Books are not should nulls");
        this.books = books;
    }

    public void setPassword(String password) {
        checkNotNull(password, "Password is not should nulls");
        checkArgument(!password.equals(""), "Password is not should empty");
        this.password = password;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public String toString() {
        return "User{" +
            "idUser=" + idUser +
            ", username='" + username + '\'' +
            ", name='" + name + '\'' +
            ", birthdate=" + birthdate +
            ", books=" + books +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) &&
            username.equals(user.username) &&
            name.equals(user.name) &&
            birthdate.equals(user.birthdate) &&
            Objects.equals(books, user.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, name, birthdate, books);
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, Message.BOOK_CAN_NOT_NULL);
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException(Message.BOOK_IS_ALREADY_ASSOCIATED);
        }
        books.add(book);
    }

    public void removeBook(Book book) {
        Objects.requireNonNull(book, Message.BOOK_CAN_NOT_NULL);
        books.removeIf(b -> b.equals(book));
    }
}
