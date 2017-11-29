package uk.gov.ida.validation.validators;

import org.junit.Test;
import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;
import uk.gov.ida.validation.validators.AbstractValidator;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link AbstractValidator}.
 */
public class AbstractValidatorTest {
    @Test
    public void ctorMessage() {
        // Given
        Message message = mock(Message.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(message) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorCondition() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(condition) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorConditionAndMessage() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        Message message = mock(Message.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(condition, message) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorMessageAndValueProvider() {
        // Given
        Message message = mock(Message.class);
        Function<Object, ?> valueProvider = mock(Function.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(message, valueProvider) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
    }

    @Test
    public void ctorConditionMessageAndValueProvider() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        Message message = mock(Message.class);
        Function<Object, ?> valueProvider = mock(Function.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(condition, message, valueProvider) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
    }

    @Test
    public void toStringTest() {
        // Given
        String valueExpression = "theValueExpression";
        Message message = mock(Message.class);
        AbstractValidator<Object> validator = new AbstractValidator<Object>(message) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.toString(), containsString("condition="));
        assertThat(validator.toString(), containsString("message="));
        assertThat(validator.toString(), containsString("valueProvider="));
    }
}