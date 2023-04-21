package org.myfirstdatabase.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
