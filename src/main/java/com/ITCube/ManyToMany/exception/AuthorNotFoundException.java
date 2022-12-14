package com.ITCube.ManyToMany.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
