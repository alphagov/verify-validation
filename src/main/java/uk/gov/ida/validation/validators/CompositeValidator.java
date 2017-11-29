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

import uk.gov.ida.validation.messages.Messages;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A validator that is composed of one or more other validators, each of which may be a composite validator
 * or a terminal-node validator.
 *
 * <p>This validator supports validation on the top-level validation context object directly
 * or on a value provided by runtime-expression or other value provider. Great for nested
 * properties</p>
 *
 * @param <T> The type of the context object being validated.
 */
public class CompositeValidator<T> extends AbstractValueProvidedValidator<T> {

    private boolean stopOnFirstError;

    private Validator<?> validators[];

    @SafeVarargs
    public CompositeValidator(Validator<T>... validators) {
        this.validators = validators;
    }

    @SafeVarargs
    public <R> CompositeValidator(Function<T, R> valueProvider,
                              Validator<R>... validators) {
        super(null, valueProvider);
        this.validators = validators;
    }

    @SafeVarargs
    public CompositeValidator(boolean stopOnFirstError, Validator<T>... validators) {
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SafeVarargs
    public <R> CompositeValidator(boolean stopOnFirstError,
                                  Function<T, R> valueProvider,
                                  Validator<R>... validators) {
        super(null, valueProvider);
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public CompositeValidator(Predicate<T> condition, boolean stopOnFirstError, Validator<T>... validators) {
        this(condition, stopOnFirstError, (Function)null, validators);
    }

    @SafeVarargs
    public <R> CompositeValidator(Predicate<T> condition,
                              boolean stopOnFirstError,
                              Function<T, R> valueProvider,
                              Validator<R>... validators) {
        super(condition, valueProvider);
        this.stopOnFirstError = stopOnFirstError;
        this.validators = validators;
    }

    @SuppressWarnings("unchecked")
    protected Messages doValidate(T object, Messages messages) {
        Object valueProvided = getValidationValue(object);

        int originalErrorCount = messages.hasErrors() ? messages.getErrors().size() : 0;

        for (Validator<?> validator : validators) {
            ((Validator<Object>)validator).validate(valueProvided, messages);
            int errorsNow = messages.hasErrors() ? messages.getErrors().size() : 0;
            if (stopOnFirstError && (errorsNow >  originalErrorCount)) {
                break;
            }
        }

        return messages;
    }

    public boolean isStopOnFirstError() {
        return stopOnFirstError;
    }

    @SuppressWarnings("unchecked")
    public <R> Validator<R>[] getValidators() {
        return (Validator[])validators;
    }
}
