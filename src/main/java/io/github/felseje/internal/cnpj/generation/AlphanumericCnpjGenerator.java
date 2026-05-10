package io.github.felseje.internal.cnpj.generation;

import static io.github.felseje.cnpj.CnpjType.ALPHANUMERIC;
import static io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator.calculateCheckDigits;

import io.github.felseje.cnpj.CnpjType;

/**
 * Generator for alphanumeric CNPJ values.
 * <p>
 * This implementation creates a CNPJ base composed of digits and uppercase letters and calculates
 * the corresponding check digits according to the {@link CnpjType#ALPHANUMERIC}.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class AlphanumericCnpjGenerator extends AbstractCnpjGenerator {

  /**
   * Constructs a new {@code AlphanumericGenerator}..
   */
  public AlphanumericCnpjGenerator() {
    super();
  }

  /**
   * Generates a random alphanumeric CNPJ string.
   * <p>
   * The method:
   * <ol>
   *     <li>Generates a 12-character alphanumeric base;</li>
   *     <li>Calculates check digits according to {@link CnpjType#ALPHANUMERIC};</li>
   *     <li>Returns the full CNPJ (base + check digits) unformatted.</li>
   * </ol>
   *
   * @return an unformatted alphanumeric CNPJ string.
   */
  public String generate() {
    final char[] base = generateBase("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    final char[] checkDigits = calculateCheckDigits(base, ALPHANUMERIC);

    return new String(base) + new String(checkDigits);
  }

}
