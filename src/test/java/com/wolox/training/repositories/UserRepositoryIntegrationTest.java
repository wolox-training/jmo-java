package com.wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wolox.training.factory.UserFactory;
import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = UserFactory.withDefaultData();

    @AfterEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenCreateUser_thenUserIsPersisted() {
        userRepository.save(user);

        User persistedUser = userRepository.findByName(user.getName()).orElse(null);

        assert persistedUser != null;
        validateUserAttributes(persistedUser);
    }

    @Test
    void whenListUsers_thenAllUserAreReturned() {
        List<User> usersList = UserFactory.userList();
        List<User> savedUser = userRepository.saveAll(usersList);

        List<User> users = userRepository.findAll();

        assertTrue(savedUser.containsAll(users));
    }

    @Test
    void whenFindByIdWitchExists_thenUserIsReturned() {
        User savedUser = userRepository.save(user);

        User persistedUser = userRepository.findById(savedUser.getIdUser()).orElse(null);

        assert persistedUser != null;
        validateUserAttributes(persistedUser);
    }

    @Test
    void whenUpdateUser_ifExistsThenUserIsUpdated() {
        userRepository.save(user);
        user.setUsername("Karateka");

        User persistedUser = userRepository.save(user);

        assertEquals("Karateka", persistedUser.getUsername());
    }

    @Test
    void whenDeleteUser_ifExistsThenUserIsRemoved() {
        User saveUser = userRepository.save(user);
        userRepository.deleteById(saveUser.getIdUser());

        User persistedUser = userRepository.findById(saveUser.getIdUser()).orElse(null);

        assertNull(persistedUser);
    }

    private void validateUserAttributes(User persistedUser) {
        assertNotNull(persistedUser.getIdUser());
        assertEquals("Kevv", persistedUser.getUsername());
        assertEquals("Kevin", persistedUser.getName());
        assertEquals(LocalDate.of(1994, 5, 2), persistedUser.getBirthdate());
    }

    @Test
    void whenFindByNameAndBirthdate_ThenReturnListOfUsers() {
        List<User> savedUsers = listPersistedUsers();

        LocalDate startDate = LocalDate.of(1915, 2, 21);
        LocalDate endDate = LocalDate.of(1925, 7, 10);

        List<User> users = userRepository.findByNameContainingIgnoreCaseAndBirthdateBetween("nando",
            startDate, endDate);

        assertEquals(3, users.size());
        assertTrue(savedUsers.containsAll(users));
    }

    @Test
    void whenFindByNameAndBirthdate_ThenShouldIgnoreCaseAndReturnListOfUsers() {
        List<User> savedUsers = listPersistedUsers();

        LocalDate startDate = LocalDate.of(1915, 2, 21);
        LocalDate endDate = LocalDate.of(1925, 7, 10);

        List<User> users = userRepository.findByNameContainingIgnoreCaseAndBirthdateBetween("NaNDo",
            startDate, endDate);

        assertEquals(3, users.size());
        assertTrue(savedUsers.containsAll(users));
    }

    private List<User> listPersistedUsers() {
        List<User> userListWithParams = UserFactory.userlistWithParams();
        List<User> userList = UserFactory.userList();

        List<User> list = Stream.of(userListWithParams, userList)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return userRepository.saveAll(list);
    }
}