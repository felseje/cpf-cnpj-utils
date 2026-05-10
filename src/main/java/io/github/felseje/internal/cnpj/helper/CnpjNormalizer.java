package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

import io.github.felseje.cnpj.exception.InvalidCnpjException;
import io.github.felseje.internal.Constants;
import io.github.felseje.internal.util.StringUtils;
import java.util.regex.Pattern;

/**
 * Normalizer implementation for processing CNPJ strings.
 *
 * <p> This class removes formatting symbols and standardizes CNPJ input into a 14-character
 * uppercase alphanumeric string, suitable for validation, classification, or storage. </p>
 * <p>
 * Examples:
 * <ul>
 *   <li>Formatted numeric: {@code "12.345.678/0001-95"}</li>
 *   <li>Formatted alphanumeric: {@code "12.ABC.345/01DE-35"}</li>
 *   <li>Raw numeric: {@code "12345678000195"}</li>
 *   <li>Raw alphanumeric: {@code "12abc34501de35"}</li>
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
   * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
   */
  private CnpjNormalizer() {
    throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Removes all non-alphanumeric characters from the input CNPJ string.
   *
   * <p>This method keeps only letters and digits, removing formatting characters
   * such as dots, slashes, dashes, and whitespace.</p>
   *
   * @param rawCnpj the CNPJ string to sanitize; must not be null or blank
   * @return a string containing only alphanumeric characters
   * @throws IllegalArgumentException if the input is null or blank
   */
  public static String sanitize(String rawCnpj) throws IllegalArgumentException {
    StringUtils.requireNotBlank(rawCnpj, Constants.NULL_OR_BLANK_CNPJ_ERROR);

    return INVALID_CNPJ_CHARACTERS.matcher(rawCnpj).replaceAll("");
  }

  /**
   * Normalizes a raw CNPJ string by removing formatting symbols and converting it to uppercase.
   *
   * <p> The result will always be a 14-character string composed of digits and/or uppercase
   * letters. </p>
   * <p> This method does not validate the check digits (DVs), only the structure and length. </p>
   * <p>
   * Examples:
   * <pre>{@code
   * normalize("12.345.678/0001-95"); // "12345678000195"
   * normalize("12.abc.345/01de-35"); // "12ABC34501DE35"
   * }</pre>
   *
   * @param rawCnpj the raw CNPJ string, formatted or unformatted
   * @return the normalized CNPJ string
   * @throws IllegalArgumentException if the input is null or blank
   * @throws InvalidCnpjException     if the normalized value does not have exactly 14 characters
   */
  public static String normalize(String rawCnpj)
      throws IllegalArgumentException, InvalidCnpjException {
    final String sanitized = sanitize(rawCnpj).toUpperCase();

    if (sanitized.length() != Constants.CNPJ_LENGTH) {
      throw new InvalidCnpjException("CNPJ must be 14 characters long");
    }

    return sanitized;
  }

}
