package io.github.felseje.cnpj;

import io.github.felseje.cnpj.exception.InvalidCnpjException;
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

@DisplayName("CnpjUtils class unit tests")
class CnpjUtilsTest {

    private static Stream<Arguments> provideInvalidInputs() {
        return Stream.of(
                Arguments.of(null, "Null input"),
                Arguments.of("", "Empty input"),
                Arguments.of("   ", "Blank input")
        );
    }

    private static Stream<Arguments> provideStructurallyInvalidCnpjs() {
        return Stream.of(
                Arguments.of("1A2B3C4D5E6F7", "Too short"),
                Arguments.of("1A2B3C4D5E6F7G8", "Too long"),
                Arguments.of("123ABC4560A1BEE", "Invalid alphanumeric format")
        );
    }

    @Test
    @DisplayName("Should throw exception when trying to instantiate utility class")
    void shouldThrowOnInstantiation() throws Exception {
        // Arrange
        Constructor<CnpjUtils> constructor = CnpjUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Act
        Executable executable = constructor::newInstance;

        // Assert
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, executable);
        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("This class should not be instantiated", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("Should generate a valid numeric CNPJ")
    void shouldGenerateValidNumericCnpj() {
        // Arrange
        CnpjType type = CnpjType.NUMERIC;

        // Act
        String generated = CnpjUtils.generate(type);

        // Assert
        assertNotNull(generated, "Generated CNPJ should not be null");
        assertEquals(14, generated.length(), "Generated numeric CNPJ must be 14 characters long");
        assertTrue(generated.matches("\\d{14}"), "Generated numeric CNPJ must contain only digits");
    }

    @Test
    @DisplayName("Should generate a formatted alphanumeric CNPJ")
    void shouldGenerateFormattedAlphanumericCnpj() {
        // Arrange
        CnpjType type = CnpjType.ALPHANUMERIC;

        // Act
        String formatted = CnpjUtils.generate(type, true);

        // Assert
        assertNotNull(formatted, "Formatted CNPJ should not be null");
        assertTrue(formatted.matches("[A-Z0-9.]+/[A-Z0-9]+-[A-Z0-9]+"), "Formatted CNPJ should match expected pattern");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', reason=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw IllegalArgumentException when input is null or blank (validate)")
    void shouldThrowIllegalArgumentWhenValidatingInvalidInput(String input, String reason) {
        // Act
        Executable executable = () -> CnpjUtils.validate(input);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The CNPJ cannot be null or blank", exception.getMessage(), "Exception message must be correct");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', reason=''{1}''")
    @MethodSource("provideStructurallyInvalidCnpjs")
    @DisplayName("Should throw InvalidCnpjException when CNPJ is structurally invalid")
    void shouldThrowInvalidCnpjExceptionOnInvalidStructure(String input, String reason) {
        // Act
        Executable executable = () -> CnpjUtils.validate(input);

        // Assert
        InvalidCnpjException ex = assertThrows(InvalidCnpjException.class, executable);
        assertEquals("The CNPJ does not match any valid format", ex.getMessage(), "Expected a structural validation failure");
    }

    @Test
    @DisplayName("Should validate a generated numeric CNPJ without exception")
    void shouldValidateGeneratedNumericCnpj() {
        // Arrange
        String cnpj = CnpjUtils.generate(CnpjType.NUMERIC);

        // Act & Assert
        assertDoesNotThrow(() -> CnpjUtils.validate(cnpj, CnpjType.NUMERIC), "Expected valid numeric CNPJ to pass validation");
    }

    @Test
    @DisplayName("Should clear CNPJ input")
    void shouldClearCnpjInput() {
        // Arrange
        String formatted = "12.345.678/0001-95";

        // Act
        String cleared = CnpjUtils.clear(formatted);

        // Assert
        assertEquals("12345678000195", cleared, "Expected cleared CNPJ to remove all formatting");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', reason=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw IllegalArgumentException when clearing null or blank input")
    void shouldThrowOnClearWithInvalidInput(String input, String reason) {
        // Act
        Executable executable = () -> CnpjUtils.clear(input);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The CNPJ cannot be null or blank", ex.getMessage(), "Exception message must be correct");
    }

    @Test
    @DisplayName("Should normalize formatted CNPJ")
    void shouldNormalizeCnpj() {
        // Arrange
        String formatted = "12.abc.345/01de-35";

        // Act
        String normalized = CnpjUtils.normalize(formatted);

        // Assert
        assertEquals("12ABC34501DE35", normalized, "Expected normalized CNPJ to be uppercase and without formatting");
        assertEquals(14, normalized.length(), "Normalized CNPJ must be 14 characters");
    }

    @Test
    @DisplayName("Should throw on normalize when CNPJ length is invalid")
    void shouldThrowOnNormalizeWithInvalidLength() {
        // Arrange
        String shortCnpj = "123";

        // Act
        Executable executable = () -> CnpjUtils.normalize(shortCnpj);

        // Assert
        InvalidCnpjException ex = assertThrows(InvalidCnpjException.class, executable);
        assertEquals("The CNPJ must be 14 characters long", ex.getMessage(), "Expected exception for short CNPJ");
    }

    @Test
    @DisplayName("Should format unformatted CNPJ correctly")
    void shouldFormatRawCnpj() {
        // Arrange
        String raw = "12345678000195";

        // Act
        String formatted = CnpjUtils.format(raw);

        // Assert
        assertEquals("12.345.678/0001-95", formatted, "Expected standard formatted output for numeric CNPJ");
    }

    @Test
    @DisplayName("Should classify numeric CNPJ correctly")
    void shouldClassifyNumericCnpj() {
        // Arrange
        String raw = "12345678000195";

        // Act
        CnpjType type = CnpjUtils.classify(raw);

        // Assert
        assertEquals(CnpjType.NUMERIC, type, "Expected classification to be NUMERIC");
    }

    @Test
    @DisplayName("Should classify alphanumeric CNPJ correctly")
    void shouldClassifyAlphanumericCnpj() {
        // Arrange
        String raw = "12ABC34501DE35";

        // Act
        CnpjType type = CnpjUtils.classify(raw);

        // Assert
        assertEquals(CnpjType.ALPHANUMERIC, type, "Expected classification to be ALPHANUMERIC");
    }

    @ParameterizedTest(name = "[{index}] input=''{0}'', reason=''{1}''")
    @MethodSource("provideInvalidInputs")
    @DisplayName("Should throw when formatting null or blank input")
    void shouldThrowOnFormatWithInvalidInput(String input, String reason) {
        // Act
        Executable executable = () -> CnpjUtils.format(input);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The CNPJ cannot be null or blank", ex.getMessage(), "Expected correct exception message");
    }

}
