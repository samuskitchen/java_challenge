package com.wolox.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlbumException extends RuntimeException {

    private final String message;

    public AlbumException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
