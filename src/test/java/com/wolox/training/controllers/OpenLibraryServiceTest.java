package com.wolox.training.controllers;

import com.wolox.training.factory.BookFactory;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(OpenLibraryService.class)
class OpenLibraryServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;

    @Spy
    @InjectMocks
    private OpenLibraryService controller;

    private final Book book = BookFactory.withDefaultData();

    @Test
    @WithMockUser
    void whenFindByIsbnWitchExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findByIsbn("0385472579")).thenReturn(Optional.of(book));
        String url = ("/api/openLibrary/0385472579");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"title\": \"Harry Potter\","
                    + "    \"subtitle\" : \"-\","
                    + "    \"publishers\" :  \"Bloomsbury\","
                    + "    \"authors\": \"J. K. Rowlingn\","
                    + "    \"publish_date\" : \"1997\","
                    + "    \"number_of_pages\" : 223,"
                    + "    \"isbn_10\" : \"6453723453\""
                    + "}"
            ));
    }

    @Test
    @WithMockUser
    @DisplayName("When the book not exist in my data base, then consult book in external api, if exist it persists")
    void whenFindByIsbnWitchNotExists_thenPersistedBookAndItIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findByIsbn("0385472579")).thenReturn(Optional.empty());
        Mockito.when(mockBookRepository.save(Mockito.any())).thenReturn(book);
        String url = ("/api/openLibrary/0385472579");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"title\": \"Harry Potter\","
                    + "    \"subtitle\" : \"-\","
                    + "    \"publishers\" :  \"Bloomsbury\","
                    + "    \"authors\": \"J. K. Rowlingn\","
                    + "    \"publish_date\" : \"1997\","
                    + "    \"number_of_pages\" : 223,"
                    + "    \"isbn_10\" : \"6453723453\""
                    + "}"
            ));
    }

    @Test
    @WithMockUser
    void whenFindByIsbnWitchNotExists_thenReturnError() throws Exception {
        Mockito.when(mockBookRepository.findByIsbn("1")).thenReturn(Optional.empty());
        String url = ("/api/openLibrary/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

