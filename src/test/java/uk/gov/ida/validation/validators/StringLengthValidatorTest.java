package uk.gov.ida.validation.validators;

import org.junit.Test;
import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static uk.gov.ida.validation.validators.Predicates.falsePredicate;
import static uk.gov.ida.validation.validators.Predicates.truePredicate;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;
import static uk.gov.ida.validation.messages.MessagesImpl.messages;
import static uk.gov.ida.validation.validators.StringLengthValidator.DEFAULT_MESSAGE_CODE;
import static uk.gov.ida.validation.validators.StringLengthValidator.DEFAULT_PARAM_MESSAGE_MAX;
import static uk.gov.ida.validation.validators.StringLengthValidator.DEFAULT_PARAM_MESSAGE_MIN;
import static uk.gov.ida.validation.validators.StringLengthValidator.DEFAULT_PARAM_MESSAGE_MIN_MAX;

/**
 * Unit test for @{@link StringLengthValidator}.
 *
 * <p>This validator supports validation on the top-level validation context object directly
 * or on a value provided by runtime-expression or other value provider. Great for nested
 * properties</p>
 */
public class StringLengthValidatorTest {
    @Test
    public void ctorMinMax() {
        // Given
        Messages messages = messages();
        Message message = globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE_MIN_MAX);
        StringLengthValidator<String> validator = new StringLengthValidator<>(5, 10);

        // Then the validator is configured correctly
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getMinLengthInclusive(), equalTo(5));
        assertThat(validator.getMaxLengthInclusive(), equalTo(10));


        // When a string whose length equals the minimum bound is validated
        Messages returnedMessages = validator.validate("12345", messages);

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
        assertThat(returnedMessages, sameInstance(messages));

        // When a string whose length equals the maximum bound is validated
        returnedMessages = validator.validate("1234567890", messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));

        // When a string whose length is less than the minimum is validated
        returnedMessages = validator.validate("123", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is greater than the minimum is validated
        returnedMessages = validator.validate("12345678901", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a validator is constructed with only a minimum bound
        validator = new StringLengthValidator<>(5, null);

        // Then the default message is appropriate for minimum bound only
        assertThat(validator.getMessage(), equalTo(globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE_MIN)));

        // When a validator is constructed with only a maximum bound
        validator = new StringLengthValidator<>(null, 10);

        // Then the default message is appropriate for maximum bound only
        assertThat(validator.getMessage(), equalTo(globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE_MAX)));

        // When a validator is constructed with no bound
        validator = new StringLengthValidator<>(null, null);

        // Then the default message fallback contains no parameterised message
        assertThat(validator.getMessage(), equalTo(globalMessage(DEFAULT_MESSAGE_CODE, null)));
    }

    @Test
    public void ctorMessageMinMax() {
        // Given
        Messages messages = messages();
        Message message = globalMessage("theCode", "theMessage");
        StringLengthValidator<String> validator = new StringLengthValidator<>(message, 5, 10);

        // Then the validator is configured correctly
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getMinLengthInclusive(), equalTo(5));
        assertThat(validator.getMaxLengthInclusive(), equalTo(10));


        // When a string whose length equals the minimum bound is validated
        Messages returnedMessages = validator.validate("12345", messages);

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
        assertThat(returnedMessages, sameInstance(messages));

        // When a string whose length equals the maximum bound is validated
        returnedMessages = validator.validate("1234567890", messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));

        // When a string whose length is less than the minimum is validated
        returnedMessages = validator.validate("123", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is greater than the minimum is validated
        returnedMessages = validator.validate("12345678901", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));
    }

    @Test
    public void ctorConditionMessageMinMax() {
        // Given
        Predicate<String> condition = truePredicate();
        Messages messages = messages();
        Message message = globalMessage("theCode", "theMessage");
        StringLengthValidator<String> validator = new StringLengthValidator<>(condition, message, 5, 10);

        // Then the validator is configured correctly
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getMinLengthInclusive(), equalTo(5));
        assertThat(validator.getMaxLengthInclusive(), equalTo(10));


        // When a string whose length equals the minimum bound is validated
        Messages returnedMessages = validator.validate("12345", messages);

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
        assertThat(returnedMessages, sameInstance(messages));

        // When a string whose length equals the maximum bound is validated
        returnedMessages = validator.validate("1234567890", messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));

        // When a string whose length is less than the minimum is validated
        returnedMessages = validator.validate("123", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is greater than the minimum is validated
        returnedMessages = validator.validate("12345678901", messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is outside the bounds is validates under a false predicate condition
        validator = new StringLengthValidator<>(falsePredicate(), message, 5, 10);
        returnedMessages = validator.validate("12345678901", messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
    }

    @Test
    public void ctorMessageValueProviderMinMax() {
        // Given
        Messages messages = messages();
        Message message = globalMessage("theCode", "theMessage");
        Function<TestBean, String> valueProvider = TestBean::getStringProperty;
        StringLengthValidator<TestBean> validator = new StringLengthValidator<>(message,
                                                                                valueProvider,
                                                                                5, 10);

        // Then the validator is configured correctly
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMinLengthInclusive(), equalTo(5));
        assertThat(validator.getMaxLengthInclusive(), equalTo(10));


        // When a string whose length equals the minimum bound is validated
        Messages returnedMessages = validator.validate(new TestBean("12345"), messages);

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
        assertThat(returnedMessages, sameInstance(messages));

        // When a string whose length equals the maximum bound is validated
        returnedMessages = validator.validate(new TestBean("1234567890"), messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));

        // When a string whose length is less than the minimum is validated
        returnedMessages = validator.validate(new TestBean("123"), messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is greater than the minimum is validated
        returnedMessages = validator.validate(new TestBean("12345678901"), messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));
    }

    @Test
    public void ctorConditionMessageValueProviderMinMax() {
        // Given
        Predicate<TestBean> condition = truePredicate();
        Messages messages = messages();
        Function<TestBean, String> valueProvider = TestBean::getStringProperty;
        Message message = globalMessage("theCode", "theMessage");
        StringLengthValidator<TestBean> validator = new StringLengthValidator<>(condition,
                                                                                message,
                                                                                valueProvider,
                                                                                5, 10);

        // Then the validator is configured correctly
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMinLengthInclusive(), equalTo(5));
        assertThat(validator.getMaxLengthInclusive(), equalTo(10));


        // When a string whose length equals the minimum bound is validated
        Messages returnedMessages = validator.validate(new TestBean("12345"), messages);

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
        assertThat(returnedMessages, sameInstance(messages));

        // When a string whose length equals the maximum bound is validated
        returnedMessages = validator.validate(new TestBean("1234567890"), messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));

        // When a string whose length is less than the minimum is validated
        returnedMessages = validator.validate(new TestBean("123"), messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is greater than the minimum is validated
        returnedMessages = validator.validate(new TestBean("12345678901"), messages());

        // Then an error is reported
        assertThat(returnedMessages.hasErrors(), is(true));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When a string whose length is outside the bounds is validates under a false predicate condition
        validator = new StringLengthValidator<>(falsePredicate(),
                                                message,
                                                valueProvider,
                                                5, 10
        );
        returnedMessages = validator.validate(new TestBean("12345678901"), messages());

        // Then no error is reported
        assertThat(returnedMessages.hasErrors(), is(false));
    }

}