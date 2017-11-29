package uk.gov.ida.validation.validators;

import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;

import java.util.function.Predicate;

import static uk.gov.ida.validation.validators.Predicates.truePredicate;
import static uk.gov.ida.validation.messages.MessageImpl.fieldMessage;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;

/**
 * A Validator which always adds an error to the list of error messages.  This is a conditional validator which can be used
 * to conditionally add an error to the errors list.
 */
public class FixedErrorValidator<T> extends AbstractValidator<T> {
    public FixedErrorValidator(Message message) {
        super(truePredicate(), message);
    }

    public FixedErrorValidator(String messageCode, String parameterisedMessage) {
        this(truePredicate(), globalMessage(messageCode, parameterisedMessage));
    }

    public FixedErrorValidator(String field, String messageCode, String parameterisedMessage) {
        this(truePredicate(), fieldMessage(field, messageCode, parameterisedMessage));
    }

    public FixedErrorValidator(Predicate<T> condition, Message message) {
        super(condition, message);
    }

    public FixedErrorValidator(Predicate<T> condition, String messageCode, String parameterisedMessage) {
        this(condition, globalMessage(messageCode, parameterisedMessage));
    }

    public FixedErrorValidator(Predicate<T> condition, String field, String messageCode, String parameterisedMessage) {
        this(condition, fieldMessage(field, messageCode, parameterisedMessage));
    }

    @Override
    protected Messages doValidate(T object, Messages messages) {
        messages.addError(fieldMessage(getMessage().getField(),
                                       getMessage().getCode(),
                                       getMessage().getParameterisedMessage(),
                                       getMessage().getMessageParameters() != null ? getMessage().getMessageParameters() :
                                       new Object[]{
                                               object,
                                               getValidationValue(object)
                                       })
                         );
        return messages;
    }
}