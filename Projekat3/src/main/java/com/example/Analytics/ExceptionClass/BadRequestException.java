package com.example.Analytics.ExceptionClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException  extends RuntimeException {

    public BadRequestException(String poruka) {
        super(poruka);
    }
}
