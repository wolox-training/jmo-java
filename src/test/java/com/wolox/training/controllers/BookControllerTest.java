package com.wolox.training.controllers;

import com.wolox.training.factory.BookFactory;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockbookRepository;

    private final Book book = BookFactory.withDefaultData();

    @Test
    public void whenFindByIdWitchExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockbookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        String url = ("/api/books/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idBook\": null,"
                    + "    \"genre\": \"Fantasy\","
                    + "    \"author\": \"J. K. Rowlingn\","
                    + "    \"image\" : \"image.png\","
                    + "    \"title\": \"Harry Potter\","
                    + "    \"subtitle\" : \"-\","
                    + "    \"publisher\" :  \"Bloomsbury\","
                    + "    \"year\" : \"1997\","
                    + "    \"pages\" : 223,"
                    + "    \"isbn\" : \"6453723453\""
                    + "}"
            ));
    }

    @Test
    void whenListBook_thenBookisReturned() throws Exception {
        String url = ("/api/books");
        List<Book> books = BookFactory.bookList();
        Mockito.when(mockbookRepository.findAll()).thenReturn(books);
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[\n"
                    + "    {\n"
                    + "        \"idBook\": 1,\n"
                    + "        \"genre\": \"Fantasy\",\n"
                    + "        \"author\": \"J. K. Rowlingn\",\n"
                    + "        \"image\": \"image.png\",\n"
                    + "        \"title\": \"Harry Potter\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Bloomsbury\",\n"
                    + "        \"year\": \"1997\",\n"
                    + "        \"pages\": 223,\n"
                    + "        \"isbn\": \"6453723453\",\n"
                    + "        \"users\": null\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": 2,\n"
                    + "        \"genre\": \"Adventure\",\n"
                    + "        \"author\": \"J. R. R. Tolkien\",\n"
                    + "        \"image\": \"image2.png\",\n"
                    + "        \"title\": \"The Lord of the rings\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Allen & Unwin\",\n"
                    + "        \"year\": \"1954\",\n"
                    + "        \"pages\": 152,\n"
                    + "        \"isbn\": \"148758\",\n"
                    + "        \"users\": null\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": 3,\n"
                    + "        \"genre\": \"Children's novel\",\n"
                    + "        \"author\": \"Rob Kidd\",\n"
                    + "        \"image\": \"image3.png\",\n"
                    + "        \"title\": \"Pirates of the Caribbean: Jack Sparrow\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Disney Press\",\n"
                    + "        \"year\": \"2006\",\n"
                    + "        \"pages\": 236,\n"
                    + "        \"isbn\": \"7346345\",\n"
                    + "        \"users\": null\n"
                    + "    }\n"
                    + "]"
            ));
    }

    @Test
    void whenCreateBook_thenBookisReturned() throws Exception {
        String url = ("/api/books");
        Mockito.when(mockbookRepository.save(book)).thenReturn(book);
        mvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idBook\": null,\n"
                    + "    \"genre\": \"Fantasy\",\n"
                    + "    \"author\": \"J. K. Rowlingn\",\n"
                    + "    \"image\" : \"image.png\",\n"
                    + "    \"title\": \"Harry Poter\",\n"
                    + "    \"subtitle\" : \"-\",\n"
                    + "    \"publisher\" :  \"Bloomsbury\",\n"
                    + "    \"year\" : \"1997\",\n"
                    + "    \"pages\" : 223,\n"
                    + "    \"isbn\" : \"6453723453\"\n"
                    + "}"
            ));
    }






}