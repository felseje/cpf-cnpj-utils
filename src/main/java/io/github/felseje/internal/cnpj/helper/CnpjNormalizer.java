package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;

import io.github.felseje.internal.util.StringUtils;
import java.util.regex.Pattern;

/**
 * Utility class responsible for normalizing CNPJ strings.
 *
 * <p>This class removes all non-alphanumeric characters and converts the result
 * to uppercase, producing a standardized representation suitable for validation, classification, or
 * storage.</p>
 *
 * <p>Supported inputs include both formatted and unformatted CNPJ values, such as:
 * <ul>
 *   <li>{@code "12.345.678/0001-95"} (formatted numeric)</li>
 *   <li>{@code "12.ABC.345/01DE-35"} (formatted alphanumeric)</li>
 *   <li>{@code "12345678000195"} (raw numeric)</li>
 *   <li>{@code "12abc34501de35"} (raw alphanumeric)</li>
 * </ul>
 *
 * <p>The normalization process is purely syntactic and does not perform:
 * <ul>
 *   <li>validation of check digits (DVs)</li>
 *   <li>length validation</li>
 *   <li>business rule enforcement</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjNormalizer {

  /**
   * Matches any character that is not alphanumeric in a CNPJ string.
   */
  private static final Pattern INVALID_CNPJ_CHARACTERS = Pattern.compile("[^a-zA-Z0-9]");

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private CnpjNormalizer() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Normalizes a raw CNPJ string by removing formatting characters and converting it to uppercase.
   *
   * <p>The result contains only alphanumeric characters (digits and uppercase letters).
   * No structural or semantic validation is performed.</p>
   *
   * <p>This method does not validate check digits (DVs) or enforce length constraints,
   * only performs syntactic normalization.</p>
   *
   * <p>Examples:
   * <pre>{@code
   * normalize("12.345.678/0001-95"); // "12345678000195"
   * normalize("12.abc.345/01de-35"); // "12ABC34501DE35"
   * }</pre>
   *
   * @param input the raw CNPJ string, formatted or unformatted
   * @return the normalized CNPJ string
   * @throws IllegalArgumentException if the input is null or blank
   */
  public static String normalize(final String input) throws IllegalArgumentException {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CNPJ_ERROR);

    return INVALID_CNPJ_CHARACTERS.matcher(input).replaceAll("").toUpperCase();
  }

}
