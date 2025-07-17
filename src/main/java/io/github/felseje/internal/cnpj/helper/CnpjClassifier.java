package io.github.felseje.internal.cnpj.helper;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.internal.core.Classifier;
import io.github.felseje.cnpj.exception.UnrecognizedCnpjTypeException;
import io.github.felseje.internal.util.StringUtils;

import java.util.regex.Pattern;

import static io.github.felseje.internal.Constants.NULL_OR_BLANK_ERROR;

/**
 * Implementation of {@link Classifier} for identifying the type of normalized CNPJ string.
 *
 * <p> This classifier determines whether the input CNPJ is {@link CnpjType#NUMERIC} or {@link CnpjType#ALPHANUMERIC} based on its structure. </p>
 * <p> It uses regular expressions to match a fully normalized input string. </p>
 *
 * Example accepted inputs:
 * <ul>
 *   <li>{@code "12345678000195"} → NUMERIC</li>
 *   <li>{@code "12ABC34501DE35"} → ALPHANUMERIC</li>
 * </ul>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CnpjClassifier implements Classifier {

    private static final Pattern RAW_NUMERIC_PATTERN = Pattern.compile("[0-9]{14}");
    private static final Pattern RAW_ALPHANUMERIC_PATTERN = Pattern.compile("[A-Z0-9]{14}");

    /**
     * Constructs a new {@code CnpjClassifier}.
     */
    public CnpjClassifier() {
    }

    /**
     * Attempts to classify the given CNPJ string into a {@link CnpjType}.
     *
     * <p>The input must be normalized (i.e., stripped of any formatting characters and in uppercase if applicable).</p>
     *
     * Examples:
     * <pre>{@code
     * classify("12345678000195");   // returns NUMERIC
     * classify("12ABC34501DE35");   // returns ALPHANUMERIC
     * }</pre>
     *
     * @param input the normalized CNPJ string to classify
     * @return a {@link CnpjType} representing the classification result
     * @throws IllegalArgumentException  if the input is null or blank
     * @throws UnrecognizedCnpjTypeException if the input does not match any known CNPJ format
     */
    @Override
    public CnpjType classify(String input) throws IllegalArgumentException, UnrecognizedCnpjTypeException {
        StringUtils.requireNonBlank(input, NULL_OR_BLANK_ERROR);
        if (RAW_NUMERIC_PATTERN.matcher(input).matches()) {
            return CnpjType.NUMERIC;
        }
        if (RAW_ALPHANUMERIC_PATTERN.matcher(input).matches()) {
            return CnpjType.ALPHANUMERIC;
        }
        throw new UnrecognizedCnpjTypeException("The CNPJ does not match any valid pattern. Make sure to use a normalized CNPJ.");
    }

}
