package com.wolox.training.controllers;

import static com.wolox.training.constants.Constants.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolox.training.factory.BookFactory;
import com.wolox.training.model.Book;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.security.CustomAuthenticationProvider;
import com.wolox.training.services.OpenLibraryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    @SpyBean
    private OpenLibraryService openLibraryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Book book = BookFactory.withDefaultData();

    @Test
    @WithMockUser()
    void whenFindByIdWitchExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        String url = ("/api/books/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                jsonBookWithDefaultData()
            ));
    }

    @Test
    @WithMockUser()
    void whenListBook_thenAllBooksAreReturned() throws Exception {
        String url = ("/api/books");
        List<Book> books = BookFactory.bookList();
        Mockito.when(mockBookRepository.findAll()).thenReturn(books);
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Fantasy\",\n"
                    + "        \"author\": \"J. K. Rowlingn\",\n"
                    + "        \"image\": \"image.png\",\n"
                    + "        \"title\": \"Harry Potter\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Bloomsbury\",\n"
                    + "        \"year\": \"1997\",\n"
                    + "        \"pages\": 223,\n"
                    + "        \"isbn\": \"6453723453\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Adventure\",\n"
                    + "        \"author\": \"J. R. R. Tolkien\",\n"
                    + "        \"image\": \"image2.png\",\n"
                    + "        \"title\": \"The Lord of the rings\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Allen & Unwin\",\n"
                    + "        \"year\": \"1954\",\n"
                    + "        \"pages\": 152,\n"
                    + "        \"isbn\": \"148758\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Children's novel\",\n"
                    + "        \"author\": \"Rob Kidd\",\n"
                    + "        \"image\": \"image3.png\",\n"
                    + "        \"title\": \"Pirates of the Caribbean: Jack Sparrow\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Disney Press\",\n"
                    + "        \"year\": \"2006\",\n"
                    + "        \"pages\": 236,\n"
                    + "        \"isbn\": \"7346345\"\n"
                    + "    }\n"
                    + "]"
            ));
    }

    @Test
    void whenCreateBook_thenBookIsPersisted() throws Exception {
        String url = ("/api/books");
        Book defaultBook = BookFactory.withDefaultData();
        Mockito.when(mockBookRepository.save(defaultBook)).thenReturn(book);
        mvc.perform(MockMvcRequestBuilders.post(url)
            .content(objectMapper.writeValueAsString(defaultBook))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                jsonBookWithDefaultData()
            ));
    }

    private String jsonBookWithDefaultData() {
        return "{\n"
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
            + "}";
    }

    @Test
    @WithMockUser()
    void whenUpdateBook_ifExistsThenBookIsUpdated() throws Exception {
        String url = ("/api/books");
        Mockito.when(mockBookRepository.findById(book.getIdBook())).thenReturn(Optional.of(book));
        book.setImage("HappyHarry.png");
        Mockito.when(mockBookRepository.save(book)).thenReturn(book);
        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(book))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenUpdateBook_ifNotExistsThenReturnError() throws Exception {
        String url = ("/api/books");
        Mockito.when(mockBookRepository.findById(book.getIdBook())).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(book))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenDeleteBook_ifExistsThenBookIsRemoved() throws Exception {
        String url = ("/api/books/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(mockBookRepository).deleteById(book.getIdBook());
        mvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenDeleteBook_ifNotExistsThenBookReturnError() throws Exception {
        String url = ("/api/books/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.doNothing().when(mockBookRepository).deleteById(book.getIdBook());
        mvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @WithMockUser
    void whenFindByIsbnWitchExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findByIsbn("0385472579")).thenReturn(Optional.of(book));
        String url = ("/api/books/findIsbn/0385472579");
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
        String url = ("/api/books/findIsbn/0385472579");
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
        String url = ("/api/books/findIsbn/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenFindByParameters_ThenReturnListOfBooks() throws Exception {
        List<Book> books = BookFactory.bookListWithSameParameters();

        Mockito.when(mockBookRepository.findByPublisherAndGenreAndYear("Walt Disney",
            "Adventure", "1988")).thenReturn(books);

        String url = ("/api/books/Walt Disney/Adventure/1988");

        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Adventure\",\n"
                    + "        \"author\": \"John Lasseter\",\n"
                    + "        \"image\": \"Buddy.png\",\n"
                    + "        \"title\": \"Toy Story\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Walt Disney\",\n"
                    + "        \"year\": \"1988\",\n"
                    + "        \"pages\": 392,\n"
                    + "        \"isbn\": \"8472821\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Adventure\",\n"
                    + "        \"author\": \"Don Hahn\",\n"
                    + "        \"image\": \"Lion-King.png\",\n"
                    + "        \"title\": \"The Lion King\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Walt Disney\",\n"
                    + "        \"year\": \"1988\",\n"
                    + "        \"pages\": 98,\n"
                    + "        \"isbn\": \"1234701\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idBook\": null,\n"
                    + "        \"genre\": \"Adventure\",\n"
                    + "        \"author\": \"John Musker\",\n"
                    + "        \"image\": \"SadMoana.png\",\n"
                    + "        \"title\": \"Moana\",\n"
                    + "        \"subtitle\": \"-\",\n"
                    + "        \"publisher\": \"Walt Disney\",\n"
                    + "        \"year\": \"1988\",\n"
                    + "        \"pages\": 142,\n"
                    + "        \"isbn\": \"986273645\"\n"
                    + "    }\n"
                    + "]"
            ));
    }

}