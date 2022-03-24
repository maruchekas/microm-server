package ru.maruchekas.micromessage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<?> handleException(InvalidRequestDataException invalidRequestDataException){

        return new ResponseEntity<>(invalidRequestDataException.getMessage(), HttpStatus.BAD_REQUEST);
    }



}