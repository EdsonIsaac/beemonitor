package io.github.edsonisaac.beemonitor.exceptions;

/**
 * The type Operation failed exception.
 *
 * @author Edson Isaac
 */
public class OperationFailedException extends RuntimeException {

    /**
     * Instantiates a new Operation failed exception.
     *
     * @param message the message
     */
    public OperationFailedException(String message) {
        super(message);
    }
}