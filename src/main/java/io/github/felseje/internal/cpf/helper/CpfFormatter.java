package io.github.felseje.internal.cpf.helper;

import static io.github.felseje.internal.Constants.CPF_LENGTH;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CPF_ERROR;

import io.github.felseje.internal.util.StringUtils;

/**
 * Utility class responsible for formatting CPF (Cadastro de Pessoas Físicas) strings.
 *
 * <p>This class provides a method to convert a normalized CPF (a sequence of exactly
 * 11 numeric digits) into the standard Brazilian display format:
 * <code>XXX.XXX.XXX-XX</code>.</p>
 *
 * <p>The input must already be normalized, meaning it must contain only digits and
 * have a length of 11 characters. This class does not perform sanitization (e.g., removal of dots
 * or dashes).</p>
 *
 * <p><strong>Example:</strong></p>
 * <pre>{@code
 * String formattedCpf = CpfFormatter.format("01234567890"); // returns "012.345.678-90"
 * }</pre>
 *
 * <p>This is a utility class and must not be instantiated.</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfFormatter {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private CpfFormatter() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Formats a normalized CPF (11 digits) into the pattern XXX.XXX.XXX-XX.
   *
   * <p>This method assumes the input is a normalized CPF containing exactly 11 digits.</p>
   *
   * @param input normalized CPF (must be 11 digits)
   * @return formatted CPF string
   * @throws IllegalArgumentException if the input is null, blank, or does not contain exactly 11
   *                                  digits
   */
  public static String format(final String input) throws IllegalArgumentException {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CPF_ERROR);

    if (input.length() != CPF_LENGTH) {
      throw new IllegalArgumentException("CPF must be a normalized 11-digit string");
    }

    return "%s.%s.%s-%s".formatted(
        input.substring(0, 3),
        input.substring(3, 6),
        input.substring(6, 9),
        input.substring(9)
    );
  }

}
