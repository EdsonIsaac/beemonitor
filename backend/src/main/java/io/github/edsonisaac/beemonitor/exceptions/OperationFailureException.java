package io.github.edsonisaac.beemonitor.exceptions;

/**
 * Exception thrown when an operation fails.
 *
 * @author Edson Isaac
 */
public class OperationFailureException extends RuntimeException {

    /**
     * Constructs a new {@code OperationFailureException} with the specified detail message.
     *
     * @param message the detail message
     */
    public OperationFailureException(String message) {
        super(message);
    }
}