package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.internal.Constants.CNPJ_LENGTH;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;
import io.github.felseje.internal.util.StringUtils;

import java.util.function.BiFunction;

/**
 * Abstract base class for CNPJ validators.
 *
 * <p> Provides common validation logic used by both {@link NumericCnpjValidator} and
 * {@link AlphanumericCnpjValidator}. </p>
 * <p> This includes structural checks, basic normalization, digit uniformity detection, and check
 * digit verification. </p>
 * <p>
 * Validation steps performed:
 * <ul>
 *   <li>Rejects null or blank strings</li>
 *   <li>Removes non-alphanumeric characters</li>
 *   <li>Rejects values where all characters are the same (e.g., {@code "00000000000000"})</li>
 *   <li>Determines the CNPJ type using {@link CnpjType#detectFrom(String)}</li>
 *   <li>Verifies check digits using the supplied calculator function</li>
 * </ul>
 *
 * @author felseje
 * @see CnpjType
 * @see NumericCnpjValidator
 * @see AlphanumericCnpjValidator
 * @see CnpjCheckDigitCalculator
 * @since 1.0.0-alpha
 */
public abstract sealed class AbstractCnpjValidator
    permits AlphanumericCnpjValidator, NumericCnpjValidator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  protected AbstractCnpjValidator() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Validates a CNPJ string against structural rules and check digits.
   *
   * <p>The provided CNPJ must be normalized, meaning it must contain exactly
   * 14 numeric digits. Formatting characters (such as '.', '/', and '-') are allowed and will be
   * ignored during validation.</p>
   *
   * @param cnpj                  the CNPJ string to validate; must represent a normalized CNPJ (14
   *                              digits), optionally formatted (e.g., "12.345.678/0001-95" or
   *                              "12345678000195")
   * @param checkDigitsCalculator a function that receives the base digits and {@link CnpjType}, and
   *                              returns the expected two check digits
   * @return {@code true} if the CNPJ is valid; {@code false} otherwise
   * @throws IllegalArgumentException if any required parameter is null or if the CNPJ does not
   *                                  contain a valid normalized representation
   */
  protected static boolean isValid(
      final String cnpj,
      final CnpjType type,
      final BiFunction<char[], CnpjType, char[]> checkDigitsCalculator
  ) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("CNPJ type cannot be null");
    }

    if (checkDigitsCalculator == null) {
      throw new IllegalArgumentException("CNPJ checkDigitsCalculator method cannot be null");
    }

    if (StringUtils.isNullOrBlank(cnpj)) {
      return false;
    }

    if (!type.matches(cnpj)) {
      return false;
    }

    if (cnpj.chars().distinct().count() == 1) {
      return false;
    }

    final char[] base = cnpj.substring(0, CNPJ_LENGTH - 2).toCharArray();
    final char firstCheckDigit = cnpj.charAt(CNPJ_LENGTH - 2);
    final char secondCheckDigit = cnpj.charAt(CNPJ_LENGTH - 1);
    final char[] calculatedCheckDigits = checkDigitsCalculator.apply(base, type);

    return firstCheckDigit == calculatedCheckDigits[0]
        && secondCheckDigit == calculatedCheckDigits[1];
  }

}
