package io.github.felseje.cnpj.exception;

import io.github.felseje.exception.InvalidDocumentBaseException;

import java.io.Serial;

/**
 * Exception thrown to indicate that a provided CNPJ base is invalid.
 *
 * <p> This exception signals that the provided CNPJ base does not meet the expected criteria such as format, length, or content. </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidCnpjBaseException extends InvalidDocumentBaseException {

    @Serial
    private static final long serialVersionUID = 6723985472398574235L;

    /**
     * Constructs a new {@link InvalidCnpjBaseException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public InvalidCnpjBaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link InvalidCnpjBaseException} with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception.
     * @param cause   the cause of the exception (can be retrieved later by {@link #getCause()}).
     */
    public InvalidCnpjBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
