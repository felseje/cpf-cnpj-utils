package io.github.felseje.cpf.exception;

import io.github.felseje.exception.InvalidDocumentBaseException;

import java.io.Serial;

/**
 * Exception thrown to indicate that a provided CPF base is invalid.
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidCpfBaseException extends InvalidDocumentBaseException {

    @Serial
    private static final long serialVersionUID = 8234917582043856274L;

    /**
     * Constructs a new {@link InvalidCpfBaseException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public InvalidCpfBaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link InvalidCpfBaseException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public InvalidCpfBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
