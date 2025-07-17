package io.github.felseje.internal.cnpj.generation;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.internal.cnpj.util.CnpjCheckDigitCalculator;

/**
 * Generator for numeric CNPJ values.
 * <p>
 * This implementation creates a CNPJ base composed only of digits (0–9) and
 * calculates the corresponding check digits according to the {@link CnpjType#NUMERIC}.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class NumericGenerator extends AbstractGenerator {

    /**
     * Constructs a new {@code NumericGenerator}.
     */
    public NumericGenerator() {
        super();
    }

    /**
     * Generates a random numeric CNPJ string.
     * <p>
     * The method:
     * <ol>
     *     <li>Generates a 12-digit numeric base;</li>
     *     <li>Calculates check digits according to {@link CnpjType#NUMERIC};</li>
     *     <li>Returns the full CNPJ (base + check digits) unformatted.</li>
     * </ol>
     *
     * @return an unformatted numeric CNPJ string.
     */
    public String generate() {
        final var base = super.generateBase("0123456789");
        final var checkDigits = CnpjCheckDigitCalculator.calculateCheckDigits(base, CnpjType.NUMERIC);
        return new String(base) + new String(checkDigits);
    }

}
