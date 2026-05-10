package io.github.felseje.internal.cnpj.helper;

import static io.github.felseje.cnpj.CnpjType.ALPHANUMERIC;
import static io.github.felseje.cnpj.CnpjType.NUMERIC;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;
import static io.github.felseje.internal.Constants.UNRECOGNIZED_CNPJ_TYPE_ERROR;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.UnrecognizedCnpjTypeException;
import io.github.felseje.internal.core.Classifier;
import io.github.felseje.internal.util.StringUtils;

/**
 * Implementation of {@link Classifier} for identifying the type of normalized CNPJ string.
 *
 * <p> This classifier determines whether the input CNPJ is {@link CnpjType#NUMERIC} or
 * {@link CnpjType#ALPHANUMERIC} based on its structure. </p>
 * <p> It uses regular expressions to match a fully normalized input string. </p>
 * <p>
 * Example accepted inputs:
 * <ul>
 *   <li>{@code "12345678000195"} → NUMERIC</li>
 *   <li>{@code "12ABC34501DE35"} → ALPHANUMERIC</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjClassifier {

  /**
   * Prevents instantiation of this class.
   *
   * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
   */
  private CnpjClassifier() {
    throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Attempts to classify the given CNPJ string into a {@link CnpjType}.
   *
   * <p>
   * Examples:
   * <pre>{@code
   * classify("12345678000195");       // returns NUMERIC
   * classify("12.345.678/0001-95");   // returns NUMERIC
   * classify("12ABC34501DE35");       // returns ALPHANUMERIC
   * classify("12.ABC.345/01DE-35");   // returns ALPHANUMERIC
   * }</pre>
   *
   * @param input the CNPJ string to classify
   * @return a {@link CnpjType} representing the classification result
   * @throws IllegalArgumentException      if the input is null or blank
   * @throws UnrecognizedCnpjTypeException if the input does not match any known CNPJ format
   */
  public static CnpjType classify(String input)
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
