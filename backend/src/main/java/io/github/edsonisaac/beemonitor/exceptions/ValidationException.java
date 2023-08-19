package io.github.edsonisaac.beemonitor.exceptions;

/**
 * Exception thrown when a validation error occurs.
 * Indicates that the provided input or data is invalid or does not meet the required criteria.
 * It is used to handle validation errors and provide relevant error messages.
 *
 * @author Edson Isaac
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new {@code ValidationException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ValidationException(String message) {
        super(message);
    }
}