package io.github.felseje.cnpj;

import io.github.felseje.cnpj.exception.InvalidCnpjException;

import java.util.Objects;

/**
 * Represents a CNPJ (Cadastro Nacional da Pessoa Jurídica), which is the Brazilian
 * national registry of legal entities.
 *
 * <p> This class is responsible for storing, validating, and formatting CNPJ numbers. </p>
 * <p> A valid CNPJ consists of 18 characters formatted (e.g., "12.345.678/0001-95", "12.ABC.345/01DE-35") or 14 characters unformatted (e.g., "12345678000195", "12ABC34501DE35"). </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public class Cnpj {

    /**
     * Cnpj normalized length constant.
     */
    public static final int LENGTH = 14;

    /**
     * Cnpj formatted length constant.
     */
    public static final int FORMATTED_LENGTH = 18;

    private final String root;
    private final String order;
    private final CnpjType type;
    private final String checkDigits;

    /**
     * Build an instance of the {@link Cnpj} using a string representation.
     *
     * @param raw the raw CNPJ string, which may be numeric or alphanumeric.
     * @throws IllegalArgumentException if the {@code raw} is null or blank.
     * @throws InvalidCnpjException     if the {@code raw} is not a valid CNPJ.
     */
    public Cnpj(String raw) throws IllegalArgumentException, InvalidCnpjException {
        CnpjUtils.validate(raw);
        final var normalized = CnpjUtils.normalize(raw);
        this.type = CnpjType.detectFrom(normalized).orElseThrow();
        this.root = normalized.substring(0, 8);
        this.order = normalized.substring(8, 12);
        this.checkDigits = normalized.substring(12);
    }

    /**
     * Returns the root portion (first 8 digits) of the CNPJ.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12.345.678/0001-95");
     * System.out.println(cnpj.getRoot()); // prints "12345678"
     * }</pre>
     *
     * @return the CNPJ root.
     */
    public String getRoot() {
        return root;
    }

    /**
     * Returns the order portion (positions 9 to 12) of the CNPJ.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12.345.678/0001-95");
     * System.out.println(cnpj.getOrder()); // prints "0001"
     * }</pre>
     *
     * @return the CNPJ order.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Returns the check digits (last 2 digits) of the CNPJ.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12.345.678/0001-95");
     * System.out.println(cnpj.getCheckDigits()); // prints "95"
     * }</pre>
     *
     * @return the CNPJ check digits.
     */
    public String getCheckDigits() {
        return checkDigits;
    }

    /**
     * Returns the type of this CNPJ, as classified by its contents.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpjNum = new Cnpj("12.345.678/0001-95");
     * Cnpj cnpjAlp = new Cnpj("12.ABC.345/01DE-35");
     * System.out.println(cnpjNum.getType()); // prints NUMERIC
     * System.out.println(cnpjAlp.getType()); // prints ALPHANUMERIC
     * }</pre>
     *
     * @return the CNPJ type.
     */
    public CnpjType getType() {
        return type;
    }

    /**
     * Returns the base part of the CNPJ, which consists of root + order (12 digits).
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12.345.678/0001-95");
     * System.out.println(cnpj.getBase()); // prints "123456780001"
     * }</pre>
     *
     * @return the CNPJ base.
     */
    public String getBase() {
        return root + order;
    }

    /**
     * Returns the full normalized CNPJ string (14 characters).
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12.345.678/0001-95");
     * System.out.println(cnpj.getValue()); // prints "12345678000195"
     * }</pre>
     *
     * @return the normalized CNPJ value.
     */
    public String getValue() {
        return getBase() + checkDigits;
    }

    /**
     * Returns a hash code value for this CNPJ.
     *
     * <p> This ensures consistency with the {@link #equals(Object)} method. </p>
     *
     * @return the hash code value for this CNPJ.
     */
    @Override
    public int hashCode() {
        int result = 71;
        result = 61 * result + (root == null ? 0 : root.hashCode());
        result = 61 * result + (order == null ? 0 : order.hashCode());
        result = 61 * result + (type == null ? 0 : type.hashCode());
        result = 61 * result + (checkDigits == null ? 0 : checkDigits.hashCode());
        return result;
    }

    /**
     * Compares this {@code Cnpj} to another object for equality.
     * <p>Two {@code Cnpj} instances are considered equal if and only if:</p>
     * <ul>
     *     <li>Both are instances of {@code Cnpj}</li>
     *     <li>The values of {@code root}, {@code order}, {@code checkDigits} and {@code type} are equal</li>
     * </ul>
     *
     * <p> The comparison is strictly based on these four internal attributes, which represent the normalized form of the CNPJ. Original formatting, punctuation, and input casing are not considered. </p>
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj1 = new Cnpj("12.345.678/0001-95");
     * Cnpj cnpj2 = new Cnpj("12345678000195");
     * boolean equals = cnpj1.equals(cnpj2); // true, since internal components are equal
     * }</pre>
     *
     * @param object the object to compare with this {@code Cnpj}.
     * @return {@code true} if the given object is also a {@code Cnpj} and all internal components match;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cnpj other)) {
            return false;
        }
        return this.type == other.type
                && Objects.equals(this.root, other.root)
                && Objects.equals(this.order, other.order)
                && Objects.equals(this.checkDigits, other.checkDigits);
    }

    /**
     * Returns the formatted representation of this CNPJ.
     *
     * <p>Example:</p>
     * <pre>{@code
     * Cnpj cnpj = new Cnpj("12345678000195");
     * System.out.println(cnpj.toString()); // prints "12.345.678/0001-95"
     * }</pre>
     *
     * @return the CNPJ string in the standard format (e.g., {@code 12.345.678/0001-95}).
     */
    @Override
    public String toString() {
        return CnpjUtils.format(getValue());
    }

}
