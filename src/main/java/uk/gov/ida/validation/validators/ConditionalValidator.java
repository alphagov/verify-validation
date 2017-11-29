package uk.gov.ida.validation.validators;

import java.util.function.Predicate;

/**
 * Validator which optionally holds a condition that must evaluate to true for the validation to be applied.
 *
 * @param <T>   The type of the context object containing the field being validated.
 */
public interface ConditionalValidator<T> extends Validator<T> {
    /**
     * Get the {@link Predicate} that has been set on this validator.
     *
     * @return the {@link Predicate} that has been set.  May be null.
     */
    Predicate<T> getCondition();
}
