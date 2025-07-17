package io.github.felseje.internal.core;

/**
 * Represents a formatter that applies a specific format to a given document string.
 * <p>
 * Implementations of this interface should transform the raw document input
 * into a standardized or human-readable format.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public interface Formatter {

    /**
     * Formats the given document string.
     *
     * @param document the document to format, typically a raw identifier like a CNPJ.
     * @return the formatted document string.
     */
    String format(String document);

}
