package io.github.felseje.internal.cpf.helper;

import io.github.felseje.cpf.Cpf;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.cpf.exception.InvalidCpfException;
import io.github.felseje.internal.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Normalizer implementation for CPF (Cadastro de Pessoas Físicas) strings.
 * <p>
 * This class provides methods to clean and normalize raw CPF inputs, removing formatting characters
 * (e.g. dots, dashes, spaces) and validating the structure and length of the result.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 *     CpfNormalizer normalizer = new CpfNormalizer();
 *     String normalized = normalizer.normalize("123.456.789-09"); // returns "12345678909"
 * }</pre>
 *
 * <p>This class does <strong>not</strong> validate check digits — only the structural integrity of the input.</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class CpfNormalizer implements Normalizer {

    private static final Pattern WRONG_CPF_DIGITS_PATTERN = Pattern.compile("[^0-9]");

    /**
     * Constructs a new {@code CpfNormalizer}.
     */
    public CpfNormalizer() {
    }

    /**
     * Removes all non-digit characters from a given CPF string.
     *
     * @param input the CPF string to clean; must not be {@code null} or blank.
     * @return a string containing only numeric digits.
     * @throws IllegalArgumentException if the input is {@code null} or blank.
     */
    @Override
    public String clear(String input) throws IllegalArgumentException {
        StringUtils.requireNonBlank(input, "The CPF must not be null or blank");
        return WRONG_CPF_DIGITS_PATTERN.matcher(input).replaceAll("");
    }

    /**
     * Normalizes a raw CPF string by removing all non-digit characters and verifying its length.
     *
     * @param input the CPF string to normalize; may be formatted or unformatted.
     * @return a normalized CPF string containing exactly 11 digits.
     * @throws IllegalArgumentException if the input is {@code null} or blank.
     * @throws InvalidCpfException      if the normalized CPF does not contain exactly 11 digits.
     */
    @Override
    public String normalize(String input) throws IllegalArgumentException, InvalidCpfException {
        final var cleaned = clear(input);
        if (cleaned.length() != Cpf.LENGTH) {
            throw new InvalidCpfException("The CPF must be 11 characters long");
        }
        return cleaned;
    }

}
