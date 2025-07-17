package io.github.felseje.internal.cnpj.helper;

import io.github.felseje.cnpj.Cnpj;
import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.cnpj.exception.InvalidCnpjException;
import io.github.felseje.internal.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Normalizer implementation for processing CNPJ strings.
 *
 * <p> This class removes formatting symbols and standardizes CNPJ input into a 14-character uppercase alphanumeric string, suitable for validation, classification, or storage. </p>
 *
 * Examples:
 * <ul>
 *   <li>Formatted numeric: {@code "12.345.678/0001-95"}</li>
 *   <li>Formatted alphanumeric: {@code "12.ABC.345/01DE-35"}</li>
 *   <li>Raw numeric: {@code "12345678000195"}</li>
 *   <li>Raw alphanumeric: {@code "12abc34501de35"}</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjNormalizer implements Normalizer {

    private static final Pattern WRONG_CNPJ_DIGITS = Pattern.compile("[^a-zA-Z0-9]");

    /**
     * Constructs a new {@code CnpjNormalizer}.
     */
    public CnpjNormalizer() {
    }

    /**
     * Removes all invalid characters from a given CNPJ string.
     *
     * <p> This method ensures the input is not null or blank and removes all characters that are not alphanumeric (e.g., dots, dashes, slashes, whitespace). </p>
     *
     * @param input the CNPJ string to clean; must not be null or blank
     * @return a string containing only the alphanumeric characters from the input
     * @throws IllegalArgumentException if the input is null or blank
     */
    @Override
    public String clear(String input) throws IllegalArgumentException {
        StringUtils.requireNonBlank(input, "The CNPJ cannot be null or blank");
        return WRONG_CNPJ_DIGITS.matcher(input).replaceAll("");
    }

    /**
     * Normalizes a raw CNPJ string by removing formatting symbols and converting it to uppercase.
     *
     * <p> The result will always be a 14-character string composed of digits and/or uppercase letters. </p>
     * <p> This method does not validate the check digits (DVs), only the structure and length. </p>
     *
     * Examples:
     * <pre>{@code
     * normalize("12.345.678/0001-95"); // "12345678000195"
     * normalize("12.abc.345/01de-35"); // "12ABC34501DE35"
     * }</pre>
     *
     * @param input the raw CNPJ string, formatted or unformatted
     * @return the normalized CNPJ string
     * @throws IllegalArgumentException if the input is null or blank
     * @throws InvalidCnpjException if the result is not exactly 14 characters long
     */
    @Override
    public String normalize(String input) throws IllegalArgumentException, InvalidCnpjException {
        final var cleaned = clear(input).toUpperCase();
        if (cleaned.length() != Cnpj.LENGTH) {
            throw new InvalidCnpjException("The CNPJ must be 14 characters long");
        }
        return cleaned;
    }

}
