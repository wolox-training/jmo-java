package com.wolox.training.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public final class Book {

    private @Id
    Long idBook;
    private @Column
    String genre;
    private @Column(nullable = false)
    String author;
    private @Column(nullable = false)
    String image;
    private @Column(nullable = false)
    String title;
    private @Column(nullable = false)
    String subtitle;
    private @Column(nullable = false)
    String publisher;
    private @Column(nullable = false)
    String year;
    private @Column(nullable = false)
    Integer pages;
    private @Column(nullable = false)
    String isbn;
    private @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    Book() {
    }

    private Book(Builder builder) {
        this.idBook = builder.idBook;
        this.genre = builder.genre;
        this.author = builder.author;
        this.image = builder.image;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.publisher = builder.publisher;
        this.year = builder.year;
        this.pages = builder.pages;
        this.isbn = builder.isbn;
        this.user = builder.user;
    }

    public static Builder from() {
        return new Builder();
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

    public User getUser() {
        return user;
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
            genre.equals(book.genre) &&
            author.equals(book.author) &&
            image.equals(book.image) &&
            title.equals(book.title) &&
            subtitle.equals(book.subtitle) &&
            publisher.equals(book.publisher) &&
            year.equals(book.year) &&
            pages.equals(book.pages) &&
            isbn.equals(book.isbn) &&
            Objects.equals(user, book.user);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(idBook, genre, author, image, title, subtitle, publisher, year, pages, isbn,
                user);
    }

    public static class Builder {

        private Long idBook;
        private String genre;
        private String author;
        private String image;
        private String title;
        private String subtitle;
        private String publisher;
        private String year;
        private Integer pages;
        private String isbn;
        private User user;

        Builder() {
        }

        public Builder withIdBook(Long idBook) {
            this.idBook = idBook;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withImage(String image) {
            this.image = image;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder withPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder withYear(String year) {
            this.year = year;
            return this;
        }

        public Builder withPages(Integer pages) {
            this.pages = pages;
            return this;
        }

        public Builder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Book build() {
            requireNonNull(author, "Author is required");
            requireNonNull(image, "Image is required");
            requireNonNull(title, "Title is required");
            requireNonNull(subtitle, "Subtitle is required");
            requireNonNull(publisher, "Publisher is required");
            requireNonNull(year, "Year is required");
            requireNonNull(pages, "Pages is required");
            requireNonNull(isbn, "Isbn is required");
            requireNonNull(user, "User is required");
            return new Book(this);
        }
    }
}
