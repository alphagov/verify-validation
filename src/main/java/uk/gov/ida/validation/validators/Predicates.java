package uk.gov.ida.validation.validators;

import java.util.function.Predicate;

public final class Predicates {
    /**
     * Package-protected get test coverage!
     */
    Predicates() {}

    public static <T> Predicate<T> truePredicate() {
        return o -> true;
    }

    public static <T> Predicate<T> falsePredicate() {
        return o -> false;
    }
}
