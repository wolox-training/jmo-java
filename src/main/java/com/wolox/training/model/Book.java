package com.wolox.training.model;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @NoArgsConstructor
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
            checkArgument(author == null, "Author is required");
            checkArgument(image == null, "Image is required");
            checkArgument(title == null, "Title is required");
            checkArgument(subtitle == null, "Subtitle is required");
            checkArgument(publisher == null, "Publisher is required");
            checkArgument(year == null, "Year is required");
            checkArgument(pages == null, "Pages is required");
            checkArgument(isbn == null, "Isbn is required");
            return new Book(this);
        }
    }
}
