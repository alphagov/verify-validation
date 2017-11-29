package uk.gov.ida.validation.validators;


import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import uk.gov.ida.validation.messages.Message;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A conditional validator supporting validation of a single value (but not limited to).
 *
 * @param <T> The type of the context object containing the field being validated.
 */
public abstract class AbstractValidator<T> extends AbstractValueProvidedValidator<T> {
    /**
     * The message template applied by this validator.
     */
    private Message message;

    public AbstractValidator(Message message) {
        this(null, message);
    }

    /**
     * Construct a validator with a supplied condition.
     *
     * @param condition a {@link Predicate} that governs whether the validation is applied.
     */
    public AbstractValidator(Predicate<T> condition) {
        this(condition, null);
    }

    public AbstractValidator(Predicate<T> condition, Message message) {
        this(condition, message, (Function<T, ?>) null);
    }

    public AbstractValidator(Message message, Function<T, ?> valueProvider) {
        this(null, message, valueProvider);
    }

    public AbstractValidator(Predicate<T> condition, Message message, Function<T, ?> valueProvider) {
        super(condition, valueProvider);
        this.message = message;
    }

    /**
     * Gets the message template applied by this validator.
     *
     * @return the message template applied by this validator, which may be null.
     */
    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
