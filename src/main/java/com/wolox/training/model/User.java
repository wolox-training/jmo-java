package com.wolox.training.model;

import com.sun.istack.NotNull;
import com.wolox.training.exceptions.BookAlreadyOwnedException;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Usser")
@ApiModel(description = "user from training java")
public final class User {

    private @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long idUser;
    private @NotNull
    String username;
    private @NotNull
    String name;
    private @NotNull
    LocalDate birthdate;
    private @OneToMany(mappedBy = "user")
    List<Book> books;

    User() {
    }

    public User(Long idUser, String username, String name, LocalDate birthdate,
        List<Book> books) {
        this.idUser = idUser;
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = books;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
            books.equals(user.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, name, birthdate, books);
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Book can not be null");
        if(books.contains(book)) {
            throw new BookAlreadyOwnedException("Book is already associated");
        }
        books.add(book);
    }

    public void removeBook(Book book) {
        Objects.requireNonNull(book, "Book can not be null");
        books.removeIf(b -> b.equals(book));
    }
}
