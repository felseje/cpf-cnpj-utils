package io.github.felseje.internal.cnpj.util;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.cnpj.util.Characters.appendChar;
import static io.github.felseje.internal.cnpj.util.Characters.digitToChar;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.InvalidCnpjBaseException;

/**
 * Utility class responsible for calculating the check digits (DVs) of a CNPJ base.
 * <p>
 * Implements the official CNPJ algorithm for generating the two check digits based on a
 * 12-character base. This class supports both numeric and alphanumeric CNPJ types, although
 * type-specific validation logic (e.g., allowed characters) is not yet enforced.
 * </p>
 *
 * <p>Usage example:
 * <pre>{@code
 *     char[] base = "123456780001".toCharArray();
 *     char[] checkDigits = CnpjCheckDigitCalculator.calculateCheckDigits(base, CnpjType.NUMERIC);
 *     // checkDigits -> {'9', '5'}
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjCheckDigitCalculator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private CnpjCheckDigitCalculator() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Calculates the check digit (DV) from a given character array using the CNPJ algorithm.
   *
   * @param digits a character array representing either the 12-digit base or the base + 1st check
   *               digit
   * @return the calculated check digit as an integer (0–9)
   */
  private static int calculateCheckDigit(final char[] digits) {
    final int[] calculationValueArray = new int[digits.length];

    for (int i = 0; i < digits.length; i++) {
      calculationValueArray[i] = digits[i] - '0'; // convert ASCII to int
    }

    int sum = 0;
    int weight = 2;

    for (int i = digits.length - 1; i >= 0; i--) {
      sum += calculationValueArray[i] * weight;
      weight = (weight == 9) ? 2 : weight + 1;
    }

    final int rest = sum % 11;

    return rest < 2 ? 0 : 11 - rest;
  }

  /**
   * Calculates both CNPJ check digits based on a 12-character base and CNPJ type.
   *
   * @param base a 12-character array representing the base of the CNPJ (without DVs)
   * @param type the {@link CnpjType} of the CNPJ (numeric or alphanumeric)
   * @return a character array containing the two check digits
   * @throws IllegalArgumentException if {@code type} is null
   * @throws InvalidCnpjBaseException if {@code base} is null or its length is not 12
   */
  public static char[] calculateCheckDigits(final char[] base, final CnpjType type)
      throws IllegalArgumentException, InvalidCnpjBaseException {
    if (type == null) {
      throw new IllegalArgumentException("CNPJ type must be not null");
    }

    if (!type.matchesBase(new String(base))) {
      throw new InvalidCnpjBaseException("CNPJ base must be valid");
    }

    final char primaryCheckDigit = digitToChar(calculateCheckDigit(base));
    final char[] baseWithPrimaryCheckDigit = appendChar(base, primaryCheckDigit);
    final char secondaryCheckDigit = digitToChar(calculateCheckDigit(baseWithPrimaryCheckDigit));

    return new char[]{primaryCheckDigit, secondaryCheckDigit};
  }

}
