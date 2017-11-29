package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Message;

import java.util.function.Function;
import java.util.function.Predicate;

import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;

/**
 * A validator that expects a given field within an object to have a non-null value.  The field
 * may be shallow, as in a top-level field, or deeply-nested, as in belonging to a sub-object
 * of the context object.
 *
 * <p>This validator supports validation on the top-level validation context object directly
 * or on a value provided by runtime-expression or other value provider. Great for nested
 * properties</p>
 *
 * @param <T>   The type of the context object containing the field being validated.
 */
public class RequiredValidator<T> extends PredicatedValidator<T> {
    public static final String DEFAULT_MESSAGE_CODE = "required";
    public static final String DEFAULT_PARAM_MESSAGE = "Value is required";
    private static final Predicate<?> VALIDATION_PREDICATE = v -> v != null;

    public RequiredValidator() {
        this(globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE));
    }

    public RequiredValidator(Message message) {
        this(null, message, (Function<T, ?>)null);
    }

    public <R> RequiredValidator(Message message, Function<T, R> valueProvider) {
        this(null, message, valueProvider);
    }

    public RequiredValidator(Predicate<T> condition, Message message) {
        this(condition, message, (Function<T, ?>)null);
    }

    public RequiredValidator(Predicate<T> condition, Message message, Function<T, ?> valueProvider) {
        super(condition, valueProvider, message, VALIDATION_PREDICATE);
    }
}
