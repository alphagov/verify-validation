package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Messages;

/**
 * Definition of a simple validator.
 *
 * @param <T>   The type of the context object being validated.
 */
public interface Validator<T> {

    /**
     * Applies the validation to the given object.
     *
     * @param object        the object being validated.
     * @param messages      standard messages container to which any validation messages can be added.
     * @return              the messages container in its post-validation state.
     */
    Messages validate(T object, Messages messages);
}