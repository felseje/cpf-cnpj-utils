package io.github.felseje.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that a document is invalid or malformed.
 * <p>
 * This exception typically signals a failure in validation logic, such as
 * incorrect length, invalid characters, or failed check digits.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidDocumentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4852739459128734621L;

    /**
     * Constructs a new {@link InvalidDocumentException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception.
     */
    public InvalidDocumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link InvalidDocumentException} with the specified detail message and cause.
     *
     * @param message the detail message describing the reason for the exception.
     * @param cause the cause of the exception (which is saved for later retrieval by {@link #getCause()}).
     */
    public InvalidDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
