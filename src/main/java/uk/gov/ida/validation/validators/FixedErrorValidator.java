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