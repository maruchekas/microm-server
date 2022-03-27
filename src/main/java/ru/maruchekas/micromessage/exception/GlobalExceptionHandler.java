package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maruchekas.micromessage.api.response.BadResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<BadResponse> handleException(InvalidRequestDataException invalidRequestDataException){

        BadResponse badResponse = new BadResponse()
                .setTitle("date_format")
                .setMessage(invalidRequestDataException.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<BadResponse> handleException(NotSuchUserException notSuchUserException){

        BadResponse badResponse = new BadResponse()
                .setTitle("user")
                .setMessage(notSuchUserException.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<BadResponse> handleException(AccessDeniedException accessDeniedException){

        BadResponse badResponse = new BadResponse()
                .setTitle("permission")
                .setMessage(accessDeniedException.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.FORBIDDEN);
    }
}