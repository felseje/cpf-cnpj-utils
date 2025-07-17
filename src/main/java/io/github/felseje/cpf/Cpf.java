package io.github.felseje.cpf;

import io.github.felseje.cpf.exception.InvalidCpfException;

import java.util.Objects;

/**
 * Represents a CPF (Cadastro de Pessoas Físicas), which is the Brazilian
 * national registry of people.
 *
 * <p> This class is responsible for storing, validating, and formatting CPF numbers. </p>
 * <p> A valid CPF consists of 11 digits and may be represented in formatted (e.g., "012.345.678-90") or unformatted form (e.g., "01234567890"). </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class Cpf {

    /**
     * Cpf normalized length constant.
     */
    public static final int LENGTH = 11;

    /**
     * Cpf formatted length constant.
     */
    public static final int FORMATTED_LENGTH = 14;

    private final String base;
    private final String checkDigits;

    /**
     * Build an instance of the {@link Cpf} using a string representation.
     *
     * @param raw the raw CPF string.
     * @throws IllegalArgumentException if the {@code raw} is null or blank.
     * @throws InvalidCpfException      if the {@code raw} is not a valid CPF.
     */
    public Cpf(String raw) throws IllegalArgumentException, InvalidCpfException {
        CpfUtils.validate(raw);
        String normalized = CpfUtils.normalize(raw);
        this.base = normalized.substring(0, 9);
        this.checkDigits = normalized.substring(9);
    }

    /**
     * Returns the base part of the CPF (9 digits).
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cpf cpf = new Cpf("012.345.678-90");
     * System.out.println(cpf.getBase()); // prints "012345678"
     * }</pre>
     *
     * @return the CPF base.
     */
    public String getBase() {
        return base;
    }

    /**
     * Returns the check digits (last 2 digits) of the CPF.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cpf cpf = new Cpf("01234567890");
     * System.out.println(cpf.getCheckDigits()); // prints "90"
     * }</pre>
     *
     * @return the CNPJ check digits.
     */
    public String getCheckDigits() {
        return checkDigits;
    }

    /**
     * Returns the full normalized CPF string (11 characters).
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cpf cpf = new Cpf("012.345.678-90");
     * System.out.println(cpf.getValue()); // prints "01234567890"
     * }</pre>
     *
     * @return the normalized CNPJ value.
     */
    public String getValue() {
        return base + checkDigits;
    }

    /**
     * Returns a hash code value for this CPF.
     *
     * <p> The hash code is computed based on the {@code value} field. </p>
     * <p> This ensures consistency with the {@link #equals(Object)} method. </p>
     *
     * @return the hash code value for this CPF.
     */
    @Override
    public int hashCode() {
        int result = 41;
        result = 31 * result + (base == null ? 0 : base.hashCode());
        result = 31 * result + (checkDigits == null ? 0 : checkDigits.hashCode());
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * <p> Two {@link Cpf} instances are considered equal if they have the same normalized {@code value}. </p>
     *
     * @param object the object to compare with.
     * @return {@code true} if this object is equal to the given object; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cpf other)) {
            return false;
        }
        return Objects.equals(this.base, other.base)
                && Objects.equals(this.checkDigits, other.checkDigits);
    }

    /**
     * Returns the formatted representation of this CPF.
     *
     * @return the CPF string in the standard format (e.g., {@code 012.345.678-90}).
     */
    @Override
    public String toString() {
        return CpfUtils.format(getValue());
    }

}
