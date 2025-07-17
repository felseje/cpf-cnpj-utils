package io.github.felseje.internal.cpf.helper;

import io.github.felseje.internal.core.Formatter;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.cpf.exception.InvalidCpfException;

/**
 * Formatter implementation for formatting CPF (Cadastro de Pessoas Físicas) strings.
 *
 * <p> This class formats a raw CPF string into the standard Brazilian CPF format: <code>XXX.XXX.XXX-XX</code>. </p>
 * <p> It relies on the {@link CpfNormalizer} to normalize the input before formatting. </p>
 *
 * Example usage:
 * <pre>{@code
 *     CpfFormatter formatter = new CpfFormatter();
 *     String formattedCpf = formatter.format("01234567890");  // returns "012.345.678-90"
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class CpfFormatter implements Formatter {

    private final Normalizer normalizer;

    /**
     * Creates a new instance of {@code CpfFormatter}.
     *
     * @throws IllegalArgumentException if the parameter normalizer is null.
     */
    public CpfFormatter(Normalizer normalizer) throws IllegalArgumentException {
        if (normalizer == null) {
            throw new IllegalArgumentException("The normalizer must not be null");
        }
        this.normalizer = normalizer;
    }

    /**
     * Performs the actual formatting of a normalized CPF string.
     *
     * @param value the normalized CPF string (expected to have 11 digits).
     * @return the CPF formatted as <code>XXX.XXX.XXX-XX</code>.
     */
    private String doFormat(final String value) {
        return "%s.%s.%s-%s".formatted(
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9)
        );
    }

    /**
     * Formats the given raw CPF string into the standard CPF pattern.
     *
     * <p> This method normalizes the input (removes invalid characters and validates length) before formatting it. </p>
     *
     * Example of usage:
     * <pre>{@code
     *     CpfFormatter formatter = new CpfFormatter(new CpfNormalizer());
     *     formatter.format("01234567890"); // returns "012.345.678-90"
     * }</pre>
     *
     * @param input the raw CPF string to format; must not be {@code null} or blank.
     * @return a formatted CPF string.
     * @throws IllegalArgumentException if {@code input} is {@code null} or blank.
     * @throws InvalidCpfException      if the {@code input} cannot be normalized into a valid CPF.
     */
    @Override
    public String format(String input) throws IllegalArgumentException, InvalidCpfException {
        return doFormat(normalizer.normalize(input));
    }

}
