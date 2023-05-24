package io.github.edsonisaac.beemonitor.exceptions;

/**
 * Exception thrown when an object is not found.
 *
 * @author Edson Isaac
 */
public class ObjectNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ObjectNotFoundException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}