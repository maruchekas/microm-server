package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ru.maruchekas.micromessage.config.Constants.INVALID_DATEFORMAT;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<?> handleException(InvalidRequestDataException invalidRequestDataException){

        return new ResponseEntity<>(INVALID_DATEFORMAT, HttpStatus.BAD_REQUEST);
    }



}