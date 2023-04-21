package org.myfirstdatabase.exceptions;

import lombok.Getter;
import org.myfirstdatabase.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }


}