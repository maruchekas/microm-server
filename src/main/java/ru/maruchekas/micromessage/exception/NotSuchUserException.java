package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.maruchekas.micromessage.config.Constants.USER_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotSuchUserException extends Exception{
    public NotSuchUserException() {
        super(USER_NOT_FOUND);
    }
}
