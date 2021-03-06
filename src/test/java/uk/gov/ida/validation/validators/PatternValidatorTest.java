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
import uk.gov.ida.validation.messages.Message;
import uk.gov.ida.validation.messages.Messages;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static uk.gov.ida.validation.validators.Predicates.falsePredicate;
import static uk.gov.ida.validation.validators.Predicates.truePredicate;
import static uk.gov.ida.validation.messages.MessageImpl.globalMessage;
import static uk.gov.ida.validation.messages.MessagesImpl.messages;
import static uk.gov.ida.validation.validators.PatternValidator.DEFAULT_MESSAGE_CODE;
import static uk.gov.ida.validation.validators.PatternValidator.DEFAULT_PARAM_MESSAGE;

/**
 * Unit tests for @{@link PatternValidator}.
 */
public class PatternValidatorTest {
    @Test
    public void ctorPattern() {
        // Given
        Message message = globalMessage(DEFAULT_MESSAGE_CODE, DEFAULT_PARAM_MESSAGE);
        Messages messages = messages();
        PatternValidator<Object> validator = new PatternValidator<>("\\d+");

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));

        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // When a valid object is validated
        Messages returnedMessages = validator.validate("123", messages);

        // Then no error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));

        // When an invalid object is validated
        returnedMessages = validator.validate("123a", messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When an invalid object is validated, but under a false condition
        validator = new PatternValidator<>(falsePredicate(), message, "\\d+");
        returnedMessages = validator.validate("123a", messages());

        // Then no error is added
        assertThat(returnedMessages.hasErrors(), is(false));
    }

    @Test
    public void ctorMessageAndPattern() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        PatternValidator<Object> validator = new PatternValidator<>(message, "\\d+");

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // When an invalid object is validated
        Messages returnedMessages = validator.validate("123", messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));

        // When an invalid object is validated
        returnedMessages = validator.validate("123a", messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));
    }

    @Test
    public void ctorConditionMessageAndPattern() {
        // Given
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        Predicate<Object> condition = truePredicate();
        PatternValidator<Object> validator = new PatternValidator<>(condition, message, "\\d+");

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), nullValue());
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // When a valid object is validated
        Messages returnedMessages = validator.validate("123", messages);

        // Then no error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));

        // When an invalid object is validated
        returnedMessages = validator.validate("123a", messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When an invalid object is validated, but under a false condition
        validator = new PatternValidator<>(falsePredicate(), message, "\\d+");
        returnedMessages = validator.validate("123a", messages());

        // Then no error is added
        assertThat(returnedMessages.hasErrors(), is(false));
    }

    @Test
    public void ctorMessageValueProviderAndPattern() {
        // Given
        Function<TestBean, String> valueProvider = TestBean::getStringProperty;
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        PatternValidator<TestBean> validator = new PatternValidator<>(message,
                                                                      valueProvider,
                                                                      "\\d+"
        );

        // Then
        assertThat(validator.getCondition(), nullValue());
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // When a valid object is validated
        Messages returnedMessages = validator.validate(new TestBean("1234"), messages);

        // Basic Pattern Validator validation
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // Then no error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));

        // When an invalid object is validated
        returnedMessages = validator.validate(new TestBean("123a"), messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When an invalid object is validated, but under a false condition
        validator = new PatternValidator<>(falsePredicate(), message, "\\d+");
        returnedMessages = validator.validate(new TestBean("123a"), messages());

        // Then no error is added
        assertThat(returnedMessages.hasErrors(), is(false));
    }

    @Test
    public void ctorConditionMessageValueProviderAndPattern() {
        // Given
        Predicate<TestBean> condition = truePredicate();
        Function<TestBean, String> valueProvider = TestBean::getStringProperty;
        Message message = globalMessage("theCode", "theMessage");
        Messages messages = messages();
        PatternValidator<TestBean> validator = new PatternValidator<>(condition,
                                                                      message,
                                                                      valueProvider,
                                                                      "\\d+"
        );

        // Then
        assertThat(validator.getCondition(), sameInstance(condition));
        assertThat(validator.getValueProvider(), sameInstance(valueProvider));
        assertThat(validator.getMessage(), equalTo(message));
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // When a valid object is validated
        Messages returnedMessages = validator.validate(new TestBean("1234"), messages);

        // Basic Pattern Validator validation
        assertThat(validator.getValidation(), notNullValue());
        assertThat(validator.getValidation().test("123"), is(true));
        assertThat(validator.getValidation().test("a12"), is(false));
        assertThat(validator.getPattern().pattern(), equalTo("\\d+"));

        // Then no error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrors(), is(false));

        // When an invalid object is validated
        returnedMessages = validator.validate(new TestBean("123a"), messages);

        // Then an error is added
        assertThat(returnedMessages, sameInstance(messages));
        assertThat(returnedMessages.hasErrorLike(message), is(true));

        // When an invalid object is validated, but under a false condition
        validator = new PatternValidator<>(falsePredicate(), message, "\\d+");
        returnedMessages = validator.validate(new TestBean("123a"), messages());

        // Then no error is added
        assertThat(returnedMessages.hasErrors(), is(false));
    }
}