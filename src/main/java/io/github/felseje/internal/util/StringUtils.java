package io.github.felseje.internal.util;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class providing common String-related operations.
 *
 * <p>This class is not instantiable and all its methods are static.</p>
 *
 * <p>Primary functionality includes:
 * <ul>
 *   <li>Checking whether a string is null or blank</li>
 *   <li>Asserting that a string is not blank, throwing an {@link IllegalArgumentException} if it is</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 *     if (StringUtils.isNullOrBlank(input)) {
 *         throw new IllegalArgumentException("Input must not be blank");
 *     }
 *
 *     StringUtils.requireNonBlank(value, "Value must be present");
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class StringUtils {

    /**
     * Prevents instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
     */
    private StringUtils() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Checks if a given string is {@code null} or blank (empty or only whitespace).
     *
     * @param string the string to check
     * @return {@code true} if the string is {@code null} or blank; {@code false} otherwise
     */
    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    /**
     * Asserts that the provided string is not {@code null} or blank.
     *
     * <p>If the string is {@code null} or blank, an {@link IllegalArgumentException} is thrown
     * with the given error message. This method also validates that the {@code errorMessage}
     * itself is not null or blank.</p>
     *
     * @param string the string to validate
     * @param errorMessage the message to include in the exception if validation fails
     * @throws IllegalArgumentException if the string is {@code null} or blank,
     *                                  or if the {@code errorMessage} is {@code null} or blank
     */
    public static void requireNonBlank(String string, String errorMessage) throws IllegalArgumentException {
        if (isNullOrBlank(errorMessage)) {
            throw new IllegalArgumentException("The error message must not be null or blank");
        }
        if (isNullOrBlank(string)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
