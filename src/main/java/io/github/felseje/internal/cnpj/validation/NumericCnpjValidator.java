package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.cnpj.CnpjType.NUMERIC;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

/**
 * Validator for numeric CNPJs.
 * <p> This class validates CNPJ strings composed exclusively of digits. </p>
 * <p> It relies on the shared validation logic in {@link AbstractCnpjValidator} and uses
 * {@link CnpjCheckDigitCalculator} to verify check digits. </p>
 * <p>
 * Examples of valid input:
 * <ul>
 *   <li>{@code "12345678000195"}</li>
 *   <li>{@code "12.345.678/0001-95"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class NumericCnpjValidator extends AbstractCnpjValidator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private NumericCnpjValidator() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Validates the given CNPJ string as a numeric CNPJ.
   *
   * <p>The input may be formatted or unformatted. It will be normalized before validation.</p>
   *
   * @param sanitized the raw CNPJ string to validate
   * @return {@code true} if the CNPJ is structurally valid and has correct check digits;
   * {@code false} otherwise
   */
  public static boolean isValid(final String sanitized) {
    return AbstractCnpjValidator.isValid(
        sanitized,
        NUMERIC,
        CnpjCheckDigitCalculator::calculateCheckDigits
    );
  }

}
