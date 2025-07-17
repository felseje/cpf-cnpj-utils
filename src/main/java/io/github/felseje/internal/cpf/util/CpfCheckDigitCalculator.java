package io.github.felseje.internal.cpf.util;

import io.github.felseje.cpf.exception.InvalidCpfBaseException;

import java.util.regex.Pattern;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class for calculating CPF check digits.
 *
 * <p>This class is non-instantiable and provides a static method to compute the two
 * check digits based on a 9-digit CPF base.</p>
 *
 * <p>It follows the official CPF validation algorithm defined by Receita Federal (Brazilian IRS).</p>
 *
 * <p>Usage example:
 * <pre>{@code
 *     int[] base = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
 *     int[] checkDigits = CpfCheckDigitCalculator.calculateCheckDigits(base);
 *     // checkDigits[0] = first check digit
 *     // checkDigits[1] = second check digit
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfCheckDigitCalculator {

    private static final Pattern WRONG_CPF_BASE = Pattern.compile("[^0-9]");
    private static final int[] WEIGHTS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    /**
     * Prevents instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate this class should not be instantiated
     */
    private CpfCheckDigitCalculator() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Calculates a single check digit based on a given digit array.
     * The calculation multiplies each digit by its respective weight, computes the sum,
     * and applies the modulo 11 rule.
     *
     * @param digits the digit array to calculate the check digit from
     * @return the calculated check digit (0–9)
     */
    private static int calculateCheckDigit(final int[] digits) {
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i] * WEIGHTS[digits.length - i];
        }
        final int rest = sum % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    /**
     * Calculates both check digits (verifiers) for a given CPF base.
     *
     * @param base the base CPF digits (expected length: 9)
     * @return an array of 2 integers: [first check digit, second check digit]
     * @throws InvalidCpfBaseException if the base is {@code null}, malformed, or contains non-digit characters
     */
    public static int[] calculateCheckDigits(int[] base) throws InvalidCpfBaseException {
        if (base == null || WRONG_CPF_BASE.matcher(Integers.toString(base)).matches()) {
            throw new InvalidCpfBaseException("The CPF base informed is invalid");
        }

        final var primaryCheckDigit = calculateCheckDigit(base);
        final var baseWithPrimaryCheckDigit = Integers.appendInt(base, primaryCheckDigit);
        final var secondaryCheckDigit = calculateCheckDigit(baseWithPrimaryCheckDigit);
        return new int[]{primaryCheckDigit, secondaryCheckDigit};
    }

}
