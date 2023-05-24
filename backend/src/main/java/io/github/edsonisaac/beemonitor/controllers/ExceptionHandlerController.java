package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import io.github.edsonisaac.beemonitor.exceptions.StandardError;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller advice to handle exceptions globally.
 *
 * @author Edson Isaac
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Handles the DataIntegrityViolationException and returns a ResponseEntity with a StandardError object containing details
     * about the data integrity violation error.
     *
     * @param ex      the DataIntegrityViolationException thrown
     * @param request the HttpServletRequest object
     * @return ResponseEntity with a StandardError object in the response body
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity with a list of StandardError objects
     * containing details about the validation errors.
     *
     * @param ex      the MethodArgumentNotValidException thrown
     * @param request the HttpServletRequest object
     * @return ResponseEntity with a list of StandardError objects in the response body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        final var errors = ex.getBindingResult().getFieldErrors().stream().map(error ->
                StandardError.builder()
                        .timestamp(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(StringUtils.capitalize(error.getField()) + " " + error.getDefaultMessage() + "!")
                        .path(request.getRequestURI())
                        .build()
        ).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles ObjectNotFoundException and returns a ResponseEntity with a StandardError object containing details
     * about the not found error.
     *
     * @param ex      the ObjectNotFoundException thrown
     * @param request the HttpServletRequest object
     * @return ResponseEntity with a StandardError object in the response body
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles OperationFailureException and returns a ResponseEntity with a StandardError object containing details
     * about the internal server error.
     *
     * @param ex      the OperationFailureException thrown
     * @param request the HttpServletRequest object
     * @return ResponseEntity with a StandardError object in the response body
     */
    @ExceptionHandler(OperationFailureException.class)
    public ResponseEntity<?> operationFailureException(OperationFailureException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handles ValidationException and returns a ResponseEntity with a StandardError object containing details
     * about the validation error.
     *
     * @param ex      the ValidationException thrown
     * @param request the HttpServletRequest object
     * @return ResponseEntity with a StandardError object in the response body
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}