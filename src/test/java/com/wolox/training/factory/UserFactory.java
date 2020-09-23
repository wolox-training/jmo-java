package com.wolox.training.factory;

import com.wolox.training.model.User;
import java.time.LocalDate;

public final class UserFactory {


    public static User withDefaultData() {
        LocalDate birthdate = LocalDate.of(1994, 5, 2);
        User user = new User();
        user.setUsername("Kevv");
        user.setName("Kevin");
        user.setBirthdate(birthdate);
        return user;
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
}
