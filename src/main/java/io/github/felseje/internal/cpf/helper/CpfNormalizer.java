package io.github.felseje.internal.cpf.helper;

import static io.github.felseje.internal.Constants.CPF_LENGTH;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CPF_ERROR;

import io.github.felseje.cpf.exception.InvalidCpfException;
import io.github.felseje.internal.util.StringUtils;
import java.util.regex.Pattern;

/**
 * Normalizer implementation for CPF (Cadastro de Pessoas Físicas) strings.
 * <p>
 * This class provides methods to clean raw CPF inputs, removing non-digit characters (such as dots,
 * dashes, and spaces) and ensuring the result contains exactly 11 digits.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 *     CpfNormalizer normalizer = new CpfNormalizer();
 *     String normalized = normalizer.normalize("123.456.789-09"); // returns "12345678909"
 * }</pre>
 *
 * <p>This class does <strong>not</strong> validate CPF check digits or authenticity -
 * it only normalizes the input and verifies its length.</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class CpfNormalizer {

  private static final Pattern WRONG_CPF_DIGITS_PATTERN = Pattern.compile("[^0-9]");

  /**
   * Constructs a new {@code CpfNormalizer}.
   */
  public CpfNormalizer() {
  }

  /**
   * Removes all non-digit characters from a given CPF string.
   *
   * @param input the CPF string to clean.
   * @return a string containing only numeric digits.
   * @throws IllegalArgumentException if the input is null or blank.
   */
  public String clear(String input) throws IllegalArgumentException {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CPF_ERROR);

    return WRONG_CPF_DIGITS_PATTERN.matcher(input).replaceAll("");
  }

  /**
   * Normalizes a raw CPF string by removing all non-digit characters and verifying its length.
   *
   * @param input the CPF string to normalize; may be formatted or unformatted.
   * @return a normalized CPF string containing exactly 11 digits.
   * @throws IllegalArgumentException if the input is null or blank.
   * @throws InvalidCpfException      if the normalized CPF does not contain exactly 11 digits.
   */
  public String normalize(String input) throws IllegalArgumentException, InvalidCpfException {
    final String cleaned = clear(input);

    if (cleaned.length() != CPF_LENGTH) {
      throw new InvalidCpfException("CPF must be 11 characters long");
    }

    return cleaned;
  }

}
