package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Message;

import java.util.function.Function;
import java.util.function.Predicate;

import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;

/**
 * A Validator that checks the length of a text field, if it has a non-null value, is within bounds.
 *
 * @param <T> The type of the context object containing the field being validated.
 */
public class StringLengthValidator<T> extends PredicatedValidator<T> {
    public static final String DEFAULT_MESSAGE_CODE = "length";
    public static final String DEFAULT_PARAM_MESSAGE_MIN_MAX = "Value must be between {0} and {1} characters in length";
    public static final String DEFAULT_PARAM_MESSAGE_MIN = "Value must be more than or equal to {0} characters in length";
    public static final String DEFAULT_PARAM_MESSAGE_MAX = "Value must be less than or equal to {0} characters in length";

    private final Predicate<?> VALIDATION_PREDICATE = v -> {
        String validationValueAsString = v == null ? null : v.toString();
        int len = (validationValueAsString == null ? 0 : validationValueAsString.trim().length());
        return len >= (getMinLengthInclusive() == null ? Integer.MIN_VALUE : getMinLengthInclusive())
                && len <= (getMaxLengthInclusive() == null ? Integer.MAX_VALUE : getMaxLengthInclusive());
    };

    /**
     * The minimum allowed length of the text of the field.
     */
    private Integer minLengthInclusive;

    /**
     * The maximum allowed length of the text of the field.
     */
    private Integer maxLengthInclusive;

    public StringLengthValidator(Integer minLengthInclusive,
                                 Integer maxLengthInclusive
                                ) {
        this(null,
             globalMessage(DEFAULT_MESSAGE_CODE, determineDefaultMessage(minLengthInclusive, maxLengthInclusive)),
             (Function<T, ?>) null,
             minLengthInclusive, maxLengthInclusive
            );
    }

    public StringLengthValidator(Message message,
                                 Integer minLengthInclusive,
                                 Integer maxLengthInclusive
                                ) {
        this(null, message, (Function<T, ?>) null,
             minLengthInclusive, maxLengthInclusive
            );
    }

    public StringLengthValidator(Predicate<T> condition, Message message,
                                 Integer minLengthInclusive,
                                 Integer maxLengthInclusive
                                ) {
        this(condition, message, (Function<T, ?>) null,
             minLengthInclusive, maxLengthInclusive
            );
    }

    public <R> StringLengthValidator(Message message,
                                     Function<T, R> valueProvider,
                                     Integer minLengthInclusive,
                                     Integer maxLengthInclusive
                                    ) {
        this(null, message, valueProvider, minLengthInclusive, maxLengthInclusive);
    }

    public <R> StringLengthValidator(Predicate<T> condition,
                                     Message message,
                                     Function<T, R> valueProvider,
                                     Integer minLengthInclusive,
                                     Integer maxLengthInclusive
                                    ) {
        super(condition, valueProvider, message);
        this.minLengthInclusive = minLengthInclusive;
        this.maxLengthInclusive = maxLengthInclusive;
        setValidation(VALIDATION_PREDICATE);
    }

    /**
     * Gets the minimum allowed length of the text of the field.
     *
     * @return the minimum length of the field, which may be null to indicate zero.
     */
    public Integer getMinLengthInclusive() {
        return minLengthInclusive;
    }

    /**
     * Gets he maximum allowed length of the text of the field.
     *
     * @return the maximum length of the field, which may be null to indicate unlimited.
     */
    public Integer getMaxLengthInclusive() {
        return maxLengthInclusive;
    }

    private static final String determineDefaultMessage(Integer minLengthInclusive,
                                                        Integer maxLengthInclusive
                                                       ) {
        if (minLengthInclusive != null && maxLengthInclusive != null) {
            return DEFAULT_PARAM_MESSAGE_MIN_MAX;
        } else if (minLengthInclusive != null) {
            return DEFAULT_PARAM_MESSAGE_MIN;
        } else if (maxLengthInclusive != null) {
            return DEFAULT_PARAM_MESSAGE_MAX;
        }

        return null;
    }
}
