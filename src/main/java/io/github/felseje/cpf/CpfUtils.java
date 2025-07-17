package io.github.felseje.cpf;

import io.github.felseje.cpf.exception.InvalidCpfException;
import io.github.felseje.internal.cpf.generation.CpfGenerator;
import io.github.felseje.internal.cpf.helper.CpfFormatter;
import io.github.felseje.internal.cpf.helper.CpfNormalizer;
import io.github.felseje.internal.cpf.validation.CpfValidator;
import io.github.felseje.internal.util.StringUtils;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;

/**
 * Utility class for generating, validating, and formatting CPF values.
 *
 * <p> Provides static methods for validating, formatting, normalizing, and generating CPF numbers, following the standard Brazilian individual taxpayer identification format. </p>
 *
 * <p> This class is not intended to be instantiated and should only be used in a static context. </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class CpfUtils {

    private CpfUtils() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

    /**
     * Generates a valid unformatted CPF string.
     *
     * @return an unformatted CPF string with 11 digits.
     */
    public static String generate() {
        return GeneratorHolder.INSTANCE.generate();
    }

    /**
     * Generates a valid CPF string, formatted or not.
     *
     * @param formatted whether the CPF should be returned formatted (###.###.###-##) or raw (only digits).
     * @return a CPF string, formatted if requested.
     */
    public static String generate(boolean formatted) {
        return GeneratorHolder.INSTANCE.generate(formatted);
    }

    /**
     * Validates if the given CPF string is valid according to the CPF rules.
     *
     * @param cpf the CPF string to validate
     * @return {@code true} if the CPF is valid, {@code false} otherwise
     */
    public static boolean isValid(String cpf) {
        return ValidatorHolder.INSTANCE.isValid(cpf);
    }

    /**
     * Validates a CPF string.
     *
     * <p>This method cleans and normalizes the input, verifies its structure,
     * ensures it isn't composed of repeated digits, and checks if the two final
     * digits (verifiers) match the ones calculated via the official algorithm.</p>
     *
     * @param cpf the CPF string to validate (formatted or unformatted).
     * @throws IllegalArgumentException if the given cpf is null or blank.
     * @throws InvalidCpfException if the given cpf is not valid.
     */
    public static void validate(String cpf) throws IllegalArgumentException, InvalidCpfException {
        StringUtils.requireNonBlank(cpf, "The CPF cannot be null or blank");
        if (!ValidatorHolder.INSTANCE.isValid(cpf)) {
            throw new InvalidCpfException("The CPF is not valid");
        }
    }

    /**
     * Removes all non-digit characters from a given CPF string.
     *
     * @param input the CPF string to clean; must not be {@code null} or blank.
     * @return a string containing only numeric digits.
     * @throws IllegalArgumentException if the input is {@code null} or blank.
     */
    public static String clear(String input) throws IllegalArgumentException {
        return NormalizerHolder.INSTANCE.clear(input);
    }

    /**
     * Normalizes a raw CPF string by removing all non-digit characters and verifying its length.
     *
     * @param input the CPF string to normalize; may be formatted or unformatted.
     * @return a normalized CPF string containing exactly 11 digits.
     * @throws IllegalArgumentException if the input is {@code null} or blank.
     * @throws InvalidCpfException      if the normalized CPF does not contain exactly 11 digits.
     */
    public static String normalize(String input) throws IllegalArgumentException, InvalidCpfException {
        return NormalizerHolder.INSTANCE.normalize(input);
    }

    /**
     * Formats the given raw CPF string into the standard CPF pattern.
     *
     * <p>This method normalizes the input (removes invalid characters and validates length)
     * before formatting it.</p>
     *
     * <p> Example of usage:
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
    public static String format(String input) throws IllegalArgumentException, InvalidCpfException {
        return FormatterHolder.INSTANCE.format(input);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CpfNormalizer}.
     *
     * <p> Responsible for normalizing CPF strings before validation or formatting. </p>
     *
     */
    private static final class NormalizerHolder {
        private static final CpfNormalizer INSTANCE = new CpfNormalizer();
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CpfFormatter}.
     *
     * <p> Formats CPF strings using the shared {@link CpfNormalizer} instance. </p>
     *
     */
    private static final class FormatterHolder {
        private static final CpfFormatter INSTANCE = new CpfFormatter(NormalizerHolder.INSTANCE);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CpfGenerator}.
     *
     * <p> Generates valid CPF numbers, optionally formatted, using the shared formatter. </p>
     *
     */
    private static final class GeneratorHolder {
        private static final CpfGenerator INSTANCE = new CpfGenerator(FormatterHolder.INSTANCE);
    }

    /**
     * Holds a lazily-initialized singleton instance of {@link CpfValidator}.
     *
     * <p> Validates CPF numbers using normalized input provided by {@link CpfNormalizer}. </p>
     *
     */
    private static final class ValidatorHolder {
        private static final CpfValidator INSTANCE = new CpfValidator(NormalizerHolder.INSTANCE);
    }

}
