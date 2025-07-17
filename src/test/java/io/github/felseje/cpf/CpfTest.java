package io.github.felseje.cpf;

import io.github.felseje.cpf.exception.InvalidCpfException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Cpf} class.
 */
@DisplayName("Cpf class unit tests")
class CpfTest {

    /**
     * Provides a stream of invalid inputs for testing CPF instantiation.
     *
     * @return a {@link Stream} of {@link Arguments} containing invalid input values and their descriptions.
     */
    private static Stream<Arguments> provideInvalidCpfInputs() {
        return Stream.of(
                Arguments.of(null, "Null input"),
                Arguments.of("", "Empty input"),
                Arguments.of("  ", "Blank input")
        );
    }

    @Test
    @DisplayName("Should create CPF from formatted input")
    void shouldCreateCpfFromFormattedInput() {
        // Arrange
        String input = "012.345.678-90";

        // Act
        Cpf cpf = new Cpf(input);

        // Assert
        assertEquals("012345678", cpf.getBase(), "CPF base is not equal to expected");
        assertEquals("90", cpf.getCheckDigits(), "CPF check digits are not equals to expected");
        assertEquals("01234567890", cpf.getValue(), "CPF value is not equal to expected");
        assertEquals("012.345.678-90", cpf.toString(), "CPF is not equal to expected");
    }

    @Test
    @DisplayName("Should create CPF from unformatted input")
    void shouldCreateCpfFromUnformattedInput() {
        // Arrange
        String input = "01234567890";

        // Act
        Cpf cpf = new Cpf(input);

        // Assert
        assertEquals("012345678", cpf.getBase(), "CPF base is not equal to expected");
        assertEquals("90", cpf.getCheckDigits(), "CPF check digits are not equals to expected");
        assertEquals("01234567890", cpf.getValue(), "CPF value is not equal to expected");
        assertEquals("012.345.678-90", cpf.toString(), "CPF is not equal to expected");
    }

    @ParameterizedTest(name = "[{index}] value=''{0}'', description=''{1}''")
    @MethodSource("provideInvalidCpfInputs")
    @DisplayName("Should throw IllegalArgumentException when input is null, empty or blank")
    void shouldThrowIllegalArgumentExceptionForInvalidInput(String source, String description) {
        // Arrange
        Executable executable = () -> new Cpf(source);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                executable,
                "Expected IllegalArgumentException to be thrown"
        );
    }

    @Test
    @DisplayName("Should throw InvalidCpfException for invalid CPF")
    void shouldThrowExceptionForInvalidCpf() {
        // Arrange
        String input = "12345678900";

        // Act
        Executable executable = () -> new Cpf(input);

        // Assert
        assertThrows(
                InvalidCpfException.class,
                executable,
                "Expected InvalidCpfException to be thrown"
        );
    }

    @Test
    @DisplayName("Should consider equal CPFs with same numeric value")
    void shouldReturnTrueForEqualCpfs() {
        // Arrange
        String firstCpfValue = "012.345.678-90";
        String secondCpfValue = "01234567890";

        // Act
        Cpf firstCpf = new Cpf(firstCpfValue);
        Cpf secondCpf = new Cpf(secondCpfValue);

        // Assert
        assertEquals(firstCpf, secondCpf, "CPFs are not equal");
        assertEquals(firstCpf.hashCode(), secondCpf.hashCode(), "CPF hash codes are not equal");
    }

    @Test
    @DisplayName("Should not consider different CPFs as equal")
    void shouldReturnFalseForDifferentCpfs() {
        // Arrange
        String firstCpfValue = "012.345.678-90";
        String secondCpfValue = "123.456.789-09";

        // Act
        Cpf firstCpf = new Cpf(firstCpfValue);
        Cpf secondCpf = new Cpf(secondCpfValue);

        // Assert
        assertNotEquals(firstCpf, secondCpf, "CPFs are equal");
        assertNotEquals(firstCpf.hashCode(), secondCpf.hashCode(), "CPF hash codes are equal");
    }

    @Test
    @DisplayName("Should return CPF formatted as XXX.XXX.XXX-XX")
    void shouldFormatCpfCorrectly() {
        // Arrange
        Cpf cpf = new Cpf("01234567890");

        // Act
        String formatted = cpf.toString();

        // Assert
        assertEquals("012.345.678-90", formatted, "Formatted CPF is not equal to expected");
    }

}
