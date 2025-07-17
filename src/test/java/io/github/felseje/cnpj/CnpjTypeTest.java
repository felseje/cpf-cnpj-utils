package io.github.felseje.cnpj;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CnpjType class unit tests")
class CnpjTypeTest {

    private static Stream<Arguments> provideValidCnpjInputsForDetection() {
        return Stream.of(
                Arguments.of("12.345.678/0001-95", "Formatted numeric CNPJ", CnpjType.NUMERIC),
                Arguments.of("12345678000195", "Unformatted numeric CNPJ", CnpjType.NUMERIC),
                Arguments.of("12.ABC.345/01DE-35", "Formatted alphanumeric CNPJ", CnpjType.ALPHANUMERIC),
                Arguments.of("12ABC34501DE35", "Unformatted alphanumeric CNPJ", CnpjType.ALPHANUMERIC)
        );
    }

    private static Stream<Arguments> provideInvalidCnpjInputsForDetection() {
        return Stream.of(
                Arguments.of(null, "Null input"),
                Arguments.of("", "Empty input"),
                Arguments.of("   ", "Blank input"),
                Arguments.of("INVALID_CNPJ", "Invalid input"),
                Arguments.of("12.345.678/0001", "Partial numeric format"),
                Arguments.of("12.ABC.345/01", "Partial alphanumeric format")
        );
    }

    private static Stream<Arguments> provideInputsForMatching() {
        return Stream.of(
                Arguments.of("12.345.678/0001-95", CnpjType.NUMERIC, "Valid formatted numeric", true),
                Arguments.of("12345678000195", CnpjType.NUMERIC, "Valid unformatted numeric", true),
                Arguments.of("12.ABC.345/01DE-35", CnpjType.ALPHANUMERIC, "Valid formatted alphanumeric", true),
                Arguments.of("12ABC34501DE35", CnpjType.ALPHANUMERIC, "Valid unformatted alphanumeric", true),
                Arguments.of("12.ABC.345/01DE-35", CnpjType.NUMERIC, "Invalid for numeric", false),
                Arguments.of(null, CnpjType.NUMERIC, "Null input for numeric", false),
                Arguments.of(null, CnpjType.ALPHANUMERIC, "Null input for alphanumeric", false)
        );
    }

    private static Stream<Arguments> providePatternRegexExpectations() {
        return Stream.of(
                Arguments.of(CnpjType.NUMERIC, true, "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}", "Formatted regex for NUMERIC"),
                Arguments.of(CnpjType.NUMERIC, false, "[0-9]{14}", "Unformatted regex for NUMERIC"),
                Arguments.of(CnpjType.ALPHANUMERIC, true, "[A-Z0-9]{2}\\.[A-Z0-9]{3}\\.[A-Z0-9]{3}/[A-Z0-9]{4}-[0-9]{2}", "Formatted regex for ALPHANUMERIC"),
                Arguments.of(CnpjType.ALPHANUMERIC, false, "[A-Z0-9]{12}[0-9]{2}", "Unformatted regex for ALPHANUMERIC")
        );
    }

    private static Stream<Arguments> provideInvalidEdgeCasesForDetection() {
        return Stream.of(
                Arguments.of("12.345.678-0001/95", "Wrong separators in numeric format"),
                Arguments.of("12.aBc.345/01De-35", "Lowercase letters in alphanumeric format"),
                Arguments.of(" 12.345.678/0001-95 ", "Numeric with leading/trailing spaces"),
                Arguments.of("12ABC34501DE35 ", "Unformatted alphanumeric with trailing space"),
                Arguments.of(" 12ABC34501DE35", "Unformatted alphanumeric with leading space"),
                Arguments.of("12.ABC.345/01DE-3X", "Invalid character at the end")
        );
    }

    private static Stream<Arguments> provideNonAsciiDigitsInputs() {
        return Stream.of(
                Arguments.of("１２．３４５．６７８／０００１－９５", "Fullwidth digits numeric formatted"), // U+FF10..FF19
                Arguments.of("１２３４５６７８０００１９５", "Fullwidth digits numeric unformatted"),
                Arguments.of("12.AＢＣ.345/01DE-35", "Contains fullwidth letters in alphanumeric formatted"),
                Arguments.of("12ABC３４５01DE35", "Contains fullwidth digits in alphanumeric unformatted"),
                Arguments.of("१२.३४५.६७८/००१-९५", "Devanagari digits numeric formatted"), // U+0966..U+096F
                Arguments.of("١٢.٣٤٥.٦٧٨/٠٠١-٩٥", "Arabic-Indic digits numeric formatted"), // U+0660..U+0669
                Arguments.of("१२३४५६७८००१९५", "Devanagari digits numeric unformatted"),
                Arguments.of("১২৩৪৫৬৭৮০০১৯৫", "Bengali digits numeric unformatted"), // U+09E6..U+09EF
                Arguments.of("௧௨.௩௪௫.௬௭௮/௦௦௧-௯௫", "Tamil digits numeric formatted"), // U+0BE6..U+0BEF
                Arguments.of("๐๑๒๓๔๕๖๗๘๙", "Thai digits only"), // U+0E50..U+0E59
                Arguments.of("໐໑໒໓໔໕໖໗໘໙", "Lao digits only") // U+0ED0..U+0ED9
        );
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidEdgeCasesForDetection")
    @DisplayName("Should return empty for invalid edge case inputs")
    void shouldReturnEmptyForInvalidEdgeCases(String input, String description) {
        // Arrange & Act
        Optional<CnpjType> result = CnpjType.detectFrom(input);

        // Assert
        assertFalse(result.isPresent(), "Expected empty result for edge case invalid input: " + input);
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideNonAsciiDigitsInputs")
    @DisplayName("Should return empty for inputs containing non-ASCII digits or letters")
    void shouldReturnEmptyForNonAsciiDigitsInputs(String input, String description) {
        // Arrange & Act
        Optional<CnpjType> result = CnpjType.detectFrom(input);

        // Assert
        assertFalse(result.isPresent(), "Expected empty result for input with non-ASCII digits: " + input);
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', type={1}, expectedMatch={2}")
    @MethodSource("provideInputsForMatching")
    @DisplayName("Should correctly match CNPJ inputs to expected type")
    void shouldMatchExpectedType(String input, CnpjType type, String description, boolean expectedMatch) {
        // Arrange & Act
        boolean result = type.matches(input);

        // Assert
        assertEquals(expectedMatch, result, "Match result mismatch for input: " + input + " with type: " + type);
    }

    @Test
    @DisplayName("Should detect the first matching CnpjType if multiple matches possible")
    void shouldDetectFirstMatchingType() {
        // Arrange
        String numericInput = "12.345.678/0001-95";

        // Act
        Optional<CnpjType> result = CnpjType.detectFrom(numericInput);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(CnpjType.NUMERIC, result.get());
    }

    @ParameterizedTest(name = "[{index}] input=''{0}''")
    @MethodSource("provideValidCnpjInputsForDetection")
    @DisplayName("Should return false when input contains leading or trailing spaces for matches()")
    void shouldFailMatchWhenInputHasSpaces(String input, String description, CnpjType expectedType) {
        // Arrange
        String inputWithSpaces = " " + input + " ";

        // Act
        boolean result = expectedType.matches(inputWithSpaces);

        // Assert
        assertFalse(result, "Expected matches() to return false for input with leading/trailing spaces: '" + inputWithSpaces + "'");
    }

    @Test
    @DisplayName("Formatted and unformatted pattern regex should be immutable and consistent")
    void shouldReturnConsistentRegexPatterns() {
        for (CnpjType type : CnpjType.values()) {
            // Arrange & Act
            String formatted1 = type.formattedPatternRegex();
            String formatted2 = type.formattedPatternRegex();

            String unformatted1 = type.unformattedPatternRegex();
            String unformatted2 = type.unformattedPatternRegex();

            // Assert
            assertEquals(formatted1, formatted2, "Formatted regex should be consistent for type " + type);
            assertEquals(unformatted1, unformatted2, "Unformatted regex should be consistent for type " + type);
        }
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}'', expectedType={2}")
    @MethodSource("provideValidCnpjInputsForDetection")
    @DisplayName("Should detect correct CNPJ type for valid inputs")
    void shouldDetectCorrectTypeForValidInputs(String input, String description, CnpjType expectedType) {
        // Arrange & Act
        Optional<CnpjType> result = CnpjType.detectFrom(input);

        // Assert
        assertTrue(result.isPresent(), "Expected detection result to be present for input: " + input);
        assertEquals(expectedType, result.get(), "Expected type does not match for input: " + input);
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidCnpjInputsForDetection")
    @DisplayName("Should return empty for invalid CNPJ inputs")
    void shouldReturnEmptyForInvalidInputs(String input, String description) {
        // Arrange & Act
        Optional<CnpjType> result = CnpjType.detectFrom(input);

        // Assert
        assertFalse(result.isPresent(), "Expected empty result for invalid input: " + input);
    }

    @ParameterizedTest(name = "[{index}] type={0}, formatted={1}, expectedRegex=''{2}'', description=''{3}''")
    @MethodSource("providePatternRegexExpectations")
    @DisplayName("Should return correct formatted/unformatted regex")
    void shouldReturnCorrectRegex(CnpjType type, boolean formatted, String expectedRegex, String description) {
        // Arrange & Act
        String result = formatted ? type.formattedPatternRegex() : type.unformattedPatternRegex();

        // Assert
        assertEquals(expectedRegex, result,
                String.format("Expected regex '%s' but got '%s' for type %s (formatted: %s)",
                        expectedRegex, result, type.name(), formatted));
    }

}
