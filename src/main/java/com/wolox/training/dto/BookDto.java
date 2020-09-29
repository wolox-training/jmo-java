package com.wolox.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class BookDto {

    private String title;
    private String subtitle;
    private JsonNode publishers;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("number_of_pages")
    private Integer pages;
    @JsonProperty("isbn_10")
    private JsonNode isbn;
    private JsonNode authors;

    public BookDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public JsonNode getPublishers() {
        return publishers;
    }

    public void setPublishers(JsonNode publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public JsonNode getIsbn() {
        return isbn;
    }

    public void setIsbn(JsonNode isbn) {
        this.isbn = isbn;
    }

    public JsonNode getAuthors() {
        return authors;
    }

    public void setAuthors(JsonNode authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookDto{" +
            "title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", publisher='" + publishers + '\'' +
            ", publishDate='" + publishDate + '\'' +
            ", pages=" + pages +
            ", isbn='" + isbn + '\'' +
            ", athors=" + authors +
            '}';
    }
}
