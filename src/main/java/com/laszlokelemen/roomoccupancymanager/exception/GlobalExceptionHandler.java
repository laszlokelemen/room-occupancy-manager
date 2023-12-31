package com.laszlokelemen.roomoccupancymanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.Collections;


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
    public ResponseEntity<ExceptionDetails> handleInvalidInput(HttpMessageNotReadableException exception,
                                                               WebRequest request) {
        return getExceptionDetailsResponseEntity(exception.getMessage(), request);
    }

    /**
     * Handles the exception when method arguments are not valid.
     *
     * @param exception the MethodArgumentNotValidException
     * @param request   the WebRequest
     * @return the ResponseEntity containing the exception details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidArgument(MethodArgumentNotValidException exception,
                                                                  WebRequest request) {
        return getExceptionDetailsResponseEntity(exception.getMessage(), request);
    }

    private static ResponseEntity<ExceptionDetails> getExceptionDetailsResponseEntity(String exception, WebRequest request) {
        var exceptionDetails = new ExceptionDetails(
                LocalDate.now(),
                Collections.singletonList(exception),
                request.getDescription(false)
        );
        return ResponseEntity.badRequest().body(exceptionDetails);
    }
}