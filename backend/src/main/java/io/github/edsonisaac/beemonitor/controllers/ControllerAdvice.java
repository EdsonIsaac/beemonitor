package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Controller advice.
 *
 * @author Edson Isaac
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    /**
     * Object not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {

        StandardError error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Validation exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationException(ValidationException ex, HttpServletRequest request) {

        StandardError error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Operation failed exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity operationFailedException(OperationFailedException ex, HttpServletRequest request) {

        StandardError error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}