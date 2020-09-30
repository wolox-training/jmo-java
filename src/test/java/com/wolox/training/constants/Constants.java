package com.wolox.training.constants;

import org.springframework.jmx.access.InvalidInvocationException;

public final class Constants {

    private Constants() {
        throw new InvalidInvocationException(Message.INVALID_INVOCATION);
    }

    public static final String UTF_8 = "utf-8";
}
