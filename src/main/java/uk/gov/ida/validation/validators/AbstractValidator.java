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


import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import uk.gov.ida.validation.messages.Message;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A conditional validator supporting validation of a single value (but not limited to).
 *
 * @param <T> The type of the context object containing the field being validated.
 */
public abstract class AbstractValidator<T> extends AbstractValueProvidedValidator<T> {
    /**
     * The message template applied by this validator.
     */
    private Message message;

    public AbstractValidator(Message message) {
        this(null, message);
    }

    /**
     * Construct a validator with a supplied condition.
     *
     * @param condition a {@link Predicate} that governs whether the validation is applied.
     */
    public AbstractValidator(Predicate<T> condition) {
        this(condition, null);
    }

    public AbstractValidator(Predicate<T> condition, Message message) {
        this(condition, message, (Function<T, ?>) null);
    }

    public AbstractValidator(Message message, Function<T, ?> valueProvider) {
        this(null, message, valueProvider);
    }

    public AbstractValidator(Predicate<T> condition, Message message, Function<T, ?> valueProvider) {
        super(condition, valueProvider);
        this.message = message;
    }

    /**
     * Gets the message template applied by this validator.
     *
     * @return the message template applied by this validator, which may be null.
     */
    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
