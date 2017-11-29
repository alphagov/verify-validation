package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Messages;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A validator that is composed of one or more other validators, each of which may be a composite validator
 * or a terminal-node validator.
 *
 * <p>This validator supports validation on the top-level validation context object directly
 * or on a value provided by runtime-expression or other value provider. Great for nested
 * properties</p>
 *
 * @param <T> The type of the context object being validated.
 */
public class CompositeValidator<T> extends AbstractValueProvidedValidator<T> {

    private boolean stopOnFirstError;

    private Validator<?> validators[];

    @SafeVarargs
    public CompositeValidator(Validator<T>... validators) {
        this.validators = validators;
    }

    @SafeVarargs
    public <R> CompositeValidator(Function<T, R> valueProvider,
                              Validator<R>... validators) {
        super(null, valueProvider);
        this.validators = validators;
    }

    @SafeVarargs
    public CompositeValidator(boolean stopOnFirstError, Validator<T>... validators) {
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SafeVarargs
    public <R> CompositeValidator(boolean stopOnFirstError,
                                  Function<T, R> valueProvider,
                                  Validator<R>... validators) {
        super(null, valueProvider);
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public CompositeValidator(Predicate<T> condition, boolean stopOnFirstError, Validator<T>... validators) {
        this(condition, stopOnFirstError, (Function)null, validators);
    }

    @SafeVarargs
    public <R> CompositeValidator(Predicate<T> condition,
                              boolean stopOnFirstError,
                              Function<T, R> valueProvider,
                              Validator<R>... validators) {
        super(condition, valueProvider);
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SuppressWarnings("unchecked")
    protected Messages doValidate(T object, Messages messages) {
        Object valueProvided = getValidationValue(object);

        int originalErrorCount = messages.hasErrors() ? messages.getErrors().size() : 0;

        for (Validator<?> validator : validators) {
            ((Validator<Object>)validator).validate(valueProvided, messages);
            int errorsNow = messages.hasErrors() ? messages.getErrors().size() : 0;
            if (stopOnFirstError && (errorsNow >  originalErrorCount)) {
                break;
            }
        }

        return messages;
    }

    public boolean isStopOnFirstError() {
        return stopOnFirstError;
    }

    @SuppressWarnings("unchecked")
    public <R> Validator<R>[] getValidators() {
        return (Validator[])validators;
    }
}
