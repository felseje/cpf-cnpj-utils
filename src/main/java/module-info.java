/**
 * Module providing utilities for validation, formatting, and manipulation of
 * Brazilian identification documents: CPF and CNPJ.
 *
 * <p>This module exports the main packages for CPF and CNPJ operations,
 * as well as their respective exception types. It also includes a generic
 * exception package for common use cases.</p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
module cpf.cnpj.utils {
    exports io.github.felseje.cpf;
    exports io.github.felseje.cpf.exception;
    exports io.github.felseje.cnpj;
    exports io.github.felseje.cnpj.exception;
    exports io.github.felseje.exception;
}