package io.github.felseje.internal.cnpj.validation;

import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

/**
 * Validator for numeric CNPJs.
 * <p> This class validates CNPJ strings composed exclusively of digits. </p>
 * <p> It relies on the shared validation logic in {@link AbstractValidator} and uses {@link CnpjCheckDigitCalculator} to verify check digits. </p>
 *
 * Examples of valid input:
 * <ul>
 *   <li>{@code "12345678000195"}</li>
 *   <li>{@code "12.345.678/0001-95"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class NumericValidator extends AbstractValidator {

    /**
     * Constructs a new {@code NumericValidator} with the given {@link Normalizer}.
     *
     * @param normalizer the normalizer used to preprocess input strings before validation
     * @throws IllegalArgumentException if {@code normalizer} is null
     */
    public NumericValidator(Normalizer normalizer) throws IllegalArgumentException {
        super(normalizer);
    }

    /**
     * Validates the given CNPJ string as a numeric CNPJ.
     *
     * <p>The input may be formatted or unformatted. It will be normalized before validation.</p>
     *
     * @param cnpj the CNPJ string to validate
     * @return {@code true} if the CNPJ is structurally valid and has correct check digits;
     *         {@code false} otherwise
     */
    public boolean isValid(String cnpj) {
        return super.isValid(cnpj, CnpjCheckDigitCalculator::calculateCheckDigits);
    }

}
