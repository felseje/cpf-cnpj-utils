package io.github.felseje.internal.cnpj.validation;

import static io.github.felseje.internal.Constants.NOT_ALLOWED_INSTANTIATION_ERROR;
import static io.github.felseje.internal.Constants.NULL_OR_BLANK_CNPJ_ERROR;

import io.github.felseje.cnpj.CnpjType;
import io.github.felseje.cnpj.exception.InvalidCnpjException;
import io.github.felseje.internal.util.StringUtils;
import java.util.function.Predicate;

public final class CnpjValidator {

  /**
   * Prevents instantiation of this class.
   *
   * @throws IllegalStateException always thrown to indicate this class should not be instantiated.
   */
  private CnpjValidator() {
    throw new IllegalStateException(NOT_ALLOWED_INSTANTIATION_ERROR);
  }

  private static void validate(String cnpj, Predicate<String> validator)
      throws InvalidCnpjException {
    if (!validator.test(cnpj)) {
      throw new InvalidCnpjException("CNPJ is not valid: " + cnpj);
    }
  }

  public static void validate(String cnpj, CnpjType type)
      throws IllegalArgumentException, InvalidCnpjException {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }

    StringUtils.requireNotBlank(cnpj, NULL_OR_BLANK_CNPJ_ERROR);

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
