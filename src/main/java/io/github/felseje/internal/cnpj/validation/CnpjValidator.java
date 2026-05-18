package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;
import static io.github.felseje.internal.util.StringUtils.requireNotBlank;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.InvalidCnpjException;
import io.github.felseje.internal.util.StringUtils;
import java.util.function.Predicate;

/**
 * Utility class for validating Brazilian CNPJ (Cadastro Nacional da Pessoa Jurídica).
 *
 * <p>A CNPJ is considered valid if it meets all the following criteria:
 * <ul>
 *   <li>Input is not null or blank</li>
 *   <li>Contains 12 alphanumeric base characters and 2 numeric check digits
 *       (exactly 14 characters long)</li>
 *   <li>Is not composed of a single repeated character
 *       (e.g. "11111111111111")</li>
 *   <li>Has valid check digits according to the official CNPJ algorithm</li>
 * </ul>
 *
 * <p>This validator expects normalized input without formatting characters.
 *
 * <p>Example usage:
 * <pre>{@code
 * CnpjValidator validator = new CnpjValidator();
 *
 * boolean valid1 = validator.isValid("HG9EHKK4000173"); // true
 * boolean valid2 = validator.isValid("16747156000170"); // true
 *
 * boolean invalid1 = validator.isValid("HG.9EH.KK4/0001-73"); // false
 * boolean invalid2 = validator.isValid("16.747.156/0001-70"); // false
 * }</pre>
 *
 * @author felseje
 * @since 1.0.0-beta
 */
public final class CnpjValidator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws UnsupportedOperationException always thrown to indicate this class should not be
   *                                       instantiated.
   */
  private CnpjValidator() {
    throw new UnsupportedOperationException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  /**
   * Validates a CNPJ using the provided validation predicate.
   *
   * <p>If the predicate returns false, an {@link InvalidCnpjException} is thrown.
   *
   * @param cnpj      the normalized CNPJ to validate
   * @param validator the validation rule to apply
   * @throws InvalidCnpjException if the CNPJ is not valid according to the provided validator
   */
  private static void validate(final String cnpj, final Predicate<String> validator)
      throws InvalidCnpjException {
    if (!validator.test(cnpj)) {
      throw new InvalidCnpjException("CNPJ is not valid");
    }
  }

  /**
   * Validates a CNPJ according to the specified {@link CnpjType}.
   *
   * <p>The validation strategy is selected based on the provided type:
   * <ul>
   *   <li>{@link CnpjType#NUMERIC} validates traditional numeric CNPJ values</li>
   *   <li>{@link CnpjType#ALPHANUMERIC} validates alphanumeric CNPJ values</li>
   * </ul>
   *
   * <p>The provided CNPJ must be normalized and must not contain
   * formatting characters such as dots, slashes, or hyphens.
   *
   * @param cnpj the normalized CNPJ to validate
   * @param type the CNPJ type that determines the validation strategy
   * @throws IllegalArgumentException if the CNPJ is null, blank, the type is null, or the type is
   *                                  unsupported
   * @throws InvalidCnpjException     if the CNPJ is invalid according to the specified validation
   *                                  strategy
   */
  public static void validate(final String cnpj, final CnpjType type)
      throws IllegalArgumentException, InvalidCnpjException {
    if (type == null) {
      throw new IllegalArgumentException("CNPJ type cannot be null");
    }

    requireNotBlank(cnpj, NULL_OR_BLANK_CNPJ_ERROR);

    switch (type) {
      case NUMERIC:
        validate(cnpj, NumericCnpjValidator::isValid);
        break;
      case ALPHANUMERIC:
        validate(cnpj, AlphanumericCnpjValidator::isValid);
        break;
      default:
        throw new IllegalArgumentException("Unsupported CNPJ type: " + type);
    }
  }

}
