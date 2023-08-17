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

@RestControllerAdvice
public class ExceptionHandlerController {

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