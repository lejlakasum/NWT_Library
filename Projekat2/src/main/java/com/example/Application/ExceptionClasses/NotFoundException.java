package com.example.Application.ExceptionClasses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String model, Integer id) {
        super("Could not find " + model + " with id: " + id);
    }
}
