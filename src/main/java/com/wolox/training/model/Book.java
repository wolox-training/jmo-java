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

    public Book(Long idBook, String genre, String author, String image, String title,
        String subtitle, String publisher, String year, Integer pages, String isbn) {
        this.idBook = idBook;
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
}
