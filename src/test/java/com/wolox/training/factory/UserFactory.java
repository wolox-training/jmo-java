package com.wolox.training.factory;

import com.wolox.training.model.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public final class UserFactory {

    public static User withDefaultData() {
        return new User(
            1L,
            "Kevv",
            "Kevin",
            LocalDate.of(1994, 5, 2)
        );
    }

    public static User withDefaultDataWithoutId() {
        return new User(
            "Kevv",
            "Kevin",
            LocalDate.of(1994, 5, 2)
        );
    }

    public static User withNullUserame() {
        LocalDate birthdate = LocalDate.of(1994, 5, 2);
        return new User(
            null,
            "Kevin",
            birthdate);
    }

    public static User withNullName() {
        LocalDate birthdate = LocalDate.of(1994, 5, 2);
        return new User(
            "Kevv",
            null,
            birthdate);
    }

    public static User withNullBirthdate() {
        return new User(
            "Kevv",
            "Kevin",
            null);
    }

    public static List<User> userList() {
        User user1 = new User(
            1L,
            "Kevv",
            "Kevin",
            LocalDate.of(1994, 5, 2));

        User user2 = new User(
            2L,
            "CamuXee",
            "Jhoan",
            LocalDate.of(1994, 7, 22));

        User user3 = new User(
            3L,
            "Darvand",
            "David",
            LocalDate.of(1995, 6, 28));

        return Arrays.asList(user1, user2, user3);
    }
}
