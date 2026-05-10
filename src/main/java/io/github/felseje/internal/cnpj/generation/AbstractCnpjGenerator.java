package io.github.felseje.internal.cnpj.generation;

import static io.github.felseje.internal.Constants.CNPJ_LENGTH;
import static io.github.felseje.internal.util.StringUtils.requireNotBlank;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract base class for generating CNPJ base values.
 * <p>
 * Provides a common method for generating the base portion of a CNPJ (the first 12 characters)
 * using a specified set of acceptable characters.
 * <br>
 * Subclasses must define the specific character set and representation (e.g., alphanumeric or
 * numeric).
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public abstract sealed class AbstractCnpjGenerator
    permits AlphanumericCnpjGenerator, NumericCnpjGenerator {

  /**
   * Protected constructor to prevent direct instantiation.
   */
  protected AbstractCnpjGenerator() {
  }

  /**
   * Generates a random CNPJ base using the provided acceptable characters.
   *
   * @param acceptableCharacters the characters allowed in the generated base (must not be null or
   *                             blank).
   * @return a character array representing the generated CNPJ base.
   * @throws IllegalArgumentException if {@code acceptableCharacters} is null or blank.
   */
  protected char[] generateBase(final String acceptableCharacters) throws IllegalArgumentException {
    requireNotBlank(acceptableCharacters, "The 'acceptableCharacters' must not be null or blank");
    final int baseSize = CNPJ_LENGTH - 2;
    final var base = new char[baseSize];
    final var random = ThreadLocalRandom.current();
    for (int i = 0; i < baseSize; i++) {
      base[i] = acceptableCharacters.charAt(random.nextInt(acceptableCharacters.length()));
    }
    return base;
  }

}
