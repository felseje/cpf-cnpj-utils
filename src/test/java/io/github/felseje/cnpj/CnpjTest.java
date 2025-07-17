package io.github.felseje.cnpj;

import io.github.felseje.cnpj.exception.InvalidCnpjException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cnpj class unit tests")
class CnpjTest {

    private static Stream<Arguments> validCnpjProvider() {
        return Stream.of(
                Arguments.of("12.345.678/0001-95", "Formatted numeric format"),
                Arguments.of("12345678000195", "Unformatted numeric format"),
                Arguments.of("12.ABC.345/01DE-35", "Formatted alphanumeric format"),
                Arguments.of("12ABC34501DE35", "Unformatted alphanumeric format")
        );
    }

    private static Stream<Arguments> invalidCnpjProvider() {
        return Stream.of(
                Arguments.of(null, "Null CNPJ"),
                Arguments.of("", "Empty CNPJ"),
                Arguments.of("   ", "Blank CNPJ"),
                Arguments.of("123", "Invalid length CNPJ"),
                Arguments.of("12.345.678/0001-9X", "Invalid check digits CNPJ"),
                Arguments.of("12!345!678/0001-95", "CNPJ with invalid characters")
        );
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("Should create a non-null Cnpj instance")
    void shouldCreateNonNullCnpj(String raw, String description) {
        // Act
        Cnpj cnpj = assertDoesNotThrow(() -> new Cnpj(raw), "Should not throw for valid input: " + description);

        // Assert
        assertNotNull(cnpj, "Cnpj instance should not be null");
    }

    @Test
    @DisplayName("Should have 14 characters in unformatted CNPJ")
    void shouldHave14CharactersInUnformattedCnpjConstant() {
        // Arrange
        int expectedLength = 14;

        // Act
        int actualLength = Cnpj.LENGTH;

        // Assert
        assertEquals(expectedLength, actualLength, "Unformatted CNPJ length should be 14");
    }

    @Test
    @DisplayName("Should have 18 characters in formatted CNPJ")
    void shouldHave18CharactersInFormattedCnpjConstant() {
        // Arrange
        int expectedLength = 18;

        // Act
        int actualLength = Cnpj.FORMATTED_LENGTH;

        // Assert
        assertEquals(expectedLength, actualLength, "Formatted CNPJ length should be 18");
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("Should have normalized value length of 14 characters")
    void shouldHaveNormalizedValueLength14(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        String normalizedValue = cnpj.getValue();

        // Assert
        assertEquals(14, normalizedValue.length(), "Normalized CNPJ value must have length 14 for input: " + description);
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("Should have formatted value length of 18 characters")
    void shouldHaveFormattedValueLength18(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        String formatted = cnpj.toString();

        // Assert
        assertEquals(18, formatted.length(), "Formatted CNPJ value must have length 18 for input: " + description);
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("getRoot should return non-null root segment")
    void getRootShouldReturnNonNull(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        String root = cnpj.getRoot();

        // Assert
        assertNotNull(root, "Root segment must not be null for input: " + description);
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("getOrder should return non-null order segment")
    void getOrderShouldReturnNonNull(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        String order = cnpj.getOrder();

        // Assert
        assertNotNull(order, "Order segment must not be null for input: " + description);
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("The method 'getCheckDigits()' should return non-null check digits segment")
    void getCheckDigitsShouldReturnNonNull(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        String checkDigits = cnpj.getCheckDigits();

        // Assert
        assertNotNull(checkDigits, "Check digits must not be null for input: " + description);
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("invalidCnpjProvider")
    @DisplayName("Should throw appropriate exception for invalid inputs")
    void shouldThrowForInvalidCnpj(String raw, String description) {
        // Arrange
        Executable constructorCall = () -> new Cnpj(raw);

        // Act & Assert
        if (raw == null || raw.isBlank()) {
            assertThrows(IllegalArgumentException.class, constructorCall, "Should throw IllegalArgumentException for: " + description);
        } else {
            assertThrows(InvalidCnpjException.class, constructorCall, "Should throw InvalidCnpjException for: " + description);
        }
    }

    @Test
    @DisplayName("The method 'getBase()' should return concatenation of root and order segments")
    void getBaseShouldReturnRootPlusOrder() {
        // Arrange
        Cnpj cnpj = new Cnpj("12.345.678/0001-95");

        // Act
        String base = cnpj.getBase();

        // Assert
        assertEquals("123456780001", base, "Base must be root concatenated with order");
    }

    @Test
    @DisplayName("The method 'getValue()' should return concatenation of base and check digits")
    void getValueShouldReturnBasePlusCheckDigits() {
        // Arrange
        Cnpj cnpj = new Cnpj("12.345.678/0001-95");

        // Act
        String value = cnpj.getValue();

        // Assert
        assertEquals("12345678000195", value, "Value must be base concatenated with check digits");
    }

    @Test
    @DisplayName("The method 'toString()' should return formatted CNPJ string")
    void toStringShouldReturnFormattedCnpj() {
        // Arrange
        Cnpj cnpj = new Cnpj("12345678000195");

        // Act
        String formatted = cnpj.toString();

        // Assert
        assertEquals("12.345.678/0001-95", formatted, "toString must return formatted CNPJ");
    }

    @Test
    @DisplayName("The method 'equals()' should return true for equivalent Cnpj instances")
    void equalsShouldReturnTrueForEquivalentCnpjs() {
        // Arrange
        Cnpj cnpj1 = new Cnpj("12.345.678/0001-95");
        Cnpj cnpj2 = new Cnpj("12345678000195");

        // Act & Assert
        assertEquals(cnpj1, cnpj2, "Cnpj instances with same value should be equal");
    }

    @Test
    @DisplayName("The method 'equals()' should return false for different Cnpj instances")
    void equalsShouldReturnFalseForDifferentCnpjs() {
        // Arrange
        Cnpj cnpj1 = new Cnpj("12.345.678/0001-95");
        Cnpj cnpj2 = new Cnpj("12.ABC.345/01DE-35");

        // Act & Assert
        assertNotEquals(cnpj1, cnpj2, "Cnpj instances with different values should not be equal");
    }

    @Test
    @DisplayName("The method 'hashCode()' should be equal for equivalent Cnpj instances")
    void hashCodeShouldBeEqualForEquivalentCnpjs() {
        // Arrange
        Cnpj cnpj1 = new Cnpj("12.345.678/0001-95");
        Cnpj cnpj2 = new Cnpj("12345678000195");

        // Act & Assert
        assertEquals(cnpj1.hashCode(), cnpj2.hashCode(), "Hash codes should be equal for equal Cnpj instances");
    }

    @Test
    @DisplayName("The method 'hashCode()' should be different for different Cnpj instances")
    void hashCodeShouldBeDifferentForDifferentCnpjs() {
        // Arrange
        Cnpj cnpj1 = new Cnpj("12.345.678/0001-95");
        Cnpj cnpj2 = new Cnpj("12.ABC.345/01DE-35");

        // Act & Assert
        assertNotEquals(cnpj1.hashCode(), cnpj2.hashCode(), "Hash codes should differ for different Cnpj instances");
    }

    @ParameterizedTest(name = "{index} => input=''{0}'', description={1}")
    @MethodSource("validCnpjProvider")
    @DisplayName("The method 'getType()' should return correct CnpjType")
    void getTypeShouldReturnCorrectType(String raw, String description) {
        // Arrange
        Cnpj cnpj = new Cnpj(raw);

        // Act
        CnpjType type = cnpj.getType();

        // Assert
        if (raw.matches("[0-9\\.\\-/]+")) {
            assertEquals(CnpjType.NUMERIC, type, "Cnpj type should be NUMERIC for input: " + description);
        } else {
            assertEquals(CnpjType.ALPHANUMERIC, type, "Cnpj type should be ALPHANUMERIC for input: " + description);
        }
    }

}
