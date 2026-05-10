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
   * Cpf normalized length constant.
   */
  public static final int CPF_LENGTH = 11;

  /**
   * Cpf formatted length constant.
   */
  public static final int CPF_FORMATTED_LENGTH = 14;

  /**
   * Cnpj normalized length constant.
   */
  public static final int CNPJ_LENGTH = 14;

  /**
   * Cnpj formatted length constant.
   */
  public static final int CNPJ_FORMATTED_LENGTH = 18;

  /**
   * Generic error message used when instantiating a class that is not meant to be instantiated.
   */
  public static final String NOT_ALLOWED_INSTANTIATION_ERROR = "This class should not be instantiated";

  /**
   * Standard error message indicating a CNPJ input is {@code null} or {@code blank}.
   */
  public static final String NULL_OR_BLANK_CNPJ_ERROR = "CNPJ must not be null or blank";

  /**
   * Standard error message indicating a CNPJ does not match any valid pattern
   */
  public static final String UNRECOGNIZED_CNPJ_TYPE_ERROR = "CNPJ does not match any valid pattern";

  /**
   * Private constructor to prevent instantiation.
   *
   * @throws IllegalStateException always thrown to enforce non-instantiability.
   */
  private Constants() {
    throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

}
