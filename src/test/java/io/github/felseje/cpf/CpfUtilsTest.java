package io.github.felseje.cpf;

import io.github.felseje.cpf.exception.InvalidCpfException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CpfUtils class unit tests")
class CpfUtilsTest {

    /**
     * Provides null, empty, and blank inputs to test generic input validation.
     */
    private static Stream<Arguments> provideInvalidInputs() {
        return Stream.of(
                Arguments.of(null, "Null input"),
                Arguments.of("", "Empty input"),
                Arguments.of("   ", "Blank input")
        );
    }

    /**
     * Provides both structurally invalid and fake CPFs for validity testing.
     */
    private static Stream<Arguments> provideInvalidInputsForCpfValidation() {
        return Stream.concat(provideInvalidInputs(), Stream.of(
                Arguments.of("12345678900", "Invalid CPF"),
                Arguments.of("111.111.111-11", "Repeated digits")
        ));
    }

    @Test
    @DisplayName("Should throw exception when trying to instantiate utility class")
    void shouldThrowOnInstantiation() throws Exception {
        // Arrange
        Constructor<CpfUtils> constructor = CpfUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Act
        Executable executable = constructor::newInstance;

        // Assert
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                executable,
                "Expected InvocationTargetException when trying to instantiate CpfUtils"
        );
        assertInstanceOf(
                IllegalStateException.class,
                ex.getCause(),
                "Expected cause to be IllegalStateException"
        );
        assertEquals(
                "This class should not be instantiated",
                ex.getCause().getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should generate unformatted CPF with 11 digits")
    void shouldGenerateUnformattedCpf() {
        // Act
        String generatedCpf = CpfUtils.generate();

        // Assert
        assertNotNull(generatedCpf, "Generated CPF should not be null");
        assertEquals(11, generatedCpf.length(), "Generated CPF should have 11 digits");
        assertTrue(generatedCpf.matches("[0-9]{11}"), "Generated CPF should contain only digits");
    }

    @Test
    @DisplayName("Should generate formatted CPF with correct pattern")
    void shouldGenerateFormattedCpf() {
        // Act
        String generatedCpf = CpfUtils.generate(true);

        // Assert
        assertNotNull(
                generatedCpf,
                "Formatted CPF should not be null"
        );
        assertTrue(
                generatedCpf.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}"),
                "Formatted CPF should match pattern XXX.XXX.XXX-XX"
        );
    }

    @Test
    @DisplayName("Should return true for a valid CPF")
    void shouldReturnTrueForValidCpf() {
        // Arrange
        String generatedCpf = CpfUtils.generate();

        // Act
        boolean result = CpfUtils.isValid(generatedCpf);

        // Assert
        assertTrue(result, "Expected valid CPF to return true");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidInputsForCpfValidation")
    @DisplayName("Should return false for invalid CPFs")
    void shouldReturnFalseForInvalidCpfs(String source, String description) {
        // Act & Assertion
        assertFalse(CpfUtils.isValid(source), "Expected false for ".concat(description.toLowerCase()));
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw IllegalArgumentException when validating null or blank input")
    void shouldThrowIllegalArgumentExceptionWhenValidatingNullOrBlankInput(String source, String description) {
        // Act
        Executable executable = () -> CpfUtils.validate(source);

        // Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                executable,
                "Expected IllegalArgumentException for ".concat(description.toLowerCase())
        );
        assertEquals(
                "The CPF cannot be null or blank",
                ex.getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should throw InvalidCpfException for invalid CPF")
    void shouldThrowInvalidCpfException() {
        // Arrange
        String invalidCpf = "12345678900";

        // Act
        Executable executable = () -> CpfUtils.validate(invalidCpf);

        // Assert
        InvalidCpfException ex = assertThrows(
                InvalidCpfException.class,
                executable,
                "Expected InvalidCpfException for invalid CPF"
        );
        assertEquals(
                "The CPF is not valid",
                ex.getMessage(),
                "Exception message should be equal to expected");
    }

    @Test
    @DisplayName("Should validate a valid CPF without throwing")
    void shouldValidateValidCpf() {
        // Arrange
        String validCpf = "012.345.678-90";

        // Act
        Executable executable = () -> CpfUtils.validate(validCpf);

        // Assert
        assertDoesNotThrow(executable, "Expected no exception for valid CPF");
    }

    @Test
    @DisplayName("Should clear non-digit characters from CPF")
    void shouldClearCpf() {
        // Arrange
        String formattedCpf = "012.345.678-90";

        // Act
        String result = CpfUtils.clear(formattedCpf);

        // Assert
        assertEquals("01234567890", result, "Expected cleaned CPF to contain only digits");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw on clear with null or blank input")
    void shouldThrowOnClearWithInvalidInput(String source, String description) {
        // Act
        Executable executable = () -> CpfUtils.clear(source);

        // Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                executable,
                "Expected IllegalArgumentException for ".concat(description.toLowerCase())
        );
        assertEquals(
                "The CPF must not be null or blank",
                ex.getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should normalize formatted CPF")
    void shouldNormalizeCpf() {
        // Arrange
        String formatted = "012.345.678-90";

        // Act
        String normalized = CpfUtils.normalize(formatted);

        // Assert
        assertEquals("01234567890", normalized, "Expected normalized CPF to be unformatted");
        assertTrue(normalized.matches("\\d{11}"), "Expected normalized CPF to have 11 digits");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw on normalize with invalid input")
    void shouldThrowOnNormalizeWithInvalidInput(String source, String description) {
        // Act
        Executable executable = () -> CpfUtils.normalize(source);

        // Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                executable,
                "Expected IllegalArgumentException for " + description.toLowerCase()
        );
        assertEquals(
                "The CPF must not be null or blank",
                ex.getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should throw InvalidCpfException when normalizing invalid length")
    void shouldThrowOnNormalizeInvalidLength() {
        // Arrange
        String shortCpf = "123";

        // Act
        Executable executable = () -> CpfUtils.normalize(shortCpf);

        // Assert
        InvalidCpfException ex = assertThrows(
                InvalidCpfException.class,
                executable,
                "Expected InvalidCpfException for short CPF"
        );
        assertEquals(
                "The CPF must be 11 characters long",
                ex.getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should format raw CPF correctly")
    void shouldFormatCpf() {
        // Arrange
        String rawCpf = "01234567890";

        // Act
        String formatted = CpfUtils.format(rawCpf);

        // Assert
        assertEquals("012.345.678-90", formatted, "Expected formatted CPF to match standard pattern");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw IllegalArgumentException when formatting null or blank input")
    void shouldThrowOnFormatWithInvalidInput(String input, String description) {
        // Act
        Executable executable = () -> CpfUtils.format(input);

        // Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                executable,
                "Expected IllegalArgumentException for input ".concat(description.toLowerCase())
        );
        assertEquals(
                "The CPF must not be null or blank",
                exception.getMessage(),
                "Exception message should be equal to expected"
        );
    }

    @Test
    @DisplayName("Should throw InvalidCpfException for incorrect input")
    void shouldThrowInvalidCpfExceptionOnFormat() {
        // Arrange
        String invalid = "123";

        // Act
        Executable executable = () -> CpfUtils.format(invalid);

        // Assert
        InvalidCpfException ex = assertThrows(
                InvalidCpfException.class,
                executable,
                "Expected InvalidCpfException for invalid CPF input"
        );
        assertEquals(
                "The CPF must be 11 characters long",
                ex.getMessage(),
                "Exception message should be equal to expected"
        );
    }

}
