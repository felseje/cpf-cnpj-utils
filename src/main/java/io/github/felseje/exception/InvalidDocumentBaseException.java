package io.github.felseje.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate that the provided document base is invalid.
 *
 * <p> This exception typically signals that the input used to build a document (e.g., CPF or CNPJ) does not meet the expected format, length, or digit requirements. </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidDocumentBaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 731067372964718659L;

    /**
     * Constructs a new {@code InvalidDocumentBaseException} with the specified detail message.
     *
     * @param message the detail message explaining why the document base is invalid.
     */
    public InvalidDocumentBaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code InvalidDocumentBaseException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of this exception (can be retrieved later by {@link #getCause()}).
     */
    public InvalidDocumentBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
