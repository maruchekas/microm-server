package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.maruchekas.micromessage.config.Constants.ACCESS_DENIED;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends Exception {

    public AccessDeniedException() {
        super(ACCESS_DENIED);
    }
}
