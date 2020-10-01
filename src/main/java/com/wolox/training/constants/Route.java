package com.wolox.training.constants;

import org.springframework.jmx.access.InvalidInvocationException;

public final class Route {

    private Route() {
        throw new InvalidInvocationException(Message.INVALID_INVOCATION);
    }

    public static final String BOOKS = "/api/books";
    public static final String ID_BOOK = "/{idBook}";
    public static final String USERS = "/api/users";
    public static final String ADD_BOOK = "/addBook/{idUser}/{idBook}";
    public static final String REMOVE_BOOK = "/removeBook/{idUser}/{idBook}";
    public static final String ID_USER = "/{idUser}";
    public static final String URL_EXTERNAL_API = "https://openlibrary.org/isbn/{isbn}.json";
    public static final String ISBN = "/findIsbn/{isbn}";
    public static final String BOOK_PARAMS = "/{publisher}/{genre}/{year}";
    public static final String USER_PARAMS = "/{name}/{startDate}/{endDate}";
    public static final String OPTIONAL_PARAMS = "/optional";
    public static final String ALL_OPTIONAL_PARAMS = "/all";

}
