package com.hyperit.flightadvisor.exception;

import com.hyperit.flightadvisor.bean.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<Object> handleUploadException(UploadException ex) {

        return new ResponseEntity<>(generateResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleUploadException(BadRequestException ex) {

        return new ResponseEntity<>(generateResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse generateResponse(String message) {
        return new ExceptionResponse()
                .setMessage(message)
                .setDate(LocalDateTime.now());
    }

}
