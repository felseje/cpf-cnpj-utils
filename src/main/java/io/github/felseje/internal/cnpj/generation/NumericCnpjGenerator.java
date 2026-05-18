package io.github.felseje.internal.cnpj.generation;

import static io.github.felseje.cnpj.CnpjType.NUMERIC;
import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator.calculateCheckDigits;

import io.github.felseje.cnpj.CnpjType;

/**
 * Generator for numeric CNPJ values.
 * <p>
 * This implementation creates a CNPJ base composed only of digits (0–9) and calculates the
 * corresponding check digits according to the {@link CnpjType#NUMERIC}.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class NumericCnpjGenerator extends AbstractCnpjGenerator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private NumericCnpjGenerator() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Generates a random numeric CNPJ string.
   * <p>
   * The method:
   * <ol>
   *     <li>Generates a 12-digit numeric base;</li>
   *     <li>Calculates the check digits according to {@link CnpjType#NUMERIC};</li>
   *     <li>Returns the full CNPJ (base + check digits) unformatted.</li>
   * </ol>
   *
   * @return an unformatted numeric CNPJ string.
   */
  public static String generate() {
    final char[] base = generateBase("0123456789");
    final char[] checkDigits = calculateCheckDigits(base, NUMERIC);

    return new String(base) + new String(checkDigits);
  }

}
