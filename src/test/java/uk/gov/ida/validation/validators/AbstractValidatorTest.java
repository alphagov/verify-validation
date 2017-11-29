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