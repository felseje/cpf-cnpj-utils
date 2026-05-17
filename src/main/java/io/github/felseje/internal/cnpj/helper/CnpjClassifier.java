package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.cnpj.CnpjType.ALPHANUMERIC;
import static io.github.felseje.cnpj.CnpjType.NUMERIC;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;
import static io.github.felseje.internal.Constants.UNRECOGNIZED_CNPJ_TYPE_ERROR;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.UnrecognizedCnpjTypeException;
import io.github.felseje.internal.util.StringUtils;

/**
 * Utility responsible for classifying a normalized CNPJ string into its type.
 *
 * <p>This classifier determines whether a given normalized CNPJ belongs to
 * {@link CnpjType#NUMERIC} or {@link CnpjType#ALPHANUMERIC} based on its structure.</p>
 *
 * <p>Classification is performed using pattern matching on a fully normalized input,
 * without applying any transformation or validation of check digits.</p>
 *
 * <p>Examples of valid normalized inputs:
 * <ul>
 *   <li>{@code "12345678000195"} → {@link CnpjType#NUMERIC}</li>
 *   <li>{@code "12ABC34501DE35"} → {@link CnpjType#ALPHANUMERIC}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjClassifier {

  /**
   * Prevents instantiation of this class.
   *
   * @throws IllegalAccessException always thrown to indicate this class should not be
   *                                instantiated.
   */
  private CnpjClassifier() throws IllegalAccessException {
    throw new IllegalAccessException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Classifies a CNPJ string into its corresponding {@link CnpjType}.
   *
   * <p>This method attempts to determine whether the given input represents a
   * numeric or alphanumeric CNPJ, accepting both formatted and unformatted inputs.</p>
   *
   * <p>Examples:
   * <pre>{@code
   * classify("12345678000195");     // NUMERIC
   * classify("12.345.678/0001-95"); // NUMERIC
   * classify("12ABC34501DE35");     // ALPHANUMERIC
   * classify("12.ABC.345/01DE-35"); // ALPHANUMERIC
   * }</pre>
   *
   * @param input the CNPJ string to classify, formatted or unformatted
   * @return the detected {@link CnpjType}
   * @throws IllegalArgumentException      if the input is null or blank
   * @throws UnrecognizedCnpjTypeException if the input does not conform to any known CNPJ pattern
   */
  public static CnpjType classify(final String input)
      throws IllegalArgumentException, UnrecognizedCnpjTypeException {
    StringUtils.requireNotBlank(input, NULL_OR_BLANK_CNPJ_ERROR);

    if (NUMERIC.matches(input)) {
      return NUMERIC;
    }

    if (ALPHANUMERIC.matches(input)) {
      return ALPHANUMERIC;
    }

    throw new UnrecognizedCnpjTypeException(UNRECOGNIZED_CNPJ_TYPE_ERROR);
  }

}
