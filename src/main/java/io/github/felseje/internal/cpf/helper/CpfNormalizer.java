package io.github.felseje.internal.cpf.helper;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

import java.util.regex.Pattern;

/**
 * Utility class responsible for normalizing CPF (Cadastro de Pessoas Físicas) values.
 *
 * <p>This class provides functionality to convert formatted CPF strings into a
 * standardized numeric-only representation suitable for validation and processing.
 *
 * <p>Example transformations:
 * <ul>
 *   <li>"123.456.789-09" → "12345678909"</li>
 *   <li>"123 456 789 09" → "12345678909"</li>
 * </ul>
 *
 * <p>This class cannot be instantiated.
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfNormalizer {

  private static final Pattern WRONG_CPF_DIGITS_PATTERN = Pattern.compile("[^0-9]");

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private CpfNormalizer() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Normalizes a CPF input by removing all non-digit characters.
   *
   * <p>This method is intended to convert formatted CPF values
   * (e.g. "123.456.789-09") into a raw numeric representation (e.g. "12345678909") suitable for
   * validation.
   *
   * @param input the CPF string to normalize, formatted or unformatted
   * @return the normalized CPF containing only digits
   * @throws IllegalArgumentException if the input is null
   */
  public static String normalize(final String input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("CPF must not be null");
    }

    return WRONG_CPF_DIGITS_PATTERN.matcher(input).replaceAll("");
  }

}
