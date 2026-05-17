package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.internal.Constants.CNPJ_LENGTH;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;

import io.github.felseje.internal.util.StringUtils;

/**
 * Utility class for formatting CNPJ strings into the standard pattern.
 *
 * <p> This formatter supports both numeric and alphanumeric CNPJ values and
 * applies formatting to produce a string in the pattern {@code "##.###.###/####-##"}.</p>
 *
 * <p><strong>Note:</strong> This method does not perform normalization or validation
 * of the input. It assumes the provided string is already properly normalized and has a valid
 * length.</p>
 *
 * <p>Examples:
 * <ul>
 *   <li>{@code "12345678000195"} → {@code "12.345.678/0001-95"}</li>
 *   <li>{@code "12ABC34501DE35"} → {@code "12.ABC.345/01DE-35"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjFormatter {

  /**
   * Prevents instantiation of this class.
   *
   * @throws IllegalAccessException always thrown to indicate this class should not be
   *                                instantiated.
   */
  private CnpjFormatter() throws IllegalAccessException {
    throw new IllegalAccessException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Formats a normalized CNPJ string into the standard presentation pattern.
   *
   * <p>This method applies only formatting rules and does not perform normalization
   * or validation of CNPJ structure or check digits.</p>
   *
   * <p>The input must be a normalized 14-character CNPJ string.</p>
   *
   * <p>Examples:
   * <pre>{@code
   * format("12345678000195"); // "12.345.678/0001-95"
   * format("12ABC34501DE35"); // "12.ABC.345/01DE-35"
   * }</pre>
   *
   * @param input the normalized CNPJ string (exactly 14 characters)
   * @return the formatted CNPJ in standard pattern
   * @throws IllegalArgumentException if input is null, blank, or not exactly 14 characters long
   */
  public static String format(final String input) throws IllegalArgumentException {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CNPJ_ERROR);

    if (input.length() != CNPJ_LENGTH) {
      throw new IllegalArgumentException("CNPJ must be a normalized 14-character string");
    }

    //noinspection StringBufferReplaceableByString
    return new StringBuilder(18).append(input, 0, 2)
        .append('.').append(input, 2, 5)
        .append('.').append(input, 5, 8)
        .append('/').append(input, 8, 12)
        .append('-').append(input, 12, input.length())
        .toString();
  }

}
