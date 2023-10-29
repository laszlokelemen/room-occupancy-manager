package com.example.roomoccupancymanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles the HttpMessageNotReadableException and returns a ResponseEntity with the ExceptionDetails.
     *
     * @param exception the HttpMessageNotReadableException that occurred
     * @param request   the WebRequest object representing the incoming request
     * @return a ResponseEntity with the ExceptionDetails as the body
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidInput(HttpMessageNotReadableException exception, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(new Date())
                .messages(Collections.singletonList(exception.getMessage()))
                .path(request.getDescription(false))
                .build();
        return ResponseEntity.badRequest().body(exceptionDetails);
    }
}