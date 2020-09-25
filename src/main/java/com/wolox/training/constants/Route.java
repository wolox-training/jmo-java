package com.wolox.training.constants;

import org.springframework.jmx.access.InvalidInvocationException;

public final class Route {

    private Route(){
        throw new InvalidInvocationException(Message.INVALID_INVOCATION);
    }

    public static final String BOOKS = "/api/books";
    public static final String ID_BOOK = "/{idBook}";
    public static final String USERS = "/api/users";
    public static final String ADD_BOOK = "/addBook/{idUser}/{idBook}";
    public static final String REMOVE_BOOK = "/removeBook/{idUser}/{idBook}";
    public static final String ID_USER = "/{idUser}";
}
