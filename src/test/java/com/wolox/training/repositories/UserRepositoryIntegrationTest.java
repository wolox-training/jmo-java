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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private final Pageable pageable = Pageable.unpaged();

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

        Page<User> users = userRepository.findAll(pageable);

        assertTrue(savedUser.containsAll(users.getContent()));
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

        Page<User> users = userRepository.findByNameContainingIgnoreCaseAndBirthdateBetween("nando",
            startDate, endDate, pageable);

        assertEquals(3, users.getContent().size());
        assertTrue(savedUsers.containsAll(users.getContent()));
    }

    @Test
    void whenFindByNameAndBirthdate_ThenShouldIgnoreCaseAndReturnListOfUsers() {
        List<User> savedUsers = listPersistedUsers();

        LocalDate startDate = LocalDate.of(1915, 2, 21);
        LocalDate endDate = LocalDate.of(1925, 7, 10);

        Page<User> users = userRepository.findByNameContainingIgnoreCaseAndBirthdateBetween("NaNDo",
            startDate, endDate, pageable);

        assertEquals(3, users.getContent().size());
        assertTrue(savedUsers.containsAll(users.getContent()));
    }

    private List<User> listPersistedUsers() {
        List<User> userListWithParams = UserFactory.userlistWithSameParameters();
        List<User> userList = UserFactory.userList();

        List<User> list = Stream.of(userListWithParams, userList)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return userRepository.saveAll(list);
    }

    @TestFactory
    @DisplayName("Should allow querying with different null parameters")
    Stream<DynamicTest> should_be_allowed() {
        List<User> userListWithParams = UserFactory.userlistWithSameParameters();
        userRepository.saveAll(userListWithParams);

        LocalDate startDate = LocalDate.of(1915, 2, 21);
        LocalDate endDate = LocalDate.of(1925, 7, 10);

        return Stream.of(
            userRepository.findByOptinalNameAndBirthdate(
                "%nando%", null, null, pageable),
            userRepository.findByOptinalNameAndBirthdate(
                null, startDate, endDate, pageable),
            userRepository.findByOptinalNameAndBirthdate(
                "%nando%", startDate, endDate, pageable),
            userRepository.findByOptinalNameAndBirthdate(
                "%nando%", startDate, null, pageable)
        )
            .map(input -> DynamicTest.dynamicTest("Allowed: " + input,
                () -> assertEquals(3, input.getContent().size()))
            );
    }

    @TestFactory
    @DisplayName("Should allow querying with different null parameters")
    Stream<DynamicTest> should_be_allowed_with_optional_parameters() {
        List<User> userListWithParams = UserFactory.userlistWithSameParameters();
        userRepository.saveAll(userListWithParams);

        LocalDate birthdate = LocalDate.of(1915, 2, 21);

        return Stream.of(
            userRepository.getAll(null, "Fernando Emilio",
                birthdate, pageable),
            userRepository.getAll("Clow", null,
                birthdate, pageable),
            userRepository.getAll("Clow", "Fernando Emilio",
                null, pageable),
            userRepository.getAll(null, "Fernando Emilio",
                null, pageable),
            userRepository.getAll("Clow", null,
                null, pageable)
        )
            .map(input -> DynamicTest.dynamicTest("Allowed: " + input,
                () -> assertEquals(1, input.getContent().size()))
            );
    }
}