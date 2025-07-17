package io.github.felseje.cpf.exception;

import io.github.felseje.exception.InvalidDocumentException;

import java.io.Serial;

/**
 * Exception thrown to indicate that a given CPF (Cadastro de Pessoas Físicas)
 * is invalid according to format or validation rules.
 *
 * <p>This exception is typically thrown when a CPF fails normalization,
 * formatting, or check-digit validation.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 *     throw new InvalidCpfException("The CPF must be 11 digits long");
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class InvalidCpfException extends InvalidDocumentException {

    @Serial
    private static final long serialVersionUID = 4863245339813140973L;

    /**
     * Constructs a new {@link InvalidCpfException} with the specified detail message.
     *
     * @param message the detail message explaining why the CPF is considered invalid.
     */
    public InvalidCpfException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link InvalidCpfException} with the specified detail message and cause.
     *
     * @param message the detail message explaining why the CPF is considered invalid.
     * @param cause the cause of this exception (can be retrieved later by {@link #getCause()}).
     */
    public InvalidCpfException(String message, Throwable cause) {
        super(message, cause);
    }

}
