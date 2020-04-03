package com.example.Analytics.ExceptionClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(String m, Integer id) {
        super("Could not found "+m+" with id "+id);
    }
}
