package io.github.felseje.cnpj;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Enum representing the supported CNPJ types: {@code NUMERIC} and {@code ALPHANUMERIC}.
 * <p>
 * Provides regex matching and automatic type detection based on input format.
 *
 * <p><strong>Examples of usage:</strong></p>
 * <pre><code>
 * CnpjType type = CnpjType.detectFrom("12.345.678/0001-95"); // returns NUMERIC
 * boolean isNumeric = CnpjType.NUMERIC.matches("12.345.678/0001-95"); // returns true
 * </code></pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public enum CnpjType {

    /**
     * A numeric-only CNPJ format, e.g., {@code "12345678000195", "12.345.678/0001-95"}.
     */
    NUMERIC(
            Pattern.compile("[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}"),
            Pattern.compile("[0-9]{14}")
    ),

    /**
     * An alphanumeric CNPJ format, which allows uppercase letters and digits in place of the numbers,
     * e.g., {@code "12ABC34501DE35", "12.ABC.345/01DE-35"}.
     */
    ALPHANUMERIC(
            Pattern.compile("[A-Z0-9]{2}\\.[A-Z0-9]{3}\\.[A-Z0-9]{3}/[A-Z0-9]{4}-[0-9]{2}"),
            Pattern.compile("[A-Z0-9]{12}[0-9]{2}")
    );

    private static final CnpjType[] VALUES = values();

    private final Pattern formattedPattern;
    private final Pattern unformattedPattern;

    /**
     * Constructs a {@code CnpjType} with a predefined regex {@link Pattern}.
     *
     * @param formattedPattern the compiled regex pattern associated with the CNPJ type
     */
    CnpjType(final Pattern formattedPattern, final Pattern unformattedPattern) {
        this.formattedPattern = formattedPattern;
        this.unformattedPattern = unformattedPattern;
    }

    /**
     * Attempts to detect the {@code CnpjType} from the given input string.
     * <p>
     * The detection is based on full regex match against known patterns.
     *
     * @param input the CNPJ string to analyze
     * @return the corresponding {@code CnpjType}, if a match is found; {@link Optional#empty()} otherwise.
     */
    public static Optional<CnpjType> detectFrom(String input) {
        if (input == null) {
            return Optional.empty();
        }
        return Arrays.stream(VALUES)
                .filter(type -> type.matches(input))
                .findFirst();
    }

    /**
     * Returns the raw regex of the formatted pattern associated with this CNPJ type.
     *
     * @return the regex pattern as a string
     */
    public String formattedPatternRegex() {
        return formattedPattern.pattern();
    }

    /**
     * Returns the raw regex of the unformatted pattern associated with this CNPJ type.
     *
     * @return the regex pattern as a string
     */
    public String unformattedPatternRegex() {
        return unformattedPattern.pattern();
    }

    /**
     * Checks if the given input string matches one pattern of this {@code CnpjType}.
     *
     * @param input the string to validate
     * @return {@code true} if the input matches the pattern, {@code false} otherwise
     */
    public boolean matches(String input) {
        return input != null && (formattedPattern.matcher(input).matches() || unformattedPattern.matcher(input).matches());
    }

}
