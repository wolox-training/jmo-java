package com.wolox.training.factory;

import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public final class UserFactory {

    public static User withDefaultData() {
        User user = new User();
        user.setUsername("Kevv");
        user.setName("Kevin");
        user.setBirthdate(LocalDate.of(1994, 5, 2));
        user.setPassword("123456");
        return user;
    }

    public static List<User> userList() {
        User user1 = withDefaultData();

        User user2 = new User();
        user2.setUsername("CamuXee");
        user2.setName("Jhoan");
        user2.setBirthdate(LocalDate.of(1994, 7, 22));
        user2.setPassword("2846sbd");

        User user3 = new User();
        user3.setUsername("Darvand");
        user3.setName("David");
        user3.setBirthdate(LocalDate.of(1995, 6, 28));
        user3.setPassword("hsgaaa$12");

        return Arrays.asList(user1, user2, user3);
    }

    public static List<User> userlistWithParams() {
        User user1 = new User();
        user1.setUsername("Xand");
        user1.setName("Fernando Andres");
        user1.setBirthdate(LocalDate.of(1921, 7, 22));
        user1.setPassword("euabdm");

        User user2 = new User();
        user2.setUsername("Clow");
        user2.  setName("Fernando Emilio");
        user2.setBirthdate(LocalDate.of(1916, 9, 16));
        user2.setPassword("anit");

        User user3 = new User();
        user3.setUsername("Batist");
            user3.setName("Favio Fernando");
        user3.setBirthdate(LocalDate.of(1923, 6, 28));
        user3.setPassword("2cfeadf");

        return Arrays.asList(user1, user2, user3);
    }
}
