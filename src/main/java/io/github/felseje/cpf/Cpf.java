package io.github.felseje.cpf;

import io.github.felseje.cpf.exception.InvalidCpfException;
import io.github.felseje.internal.cpf.helper.CpfFormatter;
import io.github.felseje.internal.cpf.helper.CpfNormalizer;
import io.github.felseje.internal.cpf.validation.CpfValidator;
import java.util.Objects;

/**
 * Immutable value object representing a CPF (Cadastro de Pessoas Físicas), the Brazilian taxpayer
 * identification number.
 *
 * <p>This class encapsulates a validated CPF and provides structured access
 * to its components (base and check digits) as well as formatted output.</p>
 *
 * <p>Input values are normalized and validated upon construction. Only valid
 * CPF values are allowed to be instantiated.</p>
 *
 * <p>A CPF consists of 11 digits, typically represented in formatted form
 * (e.g. "012.345.678-90") or unformatted form (e.g. "01234567890").</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class Cpf {

  private final String base;
  private final String checkDigits;

  /**
   * Creates a CPF instance from a raw input string.
   *
   * @param raw the CPF value, formatted or unformatted
   * @throws IllegalArgumentException if the input is null or blank
   * @throws InvalidCpfException      if the CPF is not valid
   */
  public Cpf(final String raw) throws IllegalArgumentException, InvalidCpfException {
    final String normalized = CpfNormalizer.normalize(raw);
    CpfValidator.validate(normalized);
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
   * Cpf cpf = new Cpf("012.345.678-90");
   * System.out.println(cpf.getCheckDigits()); // prints "90"
   * }</pre>
   *
   * @return the CPF check digits.
   */
  public String getCheckDigits() {
    return checkDigits;
  }

  /**
   * Returns the normalized CPF value (11 digits).
   *
   * @return the CPF in normalized form
   */
  public String getValue() {
    return base + checkDigits;
  }

  /**
   * Returns a hash code based on CPF structural components (base and check digits).
   *
   * @return hash code value
   */
  @Override
  public int hashCode() {
    int result = 41;
    result = 31 * result + (base == null ? 0 : base.hashCode());
    result = 31 * result + (checkDigits == null ? 0 : checkDigits.hashCode());
    return result;
  }

  /**
   * Indicates whether another object is equal to this CPF.
   *
   * <p>Equality is based on the CPF structural components (base and check digits).</p>
   *
   * @param object the object to compare
   * @return true if equal, false otherwise
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
    return CpfFormatter.format(getValue());
  }

}
