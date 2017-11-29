package uk.gov.ida.validation.validators;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import uk.gov.ida.validation.messages.Messages;

import java.util.function.Predicate;

/**
 * Validator which performs its validation only if a given predicate answers true.
 */
public abstract class AbstractConditionalValidator<T> implements ConditionalValidator<T> {

    /**
     * An optional condition that must evaluate to true for the validation to be applied.
     */
    private Predicate<T> condition;

    /**
     * Constructs a conditional that always performs validation.
     */
    public AbstractConditionalValidator() {
    }

    /**
     * Constructs a conditional validator with a supplied condition predicate.
     *
     * @param condition a {@link Predicate} that governs whether the validation is applied.
     */
    public AbstractConditionalValidator(Predicate<T> condition) {
        this.condition = condition;
    }

    /**
     * Conditionally-validate the context object, according to the result of evaluating this validator's
     * condition.
     *
     * @param object   the object being validated.
     * @param messages standard messages container to which will be added validation messages.
     * @return the messages container provided, containing any new messages post-validation.
     */
    @Override
    public final Messages validate(T object, Messages messages) {
        if (condition == null || condition.test(object)) {
            return doValidate(object, messages);
        }
        return messages;
    }

    @Override
    public Predicate<T> getCondition() {
        return condition;
    }

    /**
     * Carry out validation.  This method delegates to extending classes.
     *
     * @param object   the object being validated.
     * @param messages standard messages container to which will be added validation messages.
     * @return the messages container provided, containing any new messages post-validation.
     */
    protected abstract Messages doValidate(T object, Messages messages);

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
