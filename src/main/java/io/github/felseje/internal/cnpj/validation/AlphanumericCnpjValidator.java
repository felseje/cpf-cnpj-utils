package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.cnpj.CnpjType.ALPHANUMERIC;

import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

/**
 * Validator for alphanumeric CNPJs.
 * <p> This class validates CNPJ strings that may include both digits and uppercase letters. </p>
 * <p> It delegates normalization and structural checks to {@link AbstractCnpjValidator}, and uses
 * the standard {@link CnpjCheckDigitCalculator} for verifying check digits. </p>
 * <p>
 * Examples of valid input:
 * <ul>
 *   <li>{@code "12ABC34501DE35"}</li>
 *   <li>{@code "12.ABC.345/01DE-35"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class AlphanumericCnpjValidator extends AbstractCnpjValidator {

  /**
   * Validates the given CNPJ string as an alphanumeric CNPJ.
   *
   * <p>The input may be formatted or unformatted. It will be normalized before validation.</p>
   *
   * @param sanitized the CNPJ string to validate.
   * @return {@code true} if the CNPJ is structurally valid and has correct check digits;
   * {@code false} otherwise.
   */
  public static boolean isValid(String sanitized) {
    return AbstractCnpjValidator.isValid(
        sanitized,
        ALPHANUMERIC,
        CnpjCheckDigitCalculator::calculateCheckDigits
    );
  }

}
