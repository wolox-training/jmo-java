package com.wolox.training.model;

import static java.util.Objects.requireNonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    Book(){}

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

        Builder() {}

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

        public Book build() {
            requireNonNull(author, "Author is required");
            requireNonNull(image, "Image is required");
            requireNonNull(title, "Title is required");
            requireNonNull(subtitle, "Subtitle is required");
            requireNonNull(publisher, "Publisher is required");
            requireNonNull(year, "Year is required");
            requireNonNull(pages, "Pages is required");
            requireNonNull(isbn, "Isbn is required");
            return new Book(this);
        }
    }
}
