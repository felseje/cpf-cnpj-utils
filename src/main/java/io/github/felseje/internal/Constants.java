package io.github.felseje.internal;

/**
 * Holds shared constant values used across the internal package.
 *
 * <p>This class is not intended to be instantiated. All members are static.</p>
 *
 * <p>Typical usage:
 * <pre>{@code
 *     throw new IllegalStateException(Constants.NOT_ALLOWED_INSTANTIATION_ERROR);
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public final class Constants {

    /**
     * Generic error message used when instantiating a class that is not meant to be instantiated.
     */
    public static final String NOT_ALLOWED_INSTANTIATION_ERROR = "This class should not be instantiated";

    /**
     * Standard error message indicating a CNPJ input is {@code null} or blank.
     */
    public static final String NULL_OR_BLANK_ERROR = "The CNPJ must be not null or blank";

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalStateException always thrown to enforce non-instantiability.
     */
    private Constants() {
        throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
    }

}
