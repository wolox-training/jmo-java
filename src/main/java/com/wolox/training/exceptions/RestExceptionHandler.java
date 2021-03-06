package com.wolox.training.exceptions;

import com.wolox.training.constants.Message;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookNotFoundException.class,
        UserNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, Message.NOT_FOUND,
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(
        Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
