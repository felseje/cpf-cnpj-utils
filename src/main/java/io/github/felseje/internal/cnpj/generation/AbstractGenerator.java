package io.github.felseje.internal.cnpj.generation;

import io.github.felseje.cnpj.Cnpj;
import io.github.felseje.internal.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract base class for generating CNPJ base values.
 * <p>
 * Provides a common method for generating the base portion of a CNPJ
 * (the first 12 characters) using a specified set of acceptable characters.
 * <br>
 * Subclasses must define the specific character set and representation
 * (e.g., alphanumeric or numeric).
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public abstract sealed class AbstractGenerator permits AlphanumericGenerator, NumericGenerator {

    /**
     * The size of the CNPJ base (first 12 digits).
     */
    protected static final int BASE_SIZE = Cnpj.LENGTH - 2;

    /**
     * Protected constructor to prevent direct instantiation.
     */
    protected AbstractGenerator() {
    }

    /**
     * Generates a random CNPJ base using the provided acceptable characters.
     *
     * @param acceptableCharacters the characters allowed in the generated base (must not be null or blank).
     * @return a character array representing the generated CNPJ base.
     * @throws IllegalArgumentException if {@code acceptableCharacters} is null or blank.
     */
    protected char[] generateBase(final String acceptableCharacters) throws IllegalArgumentException {
        StringUtils.requireNonBlank(acceptableCharacters, "The 'acceptableCharacters' must not be null or blank");
        final var base = new char[BASE_SIZE];
        final var random = ThreadLocalRandom.current();
        for (int i = 0; i < BASE_SIZE; i++) {
            base[i] = acceptableCharacters.charAt(random.nextInt(acceptableCharacters.length()));
        }
        return base;
    }

}
