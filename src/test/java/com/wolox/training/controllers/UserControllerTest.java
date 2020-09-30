package com.wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wolox.training.factory.BookFactory;
import com.wolox.training.factory.UserFactory;
import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import com.wolox.training.security.CustomAuthenticationProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private BookRepository mockBookRepository;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final User user = UserFactory.withDefaultData();

    private final Book book = BookFactory.withDefaultData();

    @BeforeEach
    void before() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @WithMockUser()
    void whenFindByIdWitchExists_thenUserIsReturned() throws Exception {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        String url = ("/api/users/1");
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idUser\": null,\n"
                    + "    \"username\": \"Kevv\",\n"
                    + "    \"name\": \"Kevin\",\n"
                    + "    \"birthdate\": \"1994-05-02\"\n"
                    + "}"
            ));
    }

    @Test
    @WithMockUser()
    void whenListUsers_allUsersAreReturned() throws Exception {
        String url = ("/api/users");
        List<User> users = UserFactory.userList();
        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"Kevv\",\n"
                    + "        \"name\": \"Kevin\",\n"
                    + "        \"birthdate\": \"1994-05-02\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"CamuXee\",\n"
                    + "        \"name\": \"Jhoan\",\n"
                    + "        \"birthdate\": \"1994-07-22\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"Darvand\",\n"
                    + "        \"name\": \"David\",\n"
                    + "        \"birthdate\": \"1995-06-28\"\n"
                    + "    }\n"
                    + "]"
            ));
    }

    @Test
    void whenCreateUser_thenUserIsPersisted() throws Exception {
        String url = ("/api/users");
        User defaultUser = UserFactory.withDefaultData();
        Mockito.when(mockUserRepository.save(defaultUser)).thenReturn(user);
        Mockito.when(passwordEncoder.encode("123456")).thenReturn(user.getPassword());

        mvc.perform(MockMvcRequestBuilders.post(url)
            .content(objectMapper.writeValueAsString(defaultUser))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "{\n"
                    + "    \"idUser\": null,\n"
                    + "    \"username\": \"Kevv\",\n"
                    + "    \"name\": \"Kevin\",\n"
                    + "    \"birthdate\": \"1994-05-02\"\n"
                    + "}"
            ));
    }

    @Test
    @WithMockUser()
    void whenUpdateUser_ifExistsThenUserIsUpdated() throws Exception {
        String url = ("/api/users");
        Mockito.when(mockUserRepository.findById(user.getIdUser())).thenReturn(Optional.of(user));
        user.setUsername("Karateka");
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenUpdateUser_ifNotExistsThenReturnError() throws Exception {
        String url = ("/api/users");
        Mockito.when(mockUserRepository.findById(user.getIdUser())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenDeleteUser_ifExistsThenUserIsRemoved() throws Exception {
        String url = ("/api/users/1");
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(mockUserRepository).deleteById(user.getIdUser());

        mvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenDeleteUser_ifNotExistsThenUserReturnError() throws Exception {
        String url = ("/api/users/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.doNothing().when(mockBookRepository).deleteById(user.getIdUser());

        mvc.perform(MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenAddBookToUser_thenBookIsPersistedInUser() throws Exception {
        String url = ("/api/users/addBook/1/1");
        User defaultUser = UserFactory.withDefaultData();
        defaultUser.setBooks(new ArrayList<>());
        user.setBooks(new ArrayList<>());
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.of(defaultUser));
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));

        user.addBook(book);

        Mockito.when(mockUserRepository.save(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(defaultUser))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenAddBookToUser_IfBookNotExistThenReturnedError() throws Exception {
        String url = ("/api/users/addBook/1/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenAddBookToUser_IfUserNotExistThenReturnedError() throws Exception {
        String url = ("/api/users/addBook/1/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenRemoveBookToUser_thenBookIsPersistedInUser() throws Exception {
        String url = ("/api/users/removeBook/1/1");
        User defaultUser = UserFactory.withDefaultData();
        defaultUser.setBooks(new ArrayList<>());
        defaultUser.setBooks(new ArrayList<>());
        defaultUser.addBook(book);
        user.setBooks(new ArrayList<>());
        user.addBook(book);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.of(defaultUser));
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));

        user.removeBook(book);

        Mockito.when(mockUserRepository.save(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(defaultUser))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser()
    void whenRemoveBookToUser_IfBookNotExistThenReturnedError() throws Exception {
        String url = ("/api/users/removeBook/1/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser()
    void whenRemoveBookToUser_IfUserNotExistThenReturnedError() throws Exception {
        String url = ("/api/users/removeBook/1/1");
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put(url)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Karateka")
    void whenInvokerCurrentUserName_ThenReturnedUserName() throws Exception {
        String url = ("/api/users/username");

        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Karateka"));
    }

    @Test
    @WithMockUser()
    void whenFindByParameters_ThenReturnListOfUsers() throws Exception {
        List<User> users = UserFactory.userlistWithParams();
        String startDate = "1915-02-21";
        String endDate = "1926-07-10";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        Mockito.when(mockUserRepository.findByNameContainingIgnoreCaseAndBirthdateBetween("nando",
            start, end)).thenReturn(users);

        String url = ("/api/users/nando/1915-02-21/1926-07-10");

        mvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"Xand\",\n"
                    + "        \"name\": \"Fernando Andres\",\n"
                    + "        \"birthdate\": \"1921-07-22\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"Clow\",\n"
                    + "        \"name\": \"Fernando Emilio\",\n"
                    + "        \"birthdate\": \"1916-09-16\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"idUser\": null,\n"
                    + "        \"username\": \"Batist\",\n"
                    + "        \"name\": \"Favio Fernando\",\n"
                    + "        \"birthdate\": \"1923-06-28\"\n"
                    + "    }\n"
                    + "]"
            ));
    }
}