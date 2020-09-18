package com.wolox.training.model;

import com.wolox.training.exceptions.BookAlreadyOwnedException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Usser")
public final class User {

    private @Id
    Long idUser;
    private @Column(nullable = false)
    String username;
    private @Column(nullable = false)
    String name;
    private @Column(nullable = false)
    LocalDate birthdate;
    private @OneToMany(mappedBy = "user")
    List<Book> books;

    User() {
    }

    private User(Builder builder) {
        this.idUser = builder.idUser;
        this.username = builder.username;
        this.name = builder.name;
        this.birthdate = builder.birthdate;
        this.books = builder.books;
    }

    public static Builder from() {
        return new Builder();
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

    public static class Builder {

        private Long idUser;
        private String username;
        private String name;
        private LocalDate birthdate;
        private List<Book> books;

        Builder() {
        }

        public Builder withIdUser(Long idUser) {
            this.idUser = idUser;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBirthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public User build() {
            Objects.requireNonNull(username, "Username is required");
            Objects.requireNonNull(name, "Name is required");
            Objects.requireNonNull(birthdate, "Birthdate is required");
            Objects.requireNonNull(books, "Books is required");
            return new User(this);
        }
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
