package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.internal.Constants.CNPJ_LENGTH;

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
   * Validates a CNPJ string against expected structural rules and check digits.
   *
   * @param sanitized             the sanitized CNPJ string
   * @param checkDigitsCalculator a function that receives the base digits and {@link CnpjType}, and
   *                              returns the expected two check digits
   * @return {@code true} if the CNPJ is valid; {@code false} otherwise
   * @throws IllegalArgumentException if any required parameter is null
   */
  protected static boolean isValid(
      final String sanitized,
      final CnpjType type,
      final BiFunction<char[], CnpjType, char[]> checkDigitsCalculator
  ) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("type cannot be null");
    }

    if (checkDigitsCalculator == null) {
      throw new IllegalArgumentException("checkDigitsCalculator cannot be null");
    }

    if (StringUtils.isNullOrBlank(sanitized)) {
      return false;
    }

    if (!type.matches(sanitized)) {
      return false;
    }

    if (sanitized.chars().distinct().count() == 1) {
      return false;
    }

    final char[] base = sanitized.substring(0, CNPJ_LENGTH - 2).toCharArray();
    final char firstCheckDigit = sanitized.charAt(CNPJ_LENGTH - 2);
    final char secondCheckDigit = sanitized.charAt(CNPJ_LENGTH - 1);
    final char[] calculatedCheckDigits = checkDigitsCalculator.apply(base, type);

    return firstCheckDigit == calculatedCheckDigits[0]
        && secondCheckDigit == calculatedCheckDigits[1];
  }

}
