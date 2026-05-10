package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;
import static io.github.felseje.internal.Constants.UNRECOGNIZED_CNPJ_TYPE_ERROR;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.UnrecognizedCnpjTypeException;
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
   * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
   */
  private CnpjFormatter() {
    throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Formats a normalized CNPJ string into the standard CNPJ pattern.
   *
   * <p>This method assumes the input string is already normalized (i.e., contains
   * only valid characters and has sufficient length). No validation or transformation is
   * performed.</p>
   *
   * <p>Examples:
   * <pre>{@code
   *     format("12345678000195");   // returns "12.345.678/0001-95"
   *     format("12ABC34501DE35");   // returns "12.ABC.345/01DE-35"
   * }</pre>
   *
   * @param input the normalized CNPJ string (numeric or alphanumeric)
   * @return a formatted CNPJ string
   * @throws IllegalArgumentException      if {@code input} is null or blank
   * @throws UnrecognizedCnpjTypeException if the provided input does not match any CNPJ pattern
   */
  public static String format(String input) {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CNPJ_ERROR);

    if (CnpjType.detectFrom(input).isEmpty()) {
      throw new UnrecognizedCnpjTypeException(UNRECOGNIZED_CNPJ_TYPE_ERROR);
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
