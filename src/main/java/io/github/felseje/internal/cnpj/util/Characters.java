package io.github.felseje.internal.cnpj.util;

import java.util.Arrays;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class for character operations related to digit handling.
 * <p>
 * This class is not meant to be instantiated.
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class Characters {

    /**
     * Prevents instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
     */
    private Characters() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Converts a digit (0–9) to its corresponding ASCII character.
     *
     * @param digit the digit to convert.
     * @return the character representing the digit.
     * @throws IllegalArgumentException if {@code digit} is not between 0 and 9.
     */
    public static char digitToChar(int digit) throws IllegalArgumentException {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }
        return (char) ('0' + digit);
    }

    /**
     * Appends a character to the end of a character array.
     *
     * @param array the original character array.
     * @param value the character to append.
     * @return a new character array containing all elements of {@code array}, followed by {@code value}.
     * @throws NullPointerException if {@code array} is {@code null}.
     */
    public static char[] appendChar(char[] array, char value) throws NullPointerException {
        final var newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = value;
        return newArray;
    }

}
