/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Beanplanet Ltd (original donor)
 * Copyright (c) 2017 Crown Copyright (Government Digital Service)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the right
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uk.gov.ida.validation.validators;

import org.junit.Test;
import uk.gov.ida.validation.messages.MessageImpl;
import uk.gov.ida.validation.messages.Messages;
import uk.gov.ida.validation.messages.MessagesImpl;
import uk.gov.ida.validation.validators.FixedErrorValidator;

import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static uk.gov.ida.validation.validators.Predicates.falsePredicate;
import static uk.gov.ida.validation.validators.Predicates.truePredicate;
import static uk.gov.ida.validation.messages.MessageImpl.fieldMessage;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;
import static uk.gov.ida.validation.messages.MessagesImpl.messages;

/**
 * Unit test for @{@link FixedErrorValidator}.
 */
public class FixedErrorValidatorTest {
    @Test
    public void ctorMessage() {
        // Given
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(message);

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(truePredicate()));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorMessageCodeAndMessage() {
        // Given
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(message.getCode(),
                                                                          message.getParameterisedMessage());

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(truePredicate()));
        assertThat(validator.getMessage(), not(sameInstance(message)));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorFieldMessageCodeAndMessage() {
        // Given
        MessageImpl message = fieldMessage("theField",
                                           "theCode",
                                            "theMessage"
                                           );
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(message.getField(),
                                                                          message.getCode(),
                                                                          message.getParameterisedMessage());

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(truePredicate()));
        assertThat(validator.getMessage(), not(sameInstance(message)));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorConditionMessage() {
        // Given
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        Predicate<Object> condition = mock(Predicate.class);
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(condition, message);

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), sameInstance(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorConditionMessageCodeAndMessage() {
        // Given
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        Predicate<Object> condition = mock(Predicate.class);
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(condition,
                                                                          message.getCode(),
                                                                          message.getParameterisedMessage());

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), not(sameInstance(message)));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void ctorConditionFieldMessageCodeAndMessage() {
        // Given
        MessageImpl message = fieldMessage("theField",
                                           "theCode",
                                            "theMessage"
                                           );
        Predicate<Object> condition = mock(Predicate.class);
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(condition,
                                                                          message.getField(),
                                                                          message.getCode(),
                                                                          message.getParameterisedMessage());

        // When
        validator.validate(new Object(), messages());

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getMessage(), not(sameInstance(message)));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValueProvider(), nullValue());
    }

    @Test
    public void alwaysAddsAnErrorWithDefaultMessageParametersWithTrueCondition() {
        // Given
        Object objectToValidate = new Object();
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        MessagesImpl messages = messages();
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(message);

        // When
        Messages returnedMessages = validator.validate(objectToValidate, messages);

        // Then
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));
        assertThat(returnedMessages.getErrors().get(0).getMessageParameters().length, is(2));
        assertThat(returnedMessages.getErrors().get(0).getMessageParameters()[0], sameInstance(objectToValidate));
        assertThat(returnedMessages.getErrors().get(0).getMessageParameters()[1], sameInstance(objectToValidate));

    }

    @Test
    public void alwaysAddsAnErrorWithSuppliedMessageParametersWithTrueCondition() {
        // Given
        Object objectToValidate = new Object();
        Object messageParam1 = "param1", messageParam2 = "param2", messageParam3 = "param3";
        MessageImpl message = globalMessage("theCode",
                                            "theMessage",
                                            messageParam1, messageParam2,messageParam3
                                           );
        MessagesImpl messages = messages();
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(message);

        // When
        Messages returnedMessages = validator.validate(objectToValidate, messages);

        // Then
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));
        assertThat(returnedMessages.getErrors().get(0).getMessageParameters().length, is(3));
        assertThat(returnedMessages.getErrors().get(0).getMessageParameters(), equalTo(new Object[] {
                messageParam1,
                messageParam2,
                messageParam3
        }));
    }

    @Test
    public void doesNotAddAnErrorWithFalseCondition() {
        // Given
        Object objectToValidate = new Object();
        MessageImpl message = globalMessage("theCode",
                                            "theMessage"
                                           );
        MessagesImpl messages = messages();
        FixedErrorValidator<Object> validator = new FixedErrorValidator<>(falsePredicate(),
                                                                          message);

        // When
        Messages returnedMessages = validator.validate(objectToValidate, messages);

        // Then
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));
    }
}