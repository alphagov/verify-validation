package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Message;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;

/**
 * A Validator for checking a text field matches a regular expression.
 *
 * <p>This validator supports validation on the top-level validation context object directly
 * or on a value provided by runtime-expression or other value provider. Great for nested
 * properties</p>
 */
public class PatternValidator<T> extends PredicatedValidator<T> {
    public static final String DEFAULT_MESSAGE_CODE = "pattern";
    public static final String DEFAULT_PARAM_MESSAGE = "Value is required and must not be empty";

    private Pattern pattern;
    private final Predicate<?> VALIDATION_PREDICATE = v -> getPattern().matcher(v.toString()).matches();


    public PatternValidator(String pattern) {
        this(null,
             globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE),
             (Function<T, ?>)null, pattern);
    }

    public PatternValidator(Message message, String pattern) {
        this(null, message, (Function<T, ?>)null, pattern);
    }

    public PatternValidator(Predicate<T> condition, Message message, String pattern) {
        this(condition, message, (Function<T, ?>)null, pattern);
    }

    public <R> PatternValidator(Message message, Function<T, R> valueProvider, String pattern) {
        this(null, message, valueProvider, pattern);
    }

    public <R> PatternValidator(Predicate<T> condition, Message message, Function<T, R> valueProvider, String pattern) {
        super(condition, valueProvider, message);
        this.pattern = Pattern.compile(pattern);
        setValidation(VALIDATION_PREDICATE);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
