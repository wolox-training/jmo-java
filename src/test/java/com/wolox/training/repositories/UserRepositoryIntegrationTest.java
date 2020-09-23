package com.wolox.training.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wolox.training.factory.UserFactory;
import com.wolox.training.model.Book;
import com.wolox.training.model.User;
import java.util.stream.Stream;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private Book book;

    private User user;

    @TestFactory
    @DisplayName("Should fail when sending null to required values")
    Stream<DynamicTest> should_be_rejected() {
        return Stream.of(
            UserFactory.withNullUserame(),
            UserFactory.withNullName(),
            UserFactory.withNullBirthdate()
            )
            .map(input -> DynamicTest.dynamicTest("Should failed ",
                () -> assertThrows(ConstraintViolationException.class, () -> userRepository.save(input))
            ));
    }
}