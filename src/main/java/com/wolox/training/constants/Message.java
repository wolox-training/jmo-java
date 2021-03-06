package com.wolox.training.constants;

import org.springframework.jmx.access.InvalidInvocationException;

public class Message {

    private Message() {
        throw new InvalidInvocationException(INVALID_INVOCATION);
    }

    public static final String INVALID_INVOCATION = "Invalid invocation";
    public static final String BOOK_ALREADY_EXIST = "Book already exists.";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXIST = "User already exists.";
    public static final String NOT_FOUND = "Not found";
    public static final String USER_HAS_NOT_BOOK = "User has not the book";
    public static final String BOOK_CAN_NOT_NULL = "Book can not be null";
    public static final String BOOK_IS_ALREADY_ASSOCIATED = "Book is already associated";
    public static final String TITLE_APP = "Training REST API";
    public static final String LICENSE = "Apache 2.0";
    public static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
    public static final String APP_VERSION = "1.0.0";

}
