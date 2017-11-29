package uk.gov.ida.validation.validators;

import org.junit.Test;
import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;
import static uk.gov.ida.validation.messages.MessagesImpl.messages;

public class PredicatedValidatorTest {
    @Test
    public void ctorMessage() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(message) {
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), nullValue());
    }

    @Test
    public void ctorConditionAndMessage() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<Object> condition = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(condition, message) {
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), nullValue());
    }

    @Test
    public void ctorMessageAndValidation() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(message, validation) {
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), sameInstance(validation));
    }

    @Test
    public void ctorConditionMessageAndValidation() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<Object> condition = mock(Predicate.class);
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(condition, message, validation) {
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), sameInstance(validation));
    }

    @Test
    public void ctorValueProviderAndMessage() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Function<Object, Object> valueProvider = mock(Function.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(valueProvider,
                                                                                message
        ) {
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), nullValue());
    }

    @Test
    public void ctorConditionValueProviderAndMessage() {
        // Given
        Predicate<Object> condition = mock(Predicate.class);
        Message message = globalMessage("theCode", "theMessage");
        Function<Object, Object> valueProvider = mock(Function.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(condition,
                                                                                valueProvider,
                                                                                message
        ) {
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), nullValue());
    }

    @Test
    public void ctorValueProviderMessageAndValidation() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Function<Object, Object> valueProvider = mock(Function.class);
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(valueProvider,
                                                                                message,
                                                                                validation
        ) {
        };

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), sameInstance(validation));
    }

    @Test
    public void ctorConditionValueProviderMessageAndValidation() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<Object> condition = mock(Predicate.class);
        Function<Object, Object> valueProvider = mock(Function.class);
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(condition,
                                                                                valueProvider,
                                                                                message,
                                                                                validation
        ) {
        };

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), notNullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), sameInstance(validation));
    }

    @Test
    public void setValidation() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Predicate<?> validation = mock(Predicate.class);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(message) {
        };

        // When
        validator.setValidation(validation);

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValidation(), sameInstance(validation));
    }

    @Test
    public void doValidateAddsErrorWhenValidationFails() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        Predicate<TestBean> condition = mock(Predicate.class);
        Function<TestBean, String> valueProvider = o -> o.getStringProperty();
        Predicate<String> validation = mock(Predicate.class);
        TestBean objectToValidate = new TestBean("theStringPropertyValue");
        when(condition.test(anyObject())).thenReturn(true);
        PredicatedValidator<TestBean> validator = new PredicatedValidator<TestBean>(condition,
                                                                                    valueProvider,
                                                                                    message,
                                                                                    validation) {
        };

        // When
        validator.validate(objectToValidate, messages);

        // Then
        verify(validation).test(objectToValidate.getStringProperty());
        verifyNoMoreInteractions(validation);

        assertThat(messages.hasErrors(), is(true));
        assertThat(messages.hasErrorLike(globalMessage("theCode", "theMessage")), is(true));
        assertThat(messages.getErrors().get(0).getMessageParameters().length, is(2));
        assertThat(messages.getErrors().get(0).getMessageParameters()[0], sameInstance(objectToValidate));
        assertThat(messages.getErrors().get(0).getMessageParameters()[1], sameInstance(objectToValidate.getStringProperty()));
    }

    @Test
    public void doValidate() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        Predicate<Object> validation = mock(Predicate.class);
        Object objectToValidate = new Object();
        when(validation.test(anyObject())).thenReturn(true);
        PredicatedValidator<Object> validator = new PredicatedValidator<Object>(message,
                                                                                validation) {
        };

        // When
        validator.validate(objectToValidate, messages);

        // Then
        verify(validation).test(objectToValidate);
        verifyNoMoreInteractions(validation);

        assertThat(messages.hasErrors(), is(false));
        assertThat(messages.size(), is(0));
    }
}