package uk.gov.ida.validation.validators;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A validator that supports validation on the top-level validation context object directly
 * or on a value provided by a value provider. Great for nested property or expression-based validation.
 */
public abstract class AbstractValueProvidedValidator<T>
        extends AbstractConditionalValidator<T>
        implements ValueProvidedValidator<T> {

    private Function<T, ?> valueProvider;

    public AbstractValueProvidedValidator() {
    }

    public AbstractValueProvidedValidator(Predicate<T> condition) {
        this(condition, (Function<T, ?>) null);
    }

    public AbstractValueProvidedValidator(Function<T, ?> valueProvider) {
        this(null, valueProvider);
    }

    public AbstractValueProvidedValidator(Predicate<T> condition, Function<T, ?> valueProvider) {
        super(condition);
        this.valueProvider = valueProvider;
    }

    @SuppressWarnings("unchecked")
    public <R> Function<T, R> getValueProvider() {
        return (Function<T, R>) valueProvider;
    }

    @SuppressWarnings("unchecked")
    protected <R> R getValidationValue(T context) {
        if (valueProvider == null) return (R) context;

        return (R) valueProvider.apply(context);
    }

    protected String getValidationValueAsString(T context) {
        Object value = getValidationValue(context);

        return value == null ? null : value.toString();
    }

}