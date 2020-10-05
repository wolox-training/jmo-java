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
        return user;
    }

    public static User withNullUserame() {
        User user = new User();
        user.setName("Kevin");
        user.setBirthdate(LocalDate.of(1994, 5, 2));
        return user;
    }

    public static User withNullName() {
        User user = new User();
        user.setUsername("Kevv");
        user.setBirthdate(LocalDate.of(1994, 5, 2));
        return user;
    }

    public static User withNullBirthdate() {
        User user = new User();
        user.setUsername("Kevv");
        user.setName("Kevin");
        return user;
    }

    public static List<User> userList() {
        User user1 = withDefaultData();

        User user2 = new User();
        user2.setUsername("CamuXee");
        user2.setName("Jhoan");
        user2.setBirthdate(LocalDate.of(1994, 7, 22));

        User user3 = new User();
        user3.setUsername("Darvand");
        user3.setName("David");
        user3.setBirthdate(LocalDate.of(1995, 6, 28));

        return Arrays.asList(user1, user2, user3);
    }
}
