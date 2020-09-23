package com.wolox.training.controllers;

import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;
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

    private final Book book = new Book();


    @Before
    void setUp() {
        book.setGenre("Fantasy");
        book.setAuthor("J. K. Rowlingn");
        book.setImage("image.png");
        book.setTitle("Harry Potter");
        book.setSubtitle("-");
        book.setPublisher("Bloomsbury");
        book.setYear("1997");
        book.setPages(223);
        book.setIsbn("6453723453");
    }

    @Test
    public void whenFindByIdWitchExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockbookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        String url = ("/api/books/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idBook\": 0,\n"
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

    @Test
    public void whenListBook_thenBookisReturned() throws Exception {
        String url = ("/api/books");
        Mockito.when(mockbookRepository.save(book)).thenReturn(book);
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idBook\": 0,\n"
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

    @Test
    public void whenCreateBook_thenBookisReturned() throws Exception {
        String url = ("/api/books");
        Mockito.when(mockbookRepository.save(book)).thenReturn(book);
        mvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idBook\": 0,\n"
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