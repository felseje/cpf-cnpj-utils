package io.github.felseje.cnpj.exception;

import io.github.felseje.exception.InvalidDocumentException;

import java.io.Serial;

/**
 * Exception thrown to indicate that a CNPJ is invalid or malformed.
 * <p>
 * This exception typically signals a failure in validation logic, such as
 * incorrect length, invalid characters, or failed check digits.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidCnpjException extends InvalidDocumentException {

    @Serial
    private static final long serialVersionUID = 8956231479812354789L;

    /**
     * Constructs a new {@link InvalidCnpjException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception.
     */
    public InvalidCnpjException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link InvalidCnpjException} with the specified detail message and cause.
     *
     * @param message the detail message describing the reason for the exception.
     * @param cause the cause of the exception (can be retrieved later by {@link #getCause()}).
     */
    public InvalidCnpjException(String message, Throwable cause) {
        super(message, cause);
    }

}
