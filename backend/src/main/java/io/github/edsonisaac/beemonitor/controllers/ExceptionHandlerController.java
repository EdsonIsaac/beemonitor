package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import io.github.edsonisaac.beemonitor.exceptions.StandardError;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Controller advice to handle exceptions globally.
 *
 * @author Edson Isaac
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Handles AuthenticationException and returns an Unauthorized response.
     *
     * @param ex      The AuthenticationException thrown
     * @param request The HttpServletRequest object
     * @return A StandardError object in the response body
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public StandardError authenticationException(AuthenticationException ex, HttpServletRequest request) {

        return StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(UNAUTHORIZED.value())
                .error("Unauthorized")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    /**
     * Handles the DataIntegrityViolationException and returns a ResponseEntity with a StandardError object containing details
     * about the data integrity violation error.
     *
     * @param ex      the DataIntegrityViolationException thrown
     * @param request the HttpServletRequest object
     * @return A StandardError object in the response body
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StandardError dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

        return StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity with a list of StandardError objects
     * containing details about the validation errors.
     *
     * @param ex      the MethodArgumentNotValidException thrown
     * @param request the HttpServletRequest object
     * @return A list of StandardError objects in the response body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        return ex.getBindingResult().getFieldErrors().stream().map(error ->
                StandardError.builder()
                        .timestamp(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(StringUtils.capitalize(error.getField()) + " " + error.getDefaultMessage() + "!")
                        .path(request.getRequestURI())
                        .build()
        ).toList();
    }

    /**
     * Handles ObjectNotFoundException and returns a ResponseEntity with a StandardError object containing details
     * about the not found error.
     *
     * @param ex      the ObjectNotFoundException thrown
     * @param request the HttpServletRequest object
     * @return A StandardError object in the response body
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public StandardError objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {

        return StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    /**
     * Handles OperationFailureException and returns a ResponseEntity with a StandardError object containing details
     * about the internal server error.
     *
     * @param ex      the OperationFailureException thrown
     * @param request the HttpServletRequest object
     * @return A StandardError object in the response body
     */
    @ExceptionHandler(OperationFailureException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public StandardError operationFailureException(OperationFailureException ex, HttpServletRequest request) {

        return StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    /**
     * Handles ValidationException and returns a ResponseEntity with a StandardError object containing details
     * about the validation error.
     *
     * @param ex      the ValidationException thrown
     * @param request the HttpServletRequest object
     * @return A StandardError object in the response body
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StandardError validationException(ValidationException ex, HttpServletRequest request) {

        return StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}