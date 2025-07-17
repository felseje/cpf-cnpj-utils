package io.github.felseje.internal.cpf.validation;

import io.github.felseje.cpf.Cpf;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.internal.cpf.util.CpfCheckDigitCalculator;
import io.github.felseje.internal.cpf.util.Integers;
import io.github.felseje.internal.util.StringUtils;

/**
 * Validates Brazilian CPF numbers by checking structure, formatting, and verifying digits.
 *
 * <p>This class uses a {@link Normalizer} to clean and normalize input strings,
 * ensuring consistent validation regardless of formatting (e.g., with or without separators).</p>
 *
 * <p>Validation checks include:
 * <ul>
 *   <li>Non-null and non-blank input</li>
 *   <li>Correct length (11 digits)</li>
 *   <li>Not composed of the same repeated digit</li>
 *   <li>Correct check digits (verifiers)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 *     Normalizer normalizer = new CpfNormalizer();
 *     CpfValidator validator = new CpfValidator(normalizer);
 *     boolean valid = validator.isValid("123.456.789-09");
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfValidator {

    private final Normalizer normalizer;

    /**
     * Constructs a {@code CpfValidator} with the given {@link Normalizer}.
     *
     * @param normalizer the normalizer to use for input preprocessing.
     * @throws IllegalArgumentException if {@code normalizer} is {@code null}.
     */
    public CpfValidator(Normalizer normalizer) throws IllegalArgumentException {
        if (normalizer == null) {
            throw new IllegalArgumentException("The normalizer must not be null");
        }
        this.normalizer = normalizer;
    }

    /**
     * Validates a CPF string.
     *
     * <p>This method cleans and normalizes the input, verifies its structure,
     * ensures it isn't composed of repeated digits, and checks if the two final
     * digits (verifiers) match the ones calculated via the official algorithm.</p>
     *
     * @param cpf the CPF string to validate (formatted or unformatted).
     * @return {@code true} if the CPF is valid; {@code false} otherwise.
     */
    public boolean isValid(String cpf) {
        if (StringUtils.isNullOrBlank(cpf)) {
            return false;
        }
        final var cleaned = normalizer.clear(cpf);
        if (cleaned.length() != Cpf.LENGTH) {
            return false;
        }
        if (cleaned.chars().distinct().count() == 1) {
            return false;
        }
        final var baseDigits = Integers.toDigitArray(cleaned.substring(0, (Cpf.LENGTH - 2)));
        final var calculatedCheckDigits = CpfCheckDigitCalculator.calculateCheckDigits(baseDigits);
        final var actualFirstCheckDigit = Integers.charToDigit(cleaned.charAt(Cpf.LENGTH - 2));
        final var actualSecondCheckDigit = Integers.charToDigit(cleaned.charAt(Cpf.LENGTH - 1));
        return calculatedCheckDigits[0] == actualFirstCheckDigit
                && calculatedCheckDigits[1] == actualSecondCheckDigit;
    }

}
