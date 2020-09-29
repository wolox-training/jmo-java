package com.wolox.training.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class BookTest {

    private final Book book = new Book();

    @TestFactory
    @DisplayName("Should fail when sending null to required values")
    Collection<DynamicTest> should_be_rejected() {
        return Arrays.asList(
            DynamicTest.dynamicTest("Null author test",
                () -> assertThrows(NullPointerException.class, () -> book.setAuthor(null))),
            DynamicTest.dynamicTest("Null image test",
                () -> assertThrows(NullPointerException.class, () -> book.setImage(null))),
            DynamicTest.dynamicTest("Null title test",
                () -> assertThrows(NullPointerException.class, () -> book.setTitle(null))),
            DynamicTest.dynamicTest("Null subtitle test",
                () -> assertThrows(NullPointerException.class, () -> book.setSubtitle(null))),
            DynamicTest.dynamicTest("Null publisher test",
                () -> assertThrows(NullPointerException.class, () -> book.setPublisher(null))),
            DynamicTest.dynamicTest("Null year test",
                () -> assertThrows(NullPointerException.class, () -> book.setYear(null))),
            DynamicTest.dynamicTest("Null isbn test",
                () -> assertThrows(NullPointerException.class, () -> book.setIsbn(null))),
            DynamicTest.dynamicTest("Null pages test",
                () -> assertThrows(NullPointerException.class, () -> book.setPages(null))));
    }

}