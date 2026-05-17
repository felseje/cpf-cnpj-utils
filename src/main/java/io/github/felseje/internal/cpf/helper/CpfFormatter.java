package io.github.felseje.internal.cpf.helper;

import static io.github.felseje.internal.Constants.CPF_LENGTH;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CPF_ERROR;

import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.internal.util.StringUtils;

/**
 * Formatter implementation for formatting CPF (Cadastro de Pessoas Físicas) strings.
 *
 * <p> This class formats a raw CPF string into the standard Brazilian CPF format:
 * <code>XXX.XXX.XXX-XX</code>. </p>
 * <p> It relies on the {@link Normalizer} to normalize the input before formatting. </p>
 * <p>
 * Example usage:
 * <pre>{@code
 *     CpfFormatter formatter = new CpfFormatter();
 *     String formattedCpf = formatter.format("01234567890");  // returns "012.345.678-90"
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfFormatter {

  /**
   * Creates a new instance of {@code CpfFormatter}.
   *
   * @throws IllegalArgumentException if the parameter normalizer is null.
   */
  private CpfFormatter() throws IllegalAccessException {
    throw new IllegalAccessException(NOT_ALLOWED_INSTANTIATION_ERROR);
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
