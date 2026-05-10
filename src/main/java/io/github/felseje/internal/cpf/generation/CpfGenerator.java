package io.github.felseje.internal.cpf.generation;

import static io.github.felseje.internal.Constants.CPF_LENGTH;
import static io.github.felseje.internal.cpf.util.CpfCheckDigitCalculator.calculateCheckDigits;

import io.github.felseje.internal.cpf.util.Integers;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator class for creating valid CPF (Cadastro de Pessoas Físicas) numbers.
 * <p>
 * The generator creates a random 9-digit base, calculates the two check digits according to CPF
 * validation rules and return resulting CPF.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 *     CpfGenerator generator = new CpfGenerator();
 *     String cpf = generator.generate(); // e.g. "12345678909"
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfGenerator {

  /**
   * Constructs a new {@code CpfGenerator}.
   */
  public CpfGenerator() {
  }

  /**
   * Generates a random 9-digit base number for the CPF.
   *
   * @return an array of 9 random digits between 0 and 9
   */
  private int[] generateBase() {
    final int baseSize = CPF_LENGTH - 2;
    final var base = new int[baseSize];
    final var random = ThreadLocalRandom.current();

    for (int i = 0; i < baseSize; i++) {
      base[i] = random.nextInt(10);
    }

    return base;
  }

  /**
   * Generates a valid unformatted CPF string.
   *
   * @return an unformatted CPF string with 11 digits.
   */
  public String generate() {
    final var base = generateBase();
    final var checkDigits = calculateCheckDigits(base);

    return Integers.toString(base) + Integers.toString(checkDigits);
  }

}
