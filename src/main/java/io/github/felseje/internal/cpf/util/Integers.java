package io.github.felseje.internal.cpf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class for operations involving integers, especially useful for CPF-related digit manipulation.
 * <p>
 * Provides methods for:
 * <ul>
 *   <li>Appending integers to arrays</li>
 *   <li>Converting integer arrays to strings</li>
 *   <li>Parsing digit characters and digit-only strings</li>
 * </ul>
 *
 * <p>This class is final and cannot be instantiated.</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class Integers {

    // Using explicit [^0-9] instead of \D to restrict matching strictly to ASCII digits (0–9)
    private static final Pattern NON_DECIMAL_DIGIT_PATTERN = Pattern.compile("[^0-9]");

    /**
     * Prevents instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate this class should not be instantiated
     */
    private Integers() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Appends an int to the end of an integer array.
     *
     * @param array the original integer array.
     * @param value the integer to append.
     * @return a new integer array containing all elements of {@code array}, followed by {@code value}.
     * @throws NullPointerException if {@code array} is {@code null}.
     */
    public static int[] appendInt(int[] array, int value) throws NullPointerException {
        final var newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Converts an array of integers into a single concatenated {@link String}.
     * <p>
     * Each element in the array is appended directly in sequence, without separators.
     * For example, an array {@code {1, 23, 4}} will be converted to {@code "1234"}.
     *
     * @param intArray the array of integers to convert.
     * @return a {@code String} representation of the array with all elements concatenated in order.
     * @throws NullPointerException if {@code intArray} is {@code null}.
     */
    public static String toString(int[] intArray) {
        Objects.requireNonNull(intArray, "The integer array must not be null");
        final var stringBuilder = new StringBuilder(intArray.length + 5);
        for (int i : intArray) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    /**
     * Converts a numeric character to its corresponding integer digit.
     *
     * <p>This method validates that the input character is a valid digit ('0' to '9').
     * If the character is not a valid digit, it throws an IllegalArgumentException.
     *
     * @param numericCharacter the character to convert
     * @return the integer value of the character
     * @throws IllegalArgumentException if the character is not a valid digit (0-9)
     */
    public static int charToDigit(char numericCharacter) {
        if (!Character.isDigit(numericCharacter)) {
            throw new IllegalArgumentException("Invalid character: '" + numericCharacter + "'. Expected a digit between 0 and 9.");
        }
        return Character.digit(numericCharacter, 10);
    }

    /**
     * Converts a string of ASCII digits (0–9) into an array of integer digits.
     *
     * @param input the string to convert.
     * @return an array of integers representing each digit.
     * @throws NullPointerException     if the input is {@code null}.
     * @throws IllegalArgumentException if the input contains any non-ASCII digit characters.
     */
    public static int[] toDigitArray(String input) {
        Objects.requireNonNull(input, "The input string cannot be null");
        if (NON_DECIMAL_DIGIT_PATTERN.matcher(input).find()) {
            throw new IllegalArgumentException("The input string must contain only digits from 0 to 9");
        }
        final var digits = new ArrayList<Integer>(input.length());
        for (char c : input.toCharArray()) {
            digits.add(charToDigit(c));
        }
        return digits.stream().mapToInt(Integer::intValue).toArray();
    }

}
