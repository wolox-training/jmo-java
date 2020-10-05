package com.wolox.training.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
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
    @ManyToMany(mappedBy = "books")
    private List<User> users;

    public Book() {
    }

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
        checkNotNull(users , "User is required");
        this.users = users;
    }

    public Long getIdBook() {
        return idBook;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getYear() {
        return year;
    }

    public Integer getPages() {
        return pages;
    }

    public String getIsbn() {
        return isbn;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Book{" +
            "idBook=" + idBook +
            ", genre='" + genre + '\'' +
            ", author='" + author + '\'' +
            ", image='" + image + '\'' +
            ", title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", publisher='" + publisher + '\'' +
            ", year='" + year + '\'' +
            ", pages=" + pages +
            ", isbn='" + isbn + '\'' +
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
        Book book = (Book) o;
        return Objects.equals(idBook, book.idBook) &&
            Objects.equals(genre, book.genre) &&
            author.equals(book.author) &&
            image.equals(book.image) &&
            title.equals(book.title) &&
            subtitle.equals(book.subtitle) &&
            publisher.equals(book.publisher) &&
            year.equals(book.year) &&
            pages.equals(book.pages) &&
            isbn.equals(book.isbn) &&
            Objects.equals(users, book.users);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(idBook, genre, author, image, title, subtitle, publisher, year, pages, isbn,
                users);
    }
}
