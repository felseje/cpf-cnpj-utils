package io.github.felseje.cnpj;

import io.github.felseje.internal.core.Normalizer;
import io.github.felseje.cnpj.exception.InvalidCnpjException;
import io.github.felseje.cnpj.exception.UnrecognizedCnpjTypeException;
import io.github.felseje.internal.cnpj.generation.AlphanumericGenerator;
import io.github.felseje.internal.cnpj.generation.NumericGenerator;
import io.github.felseje.internal.cnpj.helper.CnpjClassifier;
import io.github.felseje.internal.cnpj.helper.CnpjFormatter;
import io.github.felseje.internal.cnpj.helper.CnpjNormalizer;
import io.github.felseje.internal.cnpj.validation.AlphanumericValidator;
import io.github.felseje.internal.cnpj.validation.NumericValidator;
import io.github.felseje.internal.util.StringUtils;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class for generating, validating, and formatting CNPJ values—both numeric and alphanumeric (2026-ready).
 *
 * <p> Provides static methods for validating, formatting, normalizing, classifying, and generating CNPJ values based on their format, in compliance with upcoming 2026 standards. </p>
 *
 * <p> This class is not intended to be instantiated and should only be used in a static context. </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */

public final class CnpjUtils {

    /**
     * Prevents instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate this class should not be instantiated
     */
    private CnpjUtils() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Ensures that the given CNPJ type is not {@code null}.
     *
     * @param type the CNPJ type to check
     * @throws IllegalArgumentException if {@code type} is {@code null}
     */
    private static void requireTypeNonNull(final CnpjType type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("The CNPJ type cannot be null");
        }
    }

    /**
     * Detects the type of CNPJ based on its string representation.
     *
     * @param cnpj the string representing the CNPJ to be analyzed
     * @return the detected {@link CnpjType} corresponding to the given CNPJ
     * @throws InvalidCnpjException if the CNPJ does not match any valid format
     */
    private static CnpjType getType(final String cnpj) throws InvalidCnpjException {
        return CnpjType.detectFrom(cnpj).orElseThrow(
                () -> new InvalidCnpjException("The CNPJ does not match any valid format")
        );
    }

    /**
     * Generates a CNPJ string based on the specified {@link CnpjType}.
     *
     * @param type the type of CNPJ to generate (numeric or alphanumeric).
     * @return a formatted CNPJ string.
     * @throws IllegalArgumentException if {@code type} is null.
     */
    public static String generate(CnpjType type) throws IllegalArgumentException {
        requireTypeNonNull(type);
        return switch (type) {
            case NUMERIC -> NumericGeneratorHolder.INSTANCE.generate();
            case ALPHANUMERIC -> AlphanumericGeneratorHolder.INSTANCE.generate();
        };
    }

    /**
     * Generates a CNPJ string based on the specified {@link CnpjType},
     * with optional formatting applied.
     *
     * @param type      the type of CNPJ to generate.
     * @param formatted whether to apply formatting using the configured formatter.
     * @return a CNPJ string, formatted if {@code formatted} is {@code true}; raw otherwise.
     * @throws IllegalArgumentException if {@code type} is null.
     */
    public static String generate(CnpjType type, boolean formatted) throws IllegalArgumentException {
        final String normalized = generate(type);
        if (formatted) {
            return FormatterHolder.INSTANCE.format(normalized);
        }
        return normalized;
    }

    /**
     * Validates a CNPJ string using a specific {@link CnpjType}.
     *
     * <p> This method delegates validation to the appropriate validator implementation
     * based on the provided CNPJ type (e.g., numeric or alphanumeric). </p>
     *
     * @param cnpj the CNPJ string to validate
     * @param type the {@link CnpjType} that defines the expected format of the CNPJ
     * @return {@code true} if the CNPJ is valid according to the specified type; {@code false} otherwise
     * @throws IllegalArgumentException if the provided {@code type} is {@code null}
     */
    public static boolean isValid(String cnpj, CnpjType type) throws IllegalArgumentException {
        requireTypeNonNull(type);
        return switch (type) {
            case NUMERIC -> NumericValidatorHolder.INSTANCE.isValid(cnpj);
            case ALPHANUMERIC -> AlphanumericValidatorHolder.INSTANCE.isValid(cnpj);
        };
    }

    /**
     * Validates a CNPJ string by automatically detecting its type.
     *
     * <p> This method attempts to detect the {@link CnpjType} from the provided CNPJ string
     * and then performs validation accordingly. </p>
     *
     * @param cnpj the CNPJ string to validate
     * @return {@code true} if the CNPJ is valid according to its detected format; {@code false} otherwise
     * @throws InvalidCnpjException if the CNPJ format cannot be detected or is not valid
     */
    public static boolean isValid(String cnpj) throws InvalidCnpjException {
        return isValid(cnpj, getType(cnpj));
    }

    /**
     * Validates the given CNPJ according to its specified {@link CnpjType}.
     *
     * @param cnpj the CNPJ string to validate; may be formatted or unformatted.
     * @param type the CNPJ type (numeric or alphanumeric).
     * @throws IllegalArgumentException if {@code cnpj} is null or blank; if {@code type} is null.
     * @throws InvalidCnpjException     if {@code cnpj} is not valid.
     */
    public static void validate(String cnpj, CnpjType type) throws IllegalArgumentException, InvalidCnpjException {
        requireTypeNonNull(type);
        StringUtils.requireNonBlank(cnpj, "The CNPJ must be not null or blank");
        if (!isValid(cnpj, type)) {
            throw new InvalidCnpjException("The CNPJ is not valid");
        }
    }

    /**
     * Attempts to classify the given CNPJ and validates according to its specific type.
     *
     * @param cnpj the CNPJ string to validate; may be formatted or unformatted.
     * @throws IllegalArgumentException if {@code cnpj} is null or blank.
     * @throws InvalidCnpjException if {@code cnpj} is not valid.
     */
    public static void validate(String cnpj) throws IllegalArgumentException, InvalidCnpjException {
        StringUtils.requireNonBlank(cnpj, "The CNPJ cannot be null or blank");
        validate(cnpj, getType(cnpj));
    }

    /**
     * Attempts to classify the given CNPJ string into a {@link CnpjType}.
     *
     * <p> The input must be normalized (i.e., stripped of any formatting characters and in uppercase if applicable). </p>
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
    public static CnpjType classify(String input) throws IllegalArgumentException, UnrecognizedCnpjTypeException {
        return ClassifierHolder.INSTANCE.classify(input);
    }

    /**
     * Attempts to format the given raw CNPJ string into a valid CNPJ pattern.
     *
     * <p> This method first normalizes the input using the configured {@link Normalizer}, then applies formatting to produce a human-readable CNPJ. </p>
     *
     * <p>Examples:
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
    public static String format(String input) throws IllegalArgumentException, InvalidCnpjException {
        return FormatterHolder.INSTANCE.format(input);
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
    public static String clear(String input) throws IllegalArgumentException {
        return NormalizerHolder.INSTANCE.clear(input);
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
     * @throws InvalidCnpjException     if the result is not exactly 14 characters long
     */
    public static String normalize(String input) throws IllegalArgumentException, InvalidCnpjException {
        return NormalizerHolder.INSTANCE.normalize(input);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CnpjClassifier}.
     *
     * <p> This leverages the initialization-on-demand holder idiom to ensure thread-safe,
     * efficient instantiation only when the instance is first accessed. </p>
     *
     */
    private static final class ClassifierHolder {
        private static final CnpjClassifier INSTANCE = new CnpjClassifier();
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CnpjNormalizer}.
     *
     * <p> Used to normalize CNPJ strings before validation or formatting. </p>
     *
     */
    private static final class NormalizerHolder {
        private static final CnpjNormalizer INSTANCE = new CnpjNormalizer();
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CnpjFormatter}.
     *
     * <p> The formatter depends on {@link CnpjNormalizer} for consistent formatting. </p>
     *
     */
    private static final class FormatterHolder {
        private static final CnpjFormatter INSTANCE = new CnpjFormatter(NormalizerHolder.INSTANCE);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link AlphanumericGenerator}.
     *
     * <p> Responsible for generating CNPJ strings in alphanumeric format. </p>
     *
     */
    private static final class AlphanumericGeneratorHolder {
        private static final AlphanumericGenerator INSTANCE = new AlphanumericGenerator();
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link AlphanumericValidator}.
     *
     * <p> Validates alphanumeric CNPJ values using the shared normalizer. </p>
     *
     */
    private static final class AlphanumericValidatorHolder {
        private static final AlphanumericValidator INSTANCE = new AlphanumericValidator(NormalizerHolder.INSTANCE);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link NumericGenerator}.
     *
     * <p> Responsible for generating numeric CNPJ strings. </p>
     *
     */
    private static final class NumericGeneratorHolder {
        private static final NumericGenerator INSTANCE = new NumericGenerator();
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link NumericValidator}.
     *
     * <p> Validates numeric CNPJ values using the shared normalizer. </p>
     *
     */
    private static final class NumericValidatorHolder {
        private static final NumericValidator INSTANCE = new NumericValidator(NormalizerHolder.INSTANCE);
    }

}
