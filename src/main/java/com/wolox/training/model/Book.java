package com.wolox.training.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public final class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idBook;
    private String genre;
    @NotNull
    private String author;
    @NotNull
    private String image;
    @NotNull
    private String title;
    @NotNull
    private String subtitle;
    @NotNull
    private String publisher;
    @NotNull
    private String year;
    @NotNull
    private Integer pages;
    @NotNull
    private String isbn;
    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private List<User> users;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        checkNotNull(author, "Author is required");
        this.author = author;
    }

    public void setImage(String image) {
        checkNotNull(image, "Image is required");
        this.image = image;
    }

    public void setTitle(String title) {
        checkNotNull(title, "Title is required");
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, "Subtitle is required");
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, "Publisher is required");
        this.publisher = publisher;
    }

    public void setYear(String year) {
        checkNotNull(year, "Year is required");
        this.year = year;
    }

    public void setPages(Integer pages) {
        checkNotNull(pages, "Pages is required");
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, "Isbn is required");
        this.isbn = isbn;
    }

    public void setUsers(List<User> users) {
        checkNotNull(users, "User is required");
        this.users = users;
    }
}
