package com.wolox.training.controllers;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.wolox.training.constants.Message;
import com.wolox.training.constants.Route;
import com.wolox.training.dto.BookDto;
import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.mapper.BookMapper;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Route.OPEN_LIBRARY)
public class OpenLibraryService {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(path = Route.ISBN)
    public ResponseEntity<BookDto> bookInfo(@Valid @PathVariable String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book.map(value -> ResponseEntity.ok(BookMapper.modelToDto(value)))
            .orElseGet(() -> newPersistedBook(isbn));
    }

    private ResponseEntity<BookDto> newPersistedBook(final String isbn) {
        final BookDto externalBook = externalBookByIsbn(isbn);
        final Book mappedBook = BookMapper.dtoToModel(externalBook);
        final Book persistedBook = bookRepository.save(mappedBook);
        final BookDto bookDto = BookMapper.modelToDto(persistedBook);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    protected BookDto externalBookByIsbn(String isbn) {
        Map<String, String> urlParams =
            new ImmutableMap.Builder<String, String>().put("isbn", isbn).build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Route.URL_EXTERNAL_API);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BookDto> response;

        try {
            response = restTemplate
                .exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET,
                    null, BookDto.class);
        } catch (Exception e) {
            throw new BookNotFoundException(Message.BOOK_NOT_FOUND);
        }

        return Preconditions
            .checkNotNull(response.getBody());
    }
}
