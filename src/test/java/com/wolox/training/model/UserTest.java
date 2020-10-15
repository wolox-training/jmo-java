package com.wolox.training.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserTest {

    private final User user = new User();

    @TestFactory
    @DisplayName("Should fail when sending null to required values")
    Collection<DynamicTest> should_be_rejected() {
        return Arrays.asList(
            DynamicTest.dynamicTest("Null username test",
                () -> assertThrows(NullPointerException.class, () -> user.setUsername(null))),
            DynamicTest.dynamicTest("Null name test",
                () -> assertThrows(NullPointerException.class, () -> user.setName(null))),
            DynamicTest.dynamicTest("Null birthdate test",
                () -> assertThrows(NullPointerException.class, () -> user.setBirthdate(null))));
    }
}