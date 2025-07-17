package io.github.felseje.cnpj.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that a CNPJ type could not be recognized.
 * <p>
 * This exception is typically used when a classifier or parser encounters
 * a document that does not match any known {@code CnpjType}.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class UnrecognizedCnpjTypeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6789123456701234567L;

    /**
     * Constructs a new {@link UnrecognizedCnpjTypeException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public UnrecognizedCnpjTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UnrecognizedCnpjTypeException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the cause of this exception (which can be retrieved later by {@link #getCause()})
     */
    public UnrecognizedCnpjTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
