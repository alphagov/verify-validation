package uk.gov.ida.validation.validators;

import java.util.function.Function;

/**
 * A validator that supports validation on the top-level validation context object directly
 * or on a value provided by a value provider. Great for nested property or expression-based validation.
 */
public interface ValueProvidedValidator<T> {
    <R> Function<T, R> getValueProvider();
}
