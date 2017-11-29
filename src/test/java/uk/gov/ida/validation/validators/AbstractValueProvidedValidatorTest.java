package uk.gov.ida.validation.validators;

import org.junit.Test;
import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;
import uk.gov.ida.validation.validators.AbstractValueProvidedValidator;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link AbstractValueProvidedValidator}.
 */
public class AbstractValueProvidedValidatorTest {
    @Test
    public void ctorNoArgs() {
        // Given
        Message message = mock(Message.class);
        AbstractValueProvidedValidator<Object> validator = new AbstractValueProvidedValidator<Object>() {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorCondition() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        AbstractValueProvidedValidator<Object> validator = new AbstractValueProvidedValidator<Object>(condition) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorValueProvider() {
        // Given
        Function<Object, ?> valueProvider = mock(Function.class);
        AbstractValueProvidedValidator<Object> validator = new AbstractValueProvidedValidator<Object>(valueProvider) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
    }

    @Test
    public void ctorConditionAndValueProvider() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        Function<Object, ?> valueProvider = mock(Function.class);
        AbstractValueProvidedValidator<Object> validator = new AbstractValueProvidedValidator<Object>(condition, valueProvider) {
            @Override
            protected Messages doValidate(Object object, Messages messages) {
                return null;
            }
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
    }
}