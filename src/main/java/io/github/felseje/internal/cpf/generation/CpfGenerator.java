package io.github.felseje.internal.cpf.generation;

import io.github.felseje.internal.core.Formatter;
import io.github.felseje.internal.cpf.util.CpfCheckDigitCalculator;
import io.github.felseje.internal.cpf.util.Integers;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator class for creating valid CPF (Cadastro de Pessoas Físicas) numbers.
 * <p>
 * The generator creates a random 9-digit base, calculates the two check digits
 * according to CPF validation rules, and formats the resulting CPF using the provided {@link Formatter}.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 *     Formatter formatter = new CpfFormatter(); // assume a formatter implementation
 *     CpfGenerator generator = new CpfGenerator(formatter);
 *     String cpf = generator.generate(); // e.g. "123.456.789-09"
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfGenerator {

    private static final int BASE_SIZE = 9;
    private final Formatter formatter;

    /**
     * Constructs a new {@code CpfGenerator} with the given formatter.
     *
     * @param formatter the formatter used to format the generated CPF; must not be {@code null}
     * @throws IllegalArgumentException if the formatter is {@code null}
     */
    public CpfGenerator(Formatter formatter) throws IllegalArgumentException {
        if (formatter == null) {
            throw new IllegalArgumentException("The formatter must not be null");
        }
        this.formatter = formatter;
    }

    /**
     * Generates a random 9-digit base number for the CPF.
     *
     * @return an array of 9 random digits between 0 and 9
     */
    private int[] generateBase() {
        final var base = new int[BASE_SIZE];
        final var random = ThreadLocalRandom.current();
        for (int i = 0; i < BASE_SIZE; i++) {
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
        final var checkDigits = CpfCheckDigitCalculator.calculateCheckDigits(base);
        return Integers.toString(base) + Integers.toString(checkDigits);
    }

    /**
     * Generates a valid CPF string, formatted or not.
     *
     * @param formatted whether the CPF should be returned formatted (###.###.###-##) or raw (only digits).
     * @return a CPF string, formatted if requested.
     */
    public String generate(boolean formatted) {
        final String normalized = generate();
        if (formatted) {
            return formatter.format(normalized);
        }
        return normalized;
    }

}
