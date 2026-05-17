package io.github.felseje.internal.cpf.validation;

import static io.github.felseje.internal.Constants.CPF_LENGTH;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CPF_ERROR;

import io.github.felseje.cpf.exception.InvalidCpfException;
import io.github.felseje.internal.cpf.util.CpfCheckDigitCalculator;
import io.github.felseje.internal.cpf.util.Integers;
import io.github.felseje.internal.util.StringUtils;

/**
 * Utility class for validating Brazilian CPF (Cadastro de Pessoas Físicas).
 *
 * <p>A CPF is considered valid if it meets all the following criteria:
 * <ul>
 *   <li>Input is not null or blank</li>
 *   <li>Contains exactly 11 numeric digits</li>
 *   <li>Is not composed of a single repeated digit (e.g., "11111111111")</li>
 *   <li>Has correct check digits, calculated according to the official CPF algorithm</li>
 * </ul>
 *
 * <p>This validator expects normalized input without formatting characters.
 *
 * <p>Example usage:
 * <pre>{@code
 *     CpfValidator validator = new CpfValidator();
 *     boolean valid = validator.isValid("12345678909"); // true
 *     boolean invalid = validator.isValid("123.456.789-09"); // false
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfValidator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws IllegalAccessException always thrown to indicate this class should not be
   *                                instantiated.
   */
  private CpfValidator() throws IllegalAccessException {
    throw new IllegalAccessException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Validates a normalized CPF number.
   *
   * <p>A valid CPF must:
   * <ul>
   *   <li>not be null or blank;</li>
   *   <li>contain exactly 11 digits;</li>
   *   <li>not contain all identical digits;</li>
   *   <li>contain valid check digits.</li>
   * </ul>
   *
   * @param normalizedCpf the normalized CPF containing only numeric digits
   * @throws IllegalArgumentException if the CPF is null or blank
   * @throws InvalidCpfException      if the CPF is structurally or mathematically invalid
   */
  public static void validate(String normalizedCpf)
      throws IllegalArgumentException, InvalidCpfException {
    StringUtils.requireNotBlank(normalizedCpf, NULL_OR_BLANK_CPF_ERROR);

    if (normalizedCpf.length() != CPF_LENGTH) {
      throw new InvalidCpfException("CPF must contain exactly %d digits".formatted(CPF_LENGTH));
    }

    if (normalizedCpf.chars().distinct().count() == 1) {
      throw new InvalidCpfException("CPF cannot contain all identical digits");
    }

    final int[] base = Integers.toDigitArray(normalizedCpf.substring(0, (CPF_LENGTH - 2)));
    final int[] calculatedCheckDigits = CpfCheckDigitCalculator.calculateCheckDigits(base);

    final int firstCheckDigit = Integers.charToDigit(normalizedCpf.charAt(CPF_LENGTH - 2));
    final int secondCheckDigit = Integers.charToDigit(normalizedCpf.charAt(CPF_LENGTH - 1));

    final boolean isValid = calculatedCheckDigits[0] == firstCheckDigit
        && calculatedCheckDigits[1] == secondCheckDigit;

    if (!isValid) {
      throw new InvalidCpfException("CPF is not valid");
    }
  }

}
