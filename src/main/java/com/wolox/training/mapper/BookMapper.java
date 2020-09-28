package com.wolox.training.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolox.training.constants.Message;
import com.wolox.training.dto.BookDto;
import com.wolox.training.model.Book;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jmx.access.InvalidInvocationException;

public final class BookMapper {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private BookMapper() {
        throw new InvalidInvocationException(Message.INVALID_INVOCATION);
    }

    public static Book dtoToModel(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setSubtitle(dto.getSubtitle());
        book.setPublisher(dto.getPublishers().get(0).textValue());
        book.setPages(dto.getPages());
        book.setIsbn(dto.getIsbn().get(0).textValue());
        book.setYear(dto.getPublishDate());
        book.setAuthor(dto.getAuthors().get(0).get("key").textValue());
        return book;
    }

    public static List<Book> dtoToModel(List<BookDto> dtos) {
        return dtos.stream()
            .map(BookMapper::dtoToModel)
            .collect(Collectors.toList());
    }

    public static BookDto modelToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setSubtitle(book.getSubtitle());
        bookDto.setPublishers(objectToJsonNode(book.getPublisher()));
        bookDto.setPages(book.getPages());
        bookDto.setIsbn(objectToJsonNode(book.getIsbn()));
        bookDto.setPublishDate(book.getYear());
        bookDto.setAuthors(objectToJsonNode(book.getAuthor()));
        return bookDto;
    }

    public static List<BookDto> modelToDto(List<Book> books) {
        return books.stream()
            .map(BookMapper::modelToDto)
            .collect(Collectors.toList());
    }

    public static JsonNode objectToJsonNode(Object object) {
        return objectMapper.convertValue(object, JsonNode.class);
    }

}
