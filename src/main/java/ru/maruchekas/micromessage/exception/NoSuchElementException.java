package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementException extends Exception{
    public NoSuchElementException(String message) {
        super(message);
    }
}
