package io.github.felseje.internal.core;

/**
 * Represents a normalizer that provides operations to sanitize and normalize input strings.
 * <p>
 * Implementations of this interface typically remove unwanted characters
 * and standardize the format of input data such as document identifiers.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public interface Normalizer {

    /**
     * Removes non-essential characters from the given input string,
     * such as punctuation, whitespace, or formatting symbols.
     *
     * @param input the raw input string to be cleared
     * @return the cleaned string containing only the relevant characters
     */
    String clear(String input);

    /**
     * Normalizes the given input string into a standardized form.
     * <p>
     * This may include trimming, converting to a uniform case,
     * or applying formatting rules specific to the application's domain.
     * </p>
     *
     * @param input the raw input string to normalize
     * @return the normalized version of the input
     */
    String normalize(String input);

}
