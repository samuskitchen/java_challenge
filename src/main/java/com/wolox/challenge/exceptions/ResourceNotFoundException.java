package com.wolox.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final String resource;
    private final String field;
    private final Object value;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));

        this.resource = resourceName;
        this.field = fieldName;
        this.value = fieldValue;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
