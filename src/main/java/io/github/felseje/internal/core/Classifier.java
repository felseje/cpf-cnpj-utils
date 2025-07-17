package io.github.felseje.internal.core;

import io.github.felseje.cnpj.CnpjType;

/**
 * Represents a classifier that determines the {@link CnpjType} of a given document.
 * <p>
 * Implementations of this interface should analyze the input document string
 * and return the corresponding {@code CnpjType} based on its content or structure.
 * </p>
 *
 * @author felseje
 * @since 1.0.0-alpha
 */
public interface Classifier {

    /**
     * Classifies the given CNPJ into a specific {@link CnpjType}.
     *
     * @param document the CNPJ to classify.
     * @return the {@code CnpjType} representing the classification result.
     */
    CnpjType classify(String document);

}
