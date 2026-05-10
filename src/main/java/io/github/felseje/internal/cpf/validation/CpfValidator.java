package io.github.felseje.internal.cpf.validation;

import static io.github.felseje.internal.Constants.CPF_LENGTH;

import io.github.felseje.internal.cpf.util.CpfCheckDigitCalculator;
import io.github.felseje.internal.cpf.util.Integers;
import io.github.felseje.internal.util.StringUtils;

/**
 * Utility class for validating Brazilian CPF (Cadastro de Pessoas Físicas) numbers.
 *
 * <p>A CPF is considered valid if it meets all the following criteria:
 * <ul>
 *   <li>Input is not null or blank</li>
 *   <li>Contains exactly 11 numeric digits</li>
 *   <li>Is not composed of a single repeated digit (e.g., "11111111111")</li>
 *   <li>Has correct check digits, calculated according to the official CPF algorithm</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 *     CpfValidator validator = new CpfValidator();
 *     boolean valid = validator.isValid("123.456.789-09"); // true
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfValidator {

  /**
   * Constructs a new instance of {@code CpfValidator}.
   */
  public CpfValidator() {
  }

  /**
   * Validates a CPF string.
   *
   * <p>This method cleans and normalizes the input, verifies its structure,
   * ensures it isn't composed of repeated digits, and checks if the two final digits (verifiers)
   * match the ones calculated via the official algorithm.</p>
   *
   * @param cpf the CPF string to validate (formatted or unformatted).
   * @return {@code true} if the CPF is valid; {@code false} otherwise.
   */
  public boolean isValid(String cpf) {
    if (StringUtils.isNullOrBlank(cpf)) {
      return false;
    }

    if (cpf.length() != 11) {
      return false;
    }

    if (cpf.chars().distinct().count() == 1) {
      return false;
    }

    final int[] base = Integers.toDigitArray(cpf.substring(0, (CPF_LENGTH - 2)));
    final int[] calculatedCheckDigits = CpfCheckDigitCalculator.calculateCheckDigits(base);
    final int firstCheckDigit = Integers.charToDigit(cpf.charAt(CPF_LENGTH - 2));
    final int secondCheckDigit = Integers.charToDigit(cpf.charAt(CPF_LENGTH - 1));

    return calculatedCheckDigits[0] == firstCheckDigit
        && calculatedCheckDigits[1] == secondCheckDigit;
  }

}
