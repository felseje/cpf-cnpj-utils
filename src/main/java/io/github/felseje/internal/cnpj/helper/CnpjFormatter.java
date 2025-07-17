package io.github.felseje.internal.cnpj.helper;

import io.github.felseje.internal.core.Formatter;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.cnpj.exception.InvalidCnpjException;

/**
 * Formatter implementation for formatting raw CNPJ strings into the standard CNPJ pattern.
 *
 * <p> This formatter supports both numeric and alphanumeric CNPJs. It delegates normalization to an injected {@link Normalizer} instance and applies formatting to produce a string in the pattern {@code "##.###.###/####-##"}. </p>
 *
 * Examples:
 * <ul>
 *   <li>{@code "12345678000195"} → {@code "12.345.678/0001-95"}</li>
 *   <li>{@code "12ABC34501DE35"} → {@code "12.ABC.345/01DE-35"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjFormatter implements Formatter {

    private final Normalizer normalizer;

    /**
     * Constructs a new {@code CnpjFormatter} with the specified {@link Normalizer}.
     *
     * @param normalizer the normalizer to preprocess the input before formatting
     * @throws NullPointerException if {@code normalizer} is null
     */
    public CnpjFormatter(Normalizer normalizer) {
        if (normalizer == null) {
            throw new NullPointerException("Normalizer must not be null");
        }
        this.normalizer = normalizer;
    }

    /**
     * Formats the given CNPJ string into the standard pattern.
     *
     * <p>This method assumes the input has already been normalized to exactly 14 characters.</p>
     *
     * @param value the normalized CNPJ string
     * @return the formatted CNPJ string
     */
    private String doFormat(final String value) {
        return new StringBuilder(18).append(value, 0, 2)
                .append('.').append(value, 2, 5)
                .append('.').append(value, 5, 8)
                .append('/').append(value, 8, 12)
                .append('-').append(value, 12, value.length())
                .toString();
    }

    /**
     * Attempts to format the given raw CNPJ string into a valid CNPJ pattern.
     *
     * <p> This method first normalizes the input using the configured {@link Normalizer}, then applies formatting to produce a human-readable CNPJ. </p>
     *
     * Examples:
     * <pre>{@code
     *     format("12345678000195");   // returns "12.345.678/0001-95"
     *     format("12ABC34501DE35");   // returns "12.ABC.345/01DE-35"
     * }</pre>
     *
     * @param input the raw CNPJ string, which may be numeric or alphanumeric
     * @return a formatted CNPJ string
     * @throws IllegalArgumentException if {@code input} is {@code null} or blank
     * @throws InvalidCnpjException     if {@code input} cannot be normalized or formatted properly
     */
    @Override
    public String format(String input) {
        return doFormat(normalizer.normalize(input));
    }

}
