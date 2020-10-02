package com.wolox.training.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenLibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${open.library.url}")
    private String openLibraryUrl;

    public ResponseEntity<BookDto> bookInfo(final String isbn) {
        final BookDto externalBook = externalBookByIsbn(isbn);
        final Book mappedBook = BookMapper.dtoToModel(externalBook);
        final Book persistedBook = bookRepository.save(mappedBook);
        final BookDto bookDto = BookMapper.modelToDto(persistedBook);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    public BookDto externalBookByIsbn(String isbn) {
        Map<String, String> urlParams =
            new ImmutableMap.Builder<String, String>().put("isbn", isbn).build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openLibraryUrl+Route.URL_EXTERNAL_API);
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
