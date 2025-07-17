package io.github.felseje.internal.cnpj.validation;

import io.github.felseje.cnpj.Cnpj;
import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.internal.util.StringUtils;
import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

import java.util.function.BiFunction;

/**
 * Abstract base class for CNPJ validators.
 *
 * <p> Provides common validation logic used by both {@link NumericValidator} and {@link AlphanumericValidator}. </p>
 * <p> This includes structural checks, basic normalization, digit uniformity detection, and check digit verification. </p>
 *
 * Validation steps performed:
 * <ul>
 *   <li>Rejects null or blank strings</li>
 *   <li>Removes non-alphanumeric characters</li>
 *   <li>Rejects values where all characters are the same (e.g., {@code "00000000000000"})</li>
 *   <li>Determines the CNPJ type using {@link CnpjType#detectFrom(String)}</li>
 *   <li>Verifies check digits using the supplied calculator function</li>
 * </ul>
 *
 * @see CnpjType
 * @see NumericValidator
 * @see AlphanumericValidator
 * @see CnpjCheckDigitCalculator
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public abstract sealed class AbstractValidator permits AlphanumericValidator, NumericValidator {

    private final Normalizer normalizer;

    /**
     * Constructs a validator with the specified {@link Normalizer}.
     *
     * @param normalizer the normalizer used to preprocess CNPJ strings
     * @throws IllegalArgumentException if {@code normalizer} is null
     */
    protected AbstractValidator(Normalizer normalizer) throws IllegalArgumentException {
        if (normalizer == null) {
            throw new IllegalArgumentException("The normalizer must not be null");
        }
        this.normalizer = normalizer;
    }

    /**
     * Validates a CNPJ string against expected structural rules and check digits.
     *
     * @param cnpj the raw CNPJ string (possibly formatted or containing noise)
     * @param checkDigitsCalculator a function that receives the base digits and {@link CnpjType},
     *                              and returns the expected two check digits
     * @return {@code true} if the CNPJ is valid; {@code false} otherwise
     */
    protected boolean isValid(final String cnpj, final BiFunction<char[], CnpjType, char[]> checkDigitsCalculator) {
        if (StringUtils.isNullOrBlank(cnpj)) {
            return false;
        }
        final var cleaned = normalizer.clear(cnpj);
        final var typeOptional = CnpjType.detectFrom(cleaned);
        if (typeOptional.isEmpty()) {
            return false;
        }
        if (cleaned.chars().distinct().count() == 1) {
            return false;
        }
        final var actualBase = cleaned.substring(0, Cnpj.LENGTH - 2).toCharArray();
        final var actualFirstCheckDigit = cleaned.charAt(Cnpj.LENGTH - 2);
        final var actualSecondCheckDigit = cleaned.charAt(Cnpj.LENGTH - 1);
        final var checkDigits = checkDigitsCalculator.apply(actualBase, typeOptional.get());
        return actualFirstCheckDigit == checkDigits[0]
                && actualSecondCheckDigit == checkDigits[1];
    }

}
