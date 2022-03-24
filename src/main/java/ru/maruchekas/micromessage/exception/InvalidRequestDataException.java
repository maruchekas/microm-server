package ru.maruchekas.micromessage.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.maruchekas.micromessage.config.Constants.INVALID_DATEFORMAT;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidRequestDataException extends Exception{

    public InvalidRequestDataException(String message) {
        super(message);
    }
}
