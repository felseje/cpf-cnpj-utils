package io.github.felseje.internal.cnpj.validation;

import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

/**
 * Validator for alphanumeric CNPJs.
 * <p> This class validates CNPJ strings that may include both digits and uppercase letters. </p>
 * <p> It delegates normalization and structural checks to {@link AbstractValidator}, and uses the standard {@link CnpjCheckDigitCalculator} for verifying check digits. </p>
 *
 * Examples of valid input:
 * <ul>
 *   <li>{@code "12ABC34501DE35"}</li>
 *   <li>{@code "12.ABC.345/01DE-35"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class AlphanumericValidator extends AbstractValidator {

    /**
     * Constructs a new {@code AlphanumericValidator} with the given {@link Normalizer}.
     *
     * @param normalizer the normalizer to preprocess the input before validation.
     * @throws IllegalArgumentException if {@code normalizer} is null.
     */
    public AlphanumericValidator(Normalizer normalizer) throws IllegalArgumentException {
        super(normalizer);
    }

    /**
     * Validates the given CNPJ string as an alphanumeric CNPJ.
     *
     * <p>The input may be formatted or unformatted. It will be normalized before validation.</p>
     *
     * @param cnpj the CNPJ string to validate.
     * @return {@code true} if the CNPJ is structurally valid and has correct check digits; {@code false} otherwise.
     */
    public boolean isValid(String cnpj) {
        return super.isValid(cnpj, CnpjCheckDigitCalculator::calculateCheckDigits);
    }

}
